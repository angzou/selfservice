package com.jasamarga.selfservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by apridosandyasa on 12/29/16.
 */

public class BaseTabActivity extends TabActivity {

    private Dialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void showLoadingDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_screen);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void dismissLoadingDialog() {
        if (null == dialog) return;
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
