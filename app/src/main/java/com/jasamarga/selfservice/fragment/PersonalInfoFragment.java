package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jasamarga.selfservice.LoginActivity;
import com.jasamarga.selfservice.MainActivity;
import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.adapters.PersonalInfoAdapter;
import com.jasamarga.selfservice.callback.PersonalInfoAdapterCallback;
import com.jasamarga.selfservice.customwiget.CustomEditText;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.events.ErrorEvent;
import com.jasamarga.selfservice.events.MorePersonalInfoEvent;
import com.jasamarga.selfservice.events.PersonalInfoEvent;
import com.jasamarga.selfservice.models.LoginDao;
import com.jasamarga.selfservice.models.PersonalInfoDao;
import com.jasamarga.selfservice.presenters.MainPresenter;
import com.jasamarga.selfservice.utility.PreferenceUtility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 12/29/16.
 */

public class PersonalInfoFragment extends Fragment implements PersonalInfoAdapterCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.llContainerPersonalInfo)
    LinearLayout llContainerPersonalInfo;

    @InjectView(R.id.tv_header_slipgaji)
    CustomTextView tv_header_slipgaji;

    @InjectView(R.id.srlPersonalInfo)
    SwipeRefreshLayout srlPersonalInfo;

    @InjectView(R.id.rvPersonalInfo)
    RecyclerView rvPersonalInfo;

    @InjectView(R.id.bottomSheetPersonalInfo)
    LinearLayout bottomSheetPersonalInfo;

    @InjectView(R.id.edSearchPersonalInfo)
    CustomEditText edSearchPersonalInfo;

    private Context context;
    private View view;
    private LoginDao loginDao;
    private DetailPersonalInfoFragment detailPersonalInfoFragment;
    private Dialog progressDialog;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayoutManager linearLayoutManager;
    private PersonalInfoAdapter personalInfoAdapter;
    private List<PersonalInfoDao> personalInfoDaoList = new ArrayList<>();
    private int start = 0, limit = 20, isSearchMode = 0, isMaxSize = 0, prevSize = 0;
    private String kword = "";

    public PersonalInfoFragment() {

    }

    @SuppressLint("ValidFragment")
    public PersonalInfoFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.content_listpersonal, container, false);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        loginDao = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.USER_DATA), LoginDao.class);

        setupPersonalInfoProgressDialog();
        setupPersonalInfoView();

        float radius = tv_header_slipgaji.getTextSize() / 18;
        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID);
        tv_header_slipgaji.getPaint().setMaskFilter(filter);

        edSearchPersonalInfo.setOnEditorActionListener(new SearchPersonalInfo());

        if (isSearchMode == 0) {
            isSearchMode = 1;
            progressDialog.show();
            mainPresenter.postPersonalInfo(loginDao.getNpp(),
                    PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                    kword,
                    String.valueOf(start),
                    String.valueOf(limit));
        }
    }

    @Override
    public void onDestroy() {
        kword = "";
        start = 0;
        prevSize = 0;
        isSearchMode = 0;
        isMaxSize = 0;
        personalInfoDaoList.clear();
        edSearchPersonalInfo.setText("");

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        super.onDestroy();
    }

    @Subscribe
    public void onEventThread(PersonalInfoEvent event) {
        srlPersonalInfo.setRefreshing(false);
        progressDialog.dismiss();
        personalInfoDaoList.addAll(event.getData());
        prevSize = personalInfoDaoList.size();
        isSearchMode = 0;
        personalInfoAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEventThread(MorePersonalInfoEvent event) {
        srlPersonalInfo.setRefreshing(false);
        progressDialog.dismiss();
        if (prevSize != personalInfoDaoList.size() + event.getData().size()) {
            prevSize = personalInfoDaoList.size();
            personalInfoDaoList.addAll(event.getData());
        }else {
            isMaxSize = 1;
        }
        isSearchMode = 0;
        personalInfoAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        Log.d("retro", "code " + event.getCode());
        srlPersonalInfo.setRefreshing(false);
        progressDialog.dismiss();
        if (event.getMessage().toLowerCase().equals("econnreset")) {
            if (isSearchMode == 0) {
                isSearchMode = 1;
                progressDialog.show();
                mainPresenter.postPersonalInfo(loginDao.getNpp(),
                        PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                        kword,
                        String.valueOf(start),
                        String.valueOf(limit));
            }
        }else if (event.getMessage().toLowerCase().contains("invalid token")) {
            Snackbar.make(llContainerPersonalInfo, "Telah login di device lain.", Snackbar.LENGTH_SHORT).show();
            isSearchMode = 0;
            ((MainActivity)context).LogoutFromAccount();
        }
    }

    private void setupPersonalInfoProgressDialog() {
        progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.loading_screen);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
    }

    private void setupPersonalInfoView() {
        srlPersonalInfo.setOnRefreshListener(new RefreshPersonalInfo());

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPersonalInfo.setHasFixedSize(true);
        rvPersonalInfo.setLayoutManager(linearLayoutManager);

        personalInfoAdapter = new PersonalInfoAdapter(context, personalInfoDaoList, this);
        rvPersonalInfo.setAdapter(personalInfoAdapter);
        rvPersonalInfo.addOnScrollListener(new LoadMorePersonalInfo());

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetPersonalInfo);
        bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24,
                getResources().getDisplayMetrics()));
    }

    @Override
    public void ShowDetailPersonalInfo(String npp) {
        detailPersonalInfoFragment = new DetailPersonalInfoFragment(context, npp);
        detailPersonalInfoFragment.show(((MainActivity)context).getSupportFragmentManager(), "detailPersonalInfoFragment");
    }

    private class RefreshPersonalInfo implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            srlPersonalInfo.setRefreshing(true);
            personalInfoDaoList.clear();
            edSearchPersonalInfo.setText("");
            kword = "";
            start = 0;
            isMaxSize = 0;

            if (isSearchMode == 0) {
                isSearchMode = 1;
                progressDialog.show();
                mainPresenter.postPersonalInfo(loginDao.getNpp(),
                        PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                        kword,
                        String.valueOf(start),
                        String.valueOf(limit));
            }
        }
    }

    private class SearchPersonalInfo implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                kword = edSearchPersonalInfo.getText().toString();
                start = 0;
                isMaxSize = 0;

                if (isSearchMode == 0 && isMaxSize == 0) {
                    isSearchMode = 1;
                    personalInfoDaoList.clear();
                    progressDialog.show();
                    mainPresenter.postPersonalInfo(loginDao.getNpp(),
                            PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                            kword,
                            String.valueOf(start),
                            String.valueOf(limit)
                    );
                }

                return true;
            }

            return false;
        }
    }

    private class LoadMorePersonalInfo extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemCount = linearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemCount = linearLayoutManager.findLastVisibleItemPosition();

            if(lastVisibleItemCount == totalItemCount - 1 && totalItemCount!=0)
            {
                if(isSearchMode == 0 && isMaxSize == 0)
                {
                    isSearchMode = 1;
                    kword = edSearchPersonalInfo.getText().toString();
                    start = totalItemCount;
                    limit = 20;
                    progressDialog.show();
                    mainPresenter.postMorePersonalInfo(loginDao.getNpp(),
                            PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                            kword,
                            String.valueOf(start),
                            String.valueOf(limit)
                    );
                }
            }

        }
    }

}
