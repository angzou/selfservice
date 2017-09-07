package com.jasamarga.selfservice.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluejamesbond.text.DocumentView;
import com.jasamarga.selfservice.R;
import com.jasamarga.selfservice.callback.LogoutFragmentCallback;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.utility.Utility;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 1/6/17.
 */

public class LogoutFragment extends DialogFragment {

    @InjectView(R.id.tvTitleLogout)
    CustomTextView tvTitleLogout;

    private Context context;
    private View view;
    private LogoutFragmentCallback callback;
    private String title;
    private int mode;

    public LogoutFragment() {

    }

    @SuppressLint("ValidFragment")
    public LogoutFragment(Context context, String title, int mode, LogoutFragmentCallback listener) {
        this.context = context;
        this.title = title;
        this.mode = mode;
        this.callback = listener;
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

        this.view = inflater.inflate(R.layout.content_logout, container, false);

        return this.view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.orange_view_corner_bg);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        tvTitleLogout.setText(title);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = (int) (Utility.getScreenWidth(this.context) * 0.77);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }

    @OnClick(R.id.btnOk)
    public void Logout() {
        if (this.mode == 0) {
            callback.LogoutFromAccount();
        }else{
            callback.QuitApplication();
        }
    }

    @OnClick(R.id.btnCancel)
    public void Dismiss() {
        dismiss();
    }

}
