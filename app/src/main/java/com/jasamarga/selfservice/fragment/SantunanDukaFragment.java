package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bluejamesbond.text.DocumentView;
import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.models.SantunanDukaDao;
import com.jasamarga.selfservice.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 12/12/16.
 */

public class SantunanDukaFragment extends DialogFragment {

    @InjectView(R.id.llSantunanDuka)
    LinearLayout llSantunanDuka;

    private Context context;
    private View view;

    private List<SantunanDukaDao> santunanDukaDaoList = new ArrayList<>();

    public SantunanDukaFragment() {

    }

    @SuppressLint("ValidFragment")
    public SantunanDukaFragment(Context context, List<SantunanDukaDao> santunanDukaDaoList) {
        this.context = context;
        this.santunanDukaDaoList = santunanDukaDaoList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.view = inflater.inflate(R.layout.content_santunan_duka, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_transparant_bg);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        llSantunanDuka.removeAllViews();
        for (int i = 0; i < santunanDukaDaoList.size(); i++) {
            View kompensasiTetapForm = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_santunan_duka, null);
            DocumentView tvTitleRow = (DocumentView) kompensasiTetapForm.findViewById(R.id.tvTitleRow);
            tvTitleRow.setText(santunanDukaDaoList.get(i).getKeterangan());
            llSantunanDuka.addView(kompensasiTetapForm);
        }
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }
}
