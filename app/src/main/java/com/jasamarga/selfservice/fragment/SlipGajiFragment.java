package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.adapters.CustomSpinnerAdapter;
import com.jasamarga.selfservice.callback.RincianPenerimaanCallback;
import com.jasamarga.selfservice.callback.SlipGajiFragmentCallback;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.events.ErrorEvent;
import com.jasamarga.selfservice.events.PenerimaanEvent;
import com.jasamarga.selfservice.events.PeriodEvent;
import com.jasamarga.selfservice.events.PotonganEvent;
import com.jasamarga.selfservice.events.SantunanDukaEvent;
import com.jasamarga.selfservice.models.LoginDao;
import com.jasamarga.selfservice.models.PenerimaanDao;
import com.jasamarga.selfservice.models.PotonganDao;
import com.jasamarga.selfservice.models.SantunanDukaDao;
import com.jasamarga.selfservice.presenters.MainPresenter;
import com.jasamarga.selfservice.utility.PreferenceUtility;
import com.jasamarga.selfservice.utility.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 12/5/16.
 */

public class SlipGajiFragment extends Fragment implements RincianPenerimaanCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.abl_slipgaji)
    AppBarLayout abl_slipgaji;

    @InjectView(R.id.llContainerSlipGaji)
    LinearLayout llContainerSlipGaji;

    @InjectView(R.id.ll_title_slipgaji)
    LinearLayout ll_title_slipgaji;

    @InjectView(R.id.tv_header_slipgaji)
    CustomTextView tv_header_slipgaji;

    @InjectView(R.id.tvNamaPenerima)
    CustomTextView tvNamaPenerima;

    @InjectView(R.id.tvKantorPenerima)
    CustomTextView tvKantorPenerima;

    @InjectView(R.id.spBulanSlipGaji)
    Spinner spBulanSlipGaji;

    @InjectView(R.id.tvTotalPenerimaan)
    CustomTextView tvTotalPenerimaan;

    @InjectView(R.id.tvTotalPotongan)
    CustomTextView tvTotalPotongan;

    @InjectView(R.id.tvGajiBersih)
    CustomTextView tvGajiBersih;

    @InjectView(R.id.bottomSheetDetail)
    LinearLayout bottomSheetDetail;

    @InjectView(R.id.ivRincianPdptan)
    ImageView ivRincianPdptan;

    @InjectView(R.id.ivRindianPot)
    ImageView ivRindianPot;

    private Context context;
    private View view;
    private SlipGajiFragmentCallback callback;
    private BottomSheetBehavior bottomSheetBehavior;
    private RincianPendapatanFragment rincianPendapatanFragment;
    private RincianPotonganFragment rincianPotonganFragment;
    private Dialog progressDialog;
    private LoginDao loginDao;
    private List<PenerimaanDao> penerimaanDaoList = new ArrayList<>();
    private List<PotonganDao> potonganDaoList = new ArrayList<>();
    private List<String> periodDaoList = new ArrayList<>();
    private List<SantunanDukaDao> santunanDukaDaoList = new ArrayList<>();
    private int totalPenerimaan = 0, totalPotongan = 0, gajiBersih = 0, selectedPeriod = 0;

    public SlipGajiFragment() {

    }

    @SuppressLint("ValidFragment")
    public SlipGajiFragment(Context context, SlipGajiFragmentCallback listener) {
        this.context = context;
        this.callback = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.content_slipgaji, container, false);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        loginDao = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.USER_DATA), LoginDao.class);

        ll_title_slipgaji.setBackgroundColor(context.getResources().getColor(R.color.colorOrange400));
        tv_header_slipgaji.setTextColor(context.getResources().getColor(R.color.colorBlueMicrosoft));
        tv_header_slipgaji.setText("SLIP\nGAJI");

        float radius = tv_header_slipgaji.getTextSize() / 18;
        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.SOLID);
        tv_header_slipgaji.getPaint().setMaskFilter(filter);
        tvNamaPenerima.setText(loginDao.getNpp() + ", " + loginDao.getNama());
        tvKantorPenerima.setText("Payroll Group JM Kantor " + loginDao.getKantor_desc());

        setupProgressDialog();
        setupBulananSlipGaji();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onEventThread(PenerimaanEvent event) {
        penerimaanDaoList = event.getData();

        for (int i = 0; i < penerimaanDaoList.size(); i++) {
            totalPenerimaan = totalPenerimaan + penerimaanDaoList.get(i).getValue_penerimaan();
        }

        mainPresenter.postRincianPotongan(loginDao.getNpp(), periodDaoList.get(selectedPeriod), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN));
    }

    @Subscribe
    public void onEventThread(PotonganEvent event) {
        potonganDaoList = event.getData();

        for (int i = 0; i < potonganDaoList.size(); i++) {
            totalPotongan = totalPotongan + potonganDaoList.get(i).getValue_potongan();
        }

        mainPresenter.postSantunanDuka(loginDao.getNpp(), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN), periodDaoList.get(selectedPeriod));
    }

    @Subscribe
    public void onEventThread(PeriodEvent event) {
        periodDaoList.clear();
        for (int i = 0; i < event.getData().size(); i++) {
            if (event.getData().get(i).getName() != null)
                periodDaoList.add(event.getData().get(i).getName());
            else
                periodDaoList.add("");
        }

        // Creating adapter for spinner
        CustomSpinnerAdapter dataAdapter = new CustomSpinnerAdapter(context, android.R.layout.simple_spinner_item, periodDaoList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spBulanSlipGaji.setAdapter(dataAdapter);
        spBulanSlipGaji.setSelection(dataAdapter.getCount() - 1);
        spBulanSlipGaji.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressDialog.show();

                totalPenerimaan = 0;
                totalPotongan = 0;
                gajiBersih = 0;
                selectedPeriod = position;
                mainPresenter.postRincianPenerimaan(loginDao.getNpp(), periodDaoList.get(selectedPeriod), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setupMenuSlipGaji();
    }

    @Subscribe
    public void onEventThread(SantunanDukaEvent event) {
        santunanDukaDaoList = event.getData();
        gajiBersih = totalPenerimaan - totalPotongan;

        tvTotalPenerimaan.setText("Rp. " + Utility.setCurrency(totalPenerimaan));
        tvTotalPotongan.setText("Rp. " + Utility.setCurrency(totalPotongan));
        tvGajiBersih.setText("Rp. " + Utility.setCurrency(gajiBersih));

        HideProgressDialog();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        Log.d("retro", "code " + event.getCode());
        if (event.getMessage().toLowerCase().contains("invalid token")) {
            progressDialog.dismiss();
            Snackbar.make(llContainerSlipGaji, "Telah login di device lain.", Snackbar.LENGTH_SHORT).show();
            callback.LogoutFromInvalidToken();
        }else {
            mainPresenter.postPeriod(loginDao.getNpp(), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN));
        }
    }

    @OnClick(R.id.ll_rinc_pend_slipgaji)
    public void ShowRincianPendapatan() {
        progressDialog.show();
        rincianPendapatanFragment = new RincianPendapatanFragment(context, this, penerimaanDaoList, totalPenerimaan, totalPotongan, gajiBersih);
        rincianPendapatanFragment.show(getChildFragmentManager(), "rincianPendapatanFragment");
        //Snackbar.make(bottomSheetDetail, "Rincian Pendapatan", Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.ll_rinc_pot_slipgaji)
    public void ShowRincianPotongan() {
        progressDialog.show();
        rincianPotonganFragment = new RincianPotonganFragment(context, this, potonganDaoList, totalPenerimaan, totalPotongan, gajiBersih, santunanDukaDaoList);
        rincianPotonganFragment.show(getChildFragmentManager(), "rincianPotonganFragment");
        //Snackbar.make(bottomSheetDetail, "Rincian Potongan", Snackbar.LENGTH_SHORT).show();
    }

    private void setupProgressDialog() {
        progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.loading_screen);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
    }

    private void setupBulananSlipGaji() {
        progressDialog.show();

        mainPresenter.postPeriod(loginDao.getNpp(), PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.API_TOKEN));
    }

    private void setupMenuSlipGaji() {
        progressDialog.show();

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDetail);
        bottomSheetBehavior.setPeekHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24,
                getResources().getDisplayMetrics()));

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icon_rincian_gaji);
        Drawable d = new BitmapDrawable(getResources(), b);
        d.setColorFilter(getResources().getColor(R.color.colorRedAP), PorterDuff.Mode.SRC_ATOP);

        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_rincian_potongan);
        Drawable d1 = new BitmapDrawable(getResources(), b1);
        d1.setColorFilter(getResources().getColor(R.color.colorRedAP), PorterDuff.Mode.SRC_ATOP);

        ivRincianPdptan.setImageDrawable(d);
        ivRindianPot.setImageDrawable(d1);
    }

    @Override
    public void HideProgressDialog() {
        progressDialog.dismiss();
    }
}
