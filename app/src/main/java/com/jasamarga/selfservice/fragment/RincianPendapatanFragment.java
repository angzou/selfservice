package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.callback.RincianPenerimaanCallback;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.models.PenerimaanDao;
import com.jasamarga.selfservice.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 12/6/16.
 */

public class RincianPendapatanFragment extends BottomSheetDialogFragment {

    @InjectView(R.id.llKompensasiTetap)
    LinearLayout llKompensasiTetap;

    @InjectView(R.id.llKompensasiLain)
    LinearLayout llKompensasiLain;

    @InjectView(R.id.llTunjangan)
    LinearLayout llTunjangan;

    @InjectView(R.id.llTunjanganLain)
    LinearLayout llTunjanganLain;

    @InjectView(R.id.llTotalRincianPenerimaan)
    LinearLayout llTotalRincianPenerimaan;

    @InjectView(R.id.tvTotalGaji)
    CustomTextView tvTotalGaji;

    @InjectView(R.id.tvTotalPotongan)
    CustomTextView tvTotalPotongan;

    @InjectView(R.id.tvPenerimaanBersih)
    CustomTextView tvPenerimaanBersih;

    private CoordinatorLayout.LayoutParams params;
    private CoordinatorLayout.Behavior behavior;
    private Context context;
    private RincianPenerimaanCallback callback;
    private List<PenerimaanDao> penerimaanDaoList = new ArrayList<>();
    private List<PenerimaanDao> kompensasiTetapList = new ArrayList<>();
    private List<PenerimaanDao> kompensasiLainList = new ArrayList<>();
    private List<PenerimaanDao> tunjanganTetapList = new ArrayList<>();
    private List<PenerimaanDao> tunjanganLainList = new ArrayList<>();

    private int kompenTetapCount = 0;
    private int tunjTetapCount = 0;
    private int tunjLainCount = 0;
    private int totalPenerimaan = 0;
    private int totalGaji = 0;
    private int totalPotongan = 0;
    private int penerimaanBersih = 0;

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }else if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                setAlwaysExpanded();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    public RincianPendapatanFragment() {

    }

    @SuppressLint("ValidFragment")
    public RincianPendapatanFragment(Context context, RincianPenerimaanCallback listener, List<PenerimaanDao> penerimaanDaoList, int totalGaji, int totalPotongan, int penerimaanBersih) {
        this.context = context;
        this.callback = listener;
        this.penerimaanDaoList = penerimaanDaoList;
        this.totalGaji = totalGaji;
        this.totalPotongan = totalPotongan;
        this.penerimaanBersih = penerimaanBersih;
        setupKompensasiTetapList();
        setupKompensasiLainList();
        setupTunjanganTetapList();
        setupTunjanganLainList();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.content_rincian_pendapatan, null);
        dialog.setContentView(view);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        params = (CoordinatorLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        behavior = params.getBehavior();
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
            setupKompensasiTetapUI();
            setupKompensasiLainUI();
            setupTunjanganTetapUI();
            setupTunjanganLainUI();
        }
    }

    private void setAlwaysExpanded() {
        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void setupKompensasiTetapList() {
        for (int i = 0; i < penerimaanDaoList.size(); i++) {
            if (penerimaanDaoList.get(i).getNama() != null) {
                if (penerimaanDaoList.get(i).getNama().equals("Kompensasi Bulanan Tetap")) {
                    kompensasiTetapList.add(penerimaanDaoList.get(i));
                }
            }
        }
    }

    private void setupKompensasiLainList() {
        for (int i = 0; i < penerimaanDaoList.size(); i++) {
            if (penerimaanDaoList.get(i).getNama() != null) {
                if (penerimaanDaoList.get(i).getNama().equals("Kompensasi Lainnya")) {
                    kompensasiLainList.add(penerimaanDaoList.get(i));
                }
            }
        }
    }

    private void setupTunjanganTetapList() {
        for (int i = 0; i < penerimaanDaoList.size(); i++) {
            if (penerimaanDaoList.get(i).getNama() != null) {
                if (penerimaanDaoList.get(i).getNama().equals("Tunjangan Lainnya")) {
                    tunjanganTetapList.add(penerimaanDaoList.get(i));
                }
            }
        }
    }

    private void setupTunjanganLainList() {
        for (int i = 0; i < penerimaanDaoList.size(); i++) {
            if (penerimaanDaoList.get(i).getNama() != null) {
                if (penerimaanDaoList.get(i).getNama().equals("Lain-lain")) {
                    tunjanganLainList.add(penerimaanDaoList.get(i));
                }
            }
        }
    }

    private void setupKompensasiTetapUI() {
        llKompensasiTetap.removeAllViews();
        for (int i = 0; i < kompensasiTetapList.size() + 1; i++) {
            View kompensasiTetapForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_rincian_pendapatan, null);
            CustomTextView tvNumberRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvNumberRow);
            CustomTextView tvTitleRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleRow);
            CustomTextView tvDetailRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailRow);
            CustomTextView tvTitleTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleTotalRow);
            CustomTextView tvDetailTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailTotalRow);
            if (i < kompensasiTetapList.size()) {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.GONE);
                tvNumberRow.setText("" + (i + 1) + ". ");
                tvTitleRow.setText(kompensasiTetapList.get(i).getReporting_name());
                tvDetailRow.setText("Rp. " + Utility.setCurrency(kompensasiTetapList.get(i).getValue_penerimaan()));
                kompenTetapCount = kompenTetapCount + kompensasiTetapList.get(i).getValue_penerimaan();
            }else {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llDetailRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.VISIBLE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.VISIBLE);
                tvTitleTotalRow.setText("Sub Total   ");
                tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(kompenTetapCount));
            }
            llKompensasiTetap.addView(kompensasiTetapForm);
        }
    }

    private void setupKompensasiLainUI() {
        llKompensasiLain.removeAllViews();
        for (int i = 0; i < kompensasiLainList.size() + 1; i++) {
            View kompensasiTetapForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_rincian_pendapatan, null);
            CustomTextView tvNumberRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvNumberRow);
            CustomTextView tvTitleRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleRow);
            CustomTextView tvDetailRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailRow);
            CustomTextView tvTitleTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleTotalRow);
            CustomTextView tvDetailTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailTotalRow);
            if (i < kompensasiLainList.size()) {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.GONE);
                tvNumberRow.setText("" + (i + 1) + ". ");
                tvTitleRow.setText(kompensasiLainList.get(i).getReporting_name());
                tvDetailRow.setText("Rp. " + Utility.setCurrency(kompensasiLainList.get(i).getValue_penerimaan()));
                kompenTetapCount = kompenTetapCount + kompensasiLainList.get(i).getValue_penerimaan();
            }else {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llDetailRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.VISIBLE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.VISIBLE);
                tvTitleTotalRow.setText("Sub Total (a + b)   ");
                tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(kompenTetapCount));
            }
            llKompensasiLain.addView(kompensasiTetapForm);
        }
    }

    private void setupTunjanganTetapUI() {
        llTunjangan.removeAllViews();
        for (int i = 0; i < tunjanganTetapList.size() + 1; i++) {
            View tunjanganTetapForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_rincian_pendapatan, null);
            CustomTextView tvNumberRow = (CustomTextView) tunjanganTetapForm.findViewById(R.id.tvNumberRow);
            CustomTextView tvTitleRow = (CustomTextView) tunjanganTetapForm.findViewById(R.id.tvTitleRow);
            CustomTextView tvDetailRow = (CustomTextView) tunjanganTetapForm.findViewById(R.id.tvDetailRow);
            CustomTextView tvTitleTotalRow = (CustomTextView) tunjanganTetapForm.findViewById(R.id.tvTitleTotalRow);
            CustomTextView tvDetailTotalRow = (CustomTextView) tunjanganTetapForm.findViewById(R.id.tvDetailTotalRow);
            if (i < tunjanganTetapList.size()) {
                ((LinearLayout) tunjanganTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.GONE);
                ((LinearLayout) tunjanganTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.GONE);
                tvNumberRow.setText("" + (i + 1) + ". ");
                tvTitleRow.setText(tunjanganTetapList.get(i).getReporting_name());
                tvDetailRow.setText("Rp. " + Utility.setCurrency(tunjanganTetapList.get(i).getValue_penerimaan()));
                tunjTetapCount = tunjTetapCount + tunjanganTetapList.get(i).getValue_penerimaan();
            }else {
                ((LinearLayout) tunjanganTetapForm.findViewById(R.id.llDetailRow)).setVisibility(View.GONE);
                ((LinearLayout) tunjanganTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.VISIBLE);
                ((LinearLayout) tunjanganTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.VISIBLE);
                tvTitleTotalRow.setText("Sub Total   ");
                tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(tunjTetapCount));
            }
            llTunjangan.addView(tunjanganTetapForm);
        }
    }

    private void setupTunjanganLainUI() {
        llTunjanganLain.removeAllViews();
        for (int i = 0; i < tunjanganLainList.size() + 1; i++) {
            View tunjanganLainForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_rincian_pendapatan, null);
            CustomTextView tvNumberRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvNumberRow);
            CustomTextView tvTitleRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvTitleRow);
            CustomTextView tvDetailRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvDetailRow);
            CustomTextView tvTitleTotalRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvTitleTotalRow);
            CustomTextView tvDetailTotalRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvDetailTotalRow);
            if (i < tunjanganLainList.size()) {
                ((LinearLayout) tunjanganLainForm.findViewById(R.id.llBorderRow)).setVisibility(View.GONE);
                ((LinearLayout) tunjanganLainForm.findViewById(R.id.llTotalRow)).setVisibility(View.GONE);
                tvNumberRow.setText("" + (i + 1) + ". ");
                tvTitleRow.setText(tunjanganLainList.get(i).getReporting_name());
                tvDetailRow.setText("Rp. " + Utility.setCurrency(tunjanganLainList.get(i).getValue_penerimaan()));
                tunjLainCount = tunjLainCount + tunjanganLainList.get(i).getValue_penerimaan();
            }else {
                ((LinearLayout) tunjanganLainForm.findViewById(R.id.llDetailRow)).setVisibility(View.GONE);
                ((LinearLayout) tunjanganLainForm.findViewById(R.id.llBorderRow)).setVisibility(View.VISIBLE);
                ((LinearLayout) tunjanganLainForm.findViewById(R.id.llTotalRow)).setVisibility(View.VISIBLE);
                tvTitleTotalRow.setText("Sub Total   ");
                tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(tunjLainCount));
            }
            llTunjanganLain.addView(tunjanganLainForm);
        }

        totalPenerimaan = kompenTetapCount + tunjTetapCount + tunjLainCount;
        setupTotalPenerimaanUI();
        callback.HideProgressDialog();
    }

    private void setupTotalPenerimaanUI() {
        llTotalRincianPenerimaan.removeAllViews();
        View tunjanganLainForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_rincian_pendapatan, null);
        CustomTextView tvNumberRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvNumberRow);
        CustomTextView tvTitleRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvTitleRow);
        CustomTextView tvDetailRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvDetailRow);
        CustomTextView tvTitleTotalRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvTitleTotalRow);
        CustomTextView tvDetailTotalRow = (CustomTextView) tunjanganLainForm.findViewById(R.id.tvDetailTotalRow);
        ((LinearLayout) tunjanganLainForm.findViewById(R.id.llDetailRow)).setVisibility(View.GONE);
        ((LinearLayout) tunjanganLainForm.findViewById(R.id.llBorderRow)).setVisibility(View.VISIBLE);
        ((LinearLayout) tunjanganLainForm.findViewById(R.id.llTotalRow)).setVisibility(View.VISIBLE);
        tvTitleTotalRow.setText("Total   ");
        tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(totalPenerimaan));
        llTotalRincianPenerimaan.addView(tunjanganLainForm);
        tvTotalGaji.setText("Rp. " + Utility.setCurrency(totalGaji));
        tvTotalPotongan.setText("Rp. " + Utility.setCurrency(totalPotongan));
        tvPenerimaanBersih.setText("Rp. " + Utility.setCurrency(penerimaanBersih));
    }
}
