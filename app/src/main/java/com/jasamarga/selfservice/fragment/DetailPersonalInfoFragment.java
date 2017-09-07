package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jasamarga.selfservice.MainActivity;
import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.adapters.BawahanListAdapter;
import com.jasamarga.selfservice.adapters.RekanListAdapter;
import com.jasamarga.selfservice.callback.BawahanListAdapterCallback;
import com.jasamarga.selfservice.callback.RekanListAdapterCallback;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.events.AtasanInfoEvent;
import com.jasamarga.selfservice.events.BawahanInfoEvent;
import com.jasamarga.selfservice.events.DetailPersonalInfoEvent;
import com.jasamarga.selfservice.events.ErrorEvent;
import com.jasamarga.selfservice.events.PeerInfoEvent;
import com.jasamarga.selfservice.events.PersonalInfoEvent;
import com.jasamarga.selfservice.models.LoginDao;
import com.jasamarga.selfservice.models.PersonalInfoDao;
import com.jasamarga.selfservice.presenters.MainPresenter;
import com.jasamarga.selfservice.utility.Config;
import com.jasamarga.selfservice.utility.PreferenceUtility;
import com.jasamarga.selfservice.utility.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by apridosandyasa on 12/29/16.
 */

public class DetailPersonalInfoFragment extends DialogFragment implements AppBarLayout.OnOffsetChangedListener,
        BawahanListAdapterCallback, RekanListAdapterCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.abl_personalinfo)
    AppBarLayout abl_personalinfo;

    @InjectView(R.id.toolbar_personalinfo)
    Toolbar toolbar_personalinfo;

    @InjectView(R.id.nsv_contentcontainer_personalinfo)
    NestedScrollView nsv_contentcontainer_personalinfo;

    @InjectView(R.id.cv_hierarki_personalinfo)
    CardView cv_hierarki_personalinfo;

    @InjectView(R.id.ll_title_personalinfo)
    LinearLayout mTitleContainer;

    @InjectView(R.id.ll_contentatasan_personalinfo)
    LinearLayout ll_contentatasan_personalinfo;

    @InjectView(R.id.ll_contentbawahan_personalinfo)
    LinearLayout ll_contentbawahan_personalinfo;

    @InjectView(R.id.ll_content_rekan_personalinfo)
    LinearLayout ll_content_rekan_personalinfo;

    @InjectView(R.id.rl_contentpersonal_personalinfo)
    RelativeLayout rl_contentpersonal_personalinfo;

    @InjectView(R.id.rl_borderpersonal_personalinfo)
    RelativeLayout rl_borderpersonal_personalinfo;

    @InjectView(R.id.rl_borderpersonal1_personalinfo)
    RelativeLayout rl_borderpersonal1_personalinfo;

    @InjectView(R.id.rl_borderpersonal2_personalinfo)
    RelativeLayout rl_borderpersonal2_personalinfo;

    @InjectView(R.id.tv_name_personalinfo)
    CustomTextView tv_name_personalinfo;

    @InjectView(R.id.tv_jabatan_personalinfo)
    CustomTextView tv_jabatan_personalinfo;

    @InjectView(R.id.tv_statuspeg_personalinfo)
    CustomTextView tv_statuspeg_personalinfo;

    @InjectView(R.id.tv_alamatkantor_personalinfo)
    CustomTextView tv_alamatkantor_personalinfo;

    @InjectView(R.id.tv_tempatttl_personalinfo)
    CustomTextView tv_tempatttl_personalinfo;

    @InjectView(R.id.tv_genderkelamin_personalinfo)
    CustomTextView tv_genderkelamin_personalinfo;

    @InjectView(R.id.tv_alamat_personalinfo)
    CustomTextView tv_alamat_personalinfo;

    @InjectView(R.id.tv_telp_personalinfo)
    CustomTextView tv_telp_personalinfo;

    @InjectView(R.id.tv_title_personalinfo)
    CustomTextView tv_title_personalinfo;

    @InjectView(R.id.tv_atasan_personalinfo)
    CustomTextView tv_atasan_personalinfo;

    @InjectView(R.id.tv_personal_personalinfo)
    CustomTextView tv_personal_personalinfo;

    @InjectView(R.id.civ_pict_personalinfo)
    ShapedImageView civ_pict_personalinfo;

    @InjectView(R.id.civ_atasan_personalinfo)
    ShapedImageView civ_atasan_personalinfo;

    @InjectView(R.id.civ_personal_personalinfo)
    ShapedImageView civ_personal_personalinfo;

    @InjectView(R.id.rv_bawahan_personalinfo)
    RecyclerView rv_bawahan_personalinfo;

    @InjectView(R.id.rv_rekan_personalinfo)
    RecyclerView rv_rekan_personalinfo;

    private Context context;
    private View view;
    private Dialog progressDialog;
    private LinearLayoutManager rv_bawahan_personalinfo_llm;
    private BawahanListAdapter rv_bawahan_personalinfo_adapter;
    private LinearLayoutManager rv_rekan_personalinfo_llm;
    private RekanListAdapter rv_rekan_personalinfo_adapter;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean isExpand                    = true;

    private String npp;
    private PersonalInfoDao personalInfoDao, atasanInfoDao;
    private List<PersonalInfoDao> bawahanList = new ArrayList<>();
    private List<PersonalInfoDao> peerList = new ArrayList<>();
    private LoginDao loginDao;
    private Target loadedBitmap, loadedBitmap1;

    public DetailPersonalInfoFragment() {

    }

    @SuppressLint("ValidFragment")
    public DetailPersonalInfoFragment(Context context, String npp) {
        this.context = context;
        this.npp = npp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.width = (int) (Utility.getScreenWidth(this.context) * 0.97);
        windowParams.height = (int) (Utility.getScreenHeight(this.context) * 0.97);
        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_detailpersonal, container, false);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.white_view_corner_bg);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        loginDao = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.USER_DATA), LoginDao.class);

        abl_personalinfo.addOnOffsetChangedListener(this);

        this.toolbar_personalinfo.inflateMenu(R.menu.menu_personalinfo);

        startAlphaAnimation(this.tv_title_personalinfo, 0, View.INVISIBLE);

        setupDetailPersonalInfoProgressDialog();
        setupBawahanListView();
        setupPeerListView();

        civ_atasan_personalinfo.setOnClickListener(new ActionPictAtasanListener());
        civ_personal_personalinfo.setOnClickListener(new ActionPictPersonalListener());

        tv_telp_personalinfo.setOnClickListener(new ActionCallPhoneNumber());
        toolbar_personalinfo.setOnMenuItemClickListener(new ActionMenuClickHirarki());

        progressDialog.show();
        mainPresenter.postDetailPersonalInfo(loginDao.getNpp(),
                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                npp);
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

        super.onDestroy();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    @Subscribe
    public void onEventThread(DetailPersonalInfoEvent event) {
        personalInfoDao = event.getData().get(0);

        tv_name_personalinfo.setText(personalInfoDao.getNama());
        tv_jabatan_personalinfo.setText(personalInfoDao.getJabatan());
        tv_statuspeg_personalinfo.setText(personalInfoDao.getStatus_desc() + ", " + personalInfoDao.getUnit_desc());
        tv_alamatkantor_personalinfo.setText(personalInfoDao.getKantor_alamat());
        tv_tempatttl_personalinfo.setText(personalInfoDao.getTempatlahir() + ", " + personalInfoDao.getTanggallahir());
        tv_genderkelamin_personalinfo.setText(personalInfoDao.getAgama() + ", " + (personalInfoDao.getJenis_kelamin().equals("M") ? "Laki-laki" : "Perempuan"));
        tv_alamat_personalinfo.setText(this.personalInfoDao.getAlamat());
        tv_telp_personalinfo.setText(((this.personalInfoDao.getTelp1() != null) ? this.personalInfoDao.getTelp1() : "-") + ", " + ((this.personalInfoDao.getTelp2() != null) ? this.personalInfoDao.getTelp2() : "-"));
        if (!tv_telp_personalinfo.getText().toString().contains("-")) {
            tv_telp_personalinfo.setTextColor(this.context.getResources().getColor(R.color.colorBlueMicrosoft));
            tv_telp_personalinfo.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        tv_title_personalinfo.setText(personalInfoDao.getNama());

        if (this.loadedBitmap == null) this.loadedBitmap = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                civ_pict_personalinfo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
                civ_personal_personalinfo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        civ_pict_personalinfo.setTag(this.loadedBitmap);
        civ_personal_personalinfo.setTag(this.loadedBitmap);

        Picasso.with(this.context)
                .load(Config.URL_IMAGE + personalInfoDao.getUrlfoto())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap);

        this.tv_personal_personalinfo.setText(personalInfoDao.getNama());

        Log.d("retro", "nppAtasan" + personalInfoDao.getNpp_atasan());
        if (personalInfoDao.getNpp_atasan() == null) {
            ll_contentatasan_personalinfo.setVisibility(View.GONE);
            rl_borderpersonal2_personalinfo.setVisibility(View.GONE);
            peerList.clear();
            rv_bawahan_personalinfo_adapter.clearData();
            this.rl_borderpersonal_personalinfo.setVisibility(View.GONE);
            this.rv_rekan_personalinfo.setVisibility(View.GONE);
        }else {
            ll_contentatasan_personalinfo.setVisibility(View.VISIBLE);
            rl_borderpersonal2_personalinfo.setVisibility(View.VISIBLE);
            mainPresenter.postAtasanInfo(loginDao.getNpp(),
                    PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                    personalInfoDao.getNpp_atasan());

            mainPresenter.postPeerInfo(loginDao.getNpp(),
                    PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                    personalInfoDao.getNpp_atasan());
        }

        mainPresenter.postBawahanInfo(loginDao.getNpp(),
                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                personalInfoDao.getNpp());
    }

    @Subscribe
    public void onEventThread(AtasanInfoEvent event) {
        atasanInfoDao = event.getData().get(0);

        if (this.loadedBitmap1 == null) this.loadedBitmap1 = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                civ_atasan_personalinfo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, (height < width) ? height : width));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        civ_atasan_personalinfo.setTag(this.loadedBitmap1);

        Picasso.with(this.context)
                .load(Config.URL_IMAGE + atasanInfoDao.getUrlfoto())
                .placeholder(R.drawable.placeholder)
                .into(this.loadedBitmap1);

        this.tv_atasan_personalinfo.setText(atasanInfoDao.getNama());
    }

    @Subscribe
    public void onEventThread(BawahanInfoEvent event) {
        progressDialog.dismiss();
        rv_bawahan_personalinfo_adapter.clearData();
        bawahanList.clear();
        bawahanList = event.getData();
        rv_bawahan_personalinfo_adapter.addData(event.getData());
        if (event.getData().size() > 0) {
            this.ll_contentbawahan_personalinfo.setVisibility(View.VISIBLE);
            this.rl_borderpersonal1_personalinfo.setVisibility(View.VISIBLE);
        }else {
            this.ll_contentbawahan_personalinfo.setVisibility(View.GONE);
            this.rl_borderpersonal1_personalinfo.setVisibility(View.GONE);
        }

    }

    @Subscribe
    public void onEventThread(PeerInfoEvent event) {
        rv_rekan_personalinfo_adapter.clearData();
        peerList.clear();
        peerList = event.getData();
        for (int i = 0; i < peerList.size(); i++) {
            if (peerList.get(i).getNpp().equals(personalInfoDao.getNpp()))
                peerList.remove(i);
        }
        rv_rekan_personalinfo_adapter.addData(peerList);
        if (event.getData().size() == 0) {
            this.rl_borderpersonal_personalinfo.setVisibility(View.GONE);
            this.rv_rekan_personalinfo.setVisibility(View.GONE);
        }else {
            this.rl_borderpersonal_personalinfo.setVisibility(View.VISIBLE);
            this.rv_rekan_personalinfo.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        progressDialog.dismiss();
        if (event.getMessage().toLowerCase().contains("invalid token")) {
            progressDialog.dismiss();
            Snackbar.make(nsv_contentcontainer_personalinfo, "Telah login di device lain.", Snackbar.LENGTH_SHORT).show();
            ((MainActivity)context).LogoutFromAccount();
        }else {
            mainPresenter.postDetailPersonalInfo(loginDao.getNpp(),
                    PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                    npp);
        }
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(tv_title_personalinfo, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(tv_title_personalinfo, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void setupDetailPersonalInfoProgressDialog() {
        this.progressDialog = new Dialog(context);
        this.progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.progressDialog.setContentView(R.layout.loading_screen);
        this.progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.progressDialog.show();
    }

    private void setupBawahanListView() {
        rv_bawahan_personalinfo_llm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_bawahan_personalinfo.setHasFixedSize(true);
        rv_bawahan_personalinfo.setLayoutManager(rv_bawahan_personalinfo_llm);

        rv_bawahan_personalinfo_adapter = new BawahanListAdapter(context, bawahanList, this);
        rv_bawahan_personalinfo.setAdapter(rv_bawahan_personalinfo_adapter);
    }

    private void setupPeerListView() {
        rv_rekan_personalinfo_llm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_rekan_personalinfo.setHasFixedSize(true);
        rv_rekan_personalinfo.setLayoutManager(rv_rekan_personalinfo_llm);

        rv_rekan_personalinfo_adapter = new RekanListAdapter(context, peerList, this);
        rv_rekan_personalinfo.setAdapter(rv_rekan_personalinfo_adapter);
    }

    @Override
    public void onBawahanListAdapterCallback(int position) {
        isExpand = true;
        progressDialog.show();
        mainPresenter.postDetailPersonalInfo(loginDao.getNpp(),
                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                bawahanList.get(position).getNpp());
        nsv_contentcontainer_personalinfo.post(new Runnable() {
            @Override
            public void run() {
                abl_personalinfo.setExpanded(true, true);
                nsv_contentcontainer_personalinfo.scrollTo(0, 0);
            }
        });
    }

    @Override
    public void onRekanListAdapterCallback(int position) {
        isExpand = true;
        mainPresenter.postDetailPersonalInfo(loginDao.getNpp(),
                PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                peerList.get(position).getNpp());
        nsv_contentcontainer_personalinfo.post(new Runnable() {
            @Override
            public void run() {
                abl_personalinfo.setExpanded(true, true);
                nsv_contentcontainer_personalinfo.scrollTo(0, 0);
            }
        });
    }

    private class ActionPictAtasanListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            isExpand = true;
            progressDialog.show();
            mainPresenter.postDetailPersonalInfo(loginDao.getNpp(),
                    PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                    personalInfoDao.getNpp_atasan());
            nsv_contentcontainer_personalinfo.post(new Runnable() {
                @Override
                public void run() {
                    abl_personalinfo.setExpanded(true, true);
                    nsv_contentcontainer_personalinfo.scrollTo(0, 0);
                }
            });
        }
    }

    private class ActionPictPersonalListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            isExpand = true;
            progressDialog.show();
            mainPresenter.postDetailPersonalInfo(loginDao.getNpp(),
                    PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN),
                    personalInfoDao.getNpp());
            nsv_contentcontainer_personalinfo.post(new Runnable() {
                @Override
                public void run() {
                    abl_personalinfo.setExpanded(true, true);
                    nsv_contentcontainer_personalinfo.scrollTo(0, 0);
                }
            });
        }
    }

    private class ActionMenuClickHirarki implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_hirarki) {
                nsv_contentcontainer_personalinfo.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isExpand == true) {
                            isExpand = false;
                            abl_personalinfo.setExpanded(isExpand, true);
                            nsv_contentcontainer_personalinfo.scrollTo(0, cv_hierarki_personalinfo.getBottom());
                        }else{
                            isExpand = true;
                            abl_personalinfo.setExpanded(isExpand, true);
                            nsv_contentcontainer_personalinfo.scrollTo(0, 0);
                        }
                    }
                });

                return true;
            }

            return false;
        }
    }

    private class ActionCallPhoneNumber implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (personalInfoDao.getTelp1() != null || personalInfoDao.getTelp2() != null) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + (personalInfoDao.getTelp1() != null ? personalInfoDao.getTelp1() : personalInfoDao.getTelp2())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);
            }
        }
    }

}
