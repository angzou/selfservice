package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.callback.RincianPenerimaanCallback;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.models.PotonganDao;
import com.jasamarga.selfservice.models.SantunanDukaDao;
import com.jasamarga.selfservice.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 12/6/16.
 */

public class RincianPotonganFragment extends BottomSheetDialogFragment {

    @InjectView(R.id.llPotonganTetap)
    LinearLayout llPotonganTetap;

    @InjectView(R.id.llPotonganLain)
    LinearLayout llPotonganLain;

    @InjectView(R.id.llTotalRincianPotongan)
    LinearLayout llTotalRincianPotongan;

    @InjectView(R.id.tvTotalGaji)
    CustomTextView tvTotalGaji;

    @InjectView(R.id.tvTotalPotongan)
    CustomTextView tvTotalPotongan;

    @InjectView(R.id.tvPenerimaanBersih)
    CustomTextView tvPenerimaanBersih;

    private CoordinatorLayout.LayoutParams params;
    private CoordinatorLayout.Behavior behavior;
    private Context context;
    private SantunanDukaFragment santunanDukaFragment;
    private RincianPenerimaanCallback callback;
    private List<PotonganDao> potonganDaoList = new ArrayList<>();
    private List<PotonganDao> potonganTetapList = new ArrayList<>();
    private List<PotonganDao> potonganLainList = new ArrayList<>();
    private List<SantunanDukaDao> santunanDukaDaoList = new ArrayList<>();

    private int potTetapCount = 0;
    private int potLainCount = 0;
    private int totalPotongan = 0;
    private int totalGaji = 0;
    private int totalPotongan1 = 0;
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

    public RincianPotonganFragment() {

    }

    @SuppressLint("ValidFragment")
    public RincianPotonganFragment(Context context, RincianPenerimaanCallback listener, List<PotonganDao> potonganDaoList, int totalGaji, int totalPotongan1, int penerimaanBersih, List<SantunanDukaDao> santunanDukaDaoList) {
        this.context = context;
        this.callback = listener;
        this.potonganDaoList = potonganDaoList;
        this.totalGaji = totalGaji;
        this.totalPotongan1 = totalPotongan1;
        this.penerimaanBersih = penerimaanBersih;
        this.santunanDukaDaoList = santunanDukaDaoList;
        setupPotonganTetapList();
        setupPotonganLainList();
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.content_rincian_potongan, null);
        dialog.setContentView(view);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        params = (CoordinatorLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        behavior = params.getBehavior();
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(bottomSheetCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
            setupPotonganTetapUI();
            setupPotonganLainUI();
        }
    }

    private void setAlwaysExpanded() {
        ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void setupPotonganTetapList() {
        for (int i = 0; i < potonganDaoList.size(); i++) {
            if (potonganDaoList.get(i).getNama() != null) {
                if (potonganDaoList.get(i).getNama().equals("Ketidakhadiran, Iuran dan Pajak")) {
                    potonganTetapList.add(potonganDaoList.get(i));
                }
            }
        }
    }

    private void setupPotonganLainList() {
        for (int i = 0; i < potonganDaoList.size(); i++) {
            if (potonganDaoList.get(i).getNama() != null) {
                if (potonganDaoList.get(i).getNama().equals("Lain-lain")) {
                    potonganLainList.add(potonganDaoList.get(i));
                }
            }
        }
    }

    private void setupPotonganTetapUI() {
        llPotonganTetap.removeAllViews();
        for (int i = 0; i < potonganTetapList.size() + 1; i++) {
            View kompensasiTetapForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_rincian_pendapatan, null);
            CustomTextView tvNumberRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvNumberRow);
            CustomTextView tvTitleRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleRow);
            CustomTextView tvDetailRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailRow);
            CustomTextView tvTitleTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleTotalRow);
            CustomTextView tvDetailTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailTotalRow);
            if (i < potonganTetapList.size()) {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.GONE);
                tvNumberRow.setText("" + (i + 1) + ". ");
                tvTitleRow.setText(potonganTetapList.get(i).getReporting_name());
                tvDetailRow.setText("Rp. " + Utility.setCurrency(potonganTetapList.get(i).getValue_potongan()));
                potTetapCount = potTetapCount + potonganTetapList.get(i).getValue_potongan();
                if (potonganTetapList.get(i).getReporting_name().equals("Pot. Santunan Duka")) {
                    tvTitleRow.setTextColor(context.getResources().getColor(R.color.colorBlueMicrosoft));
                    tvTitleRow.setPaintFlags(tvTitleRow.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
                    tvTitleRow.setOnClickListener(new ShowSantunaDukaView());
                }
            }else {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llDetailRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.VISIBLE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.VISIBLE);
                tvTitleTotalRow.setText("Sub Total   ");
                tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(potTetapCount));
            }
            llPotonganTetap.addView(kompensasiTetapForm);
        }
    }

    private void setupPotonganLainUI() {
        llPotonganLain.removeAllViews();
        for (int i = 0; i < potonganLainList.size() + 1; i++) {
            View kompensasiTetapForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_rincian_pendapatan, null);
            CustomTextView tvNumberRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvNumberRow);
            CustomTextView tvTitleRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleRow);
            CustomTextView tvDetailRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailRow);
            CustomTextView tvTitleTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvTitleTotalRow);
            CustomTextView tvDetailTotalRow = (CustomTextView) kompensasiTetapForm.findViewById(R.id.tvDetailTotalRow);
            if (i < potonganLainList.size()) {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.GONE);
                tvNumberRow.setText("" + (i + 1) + ". ");
                tvTitleRow.setText(potonganLainList.get(i).getReporting_name());
                tvDetailRow.setText("Rp. " + Utility.setCurrency(potonganLainList.get(i).getValue_potongan()));
                potLainCount = potLainCount + potonganLainList.get(i).getValue_potongan();
            }else {
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llDetailRow)).setVisibility(View.GONE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llBorderRow)).setVisibility(View.VISIBLE);
                ((LinearLayout) kompensasiTetapForm.findViewById(R.id.llTotalRow)).setVisibility(View.VISIBLE);
                tvTitleTotalRow.setText("Sub Total   ");
                tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(potLainCount));
            }
            llPotonganLain.addView(kompensasiTetapForm);
        }

        totalPotongan = potTetapCount + potLainCount;
        setupTotalPotonganUI();
        callback.HideProgressDialog();
    }

    private void setupTotalPotonganUI() {
        llTotalRincianPotongan.removeAllViews();
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
        tvDetailTotalRow.setText("Rp. " + Utility.setCurrency(totalPotongan));
        llTotalRincianPotongan.addView(tunjanganLainForm);
        tvTotalGaji.setText("Rp. " + Utility.setCurrency(totalGaji));
        tvTotalPotongan.setText("Rp. " + Utility.setCurrency(totalPotongan1));
        tvPenerimaanBersih.setText("Rp. " + Utility.setCurrency(penerimaanBersih));
    }

    private void setupPotonganDukaUI(int pos) {
        View view = llPotonganTetap.getChildAt(pos);
        CustomTextView tvTitleRow = (CustomTextView) view.findViewById(R.id.tvTitleRow);
        tvTitleRow.setTextColor(context.getResources().getColor(R.color.colorBlueMicrosoft));
        tvTitleRow.setPaintFlags(tvTitleRow.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
        tvTitleRow.setOnClickListener(new ShowSantunaDukaView());
    }

    private class ShowSantunaDukaView implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            santunanDukaFragment = new SantunanDukaFragment(context, santunanDukaDaoList);
            santunanDukaFragment.show(getChildFragmentManager(), "santunanDukaFragment");
        }
    }

}
