package com.jasamarga.selfservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jasamarga.selfservice.customwiget.CustomEditText;
import com.jasamarga.selfservice.events.ErrorEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 12/6/16.
 */

public class LupaPassActivity extends BaseActivity {

    @InjectView(R.id.edEmailForgot)
    CustomEditText edEmailForgot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lupapass);
    }

    @OnClick(R.id.btnSubmit)
    public void Submit() {

    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {

    }

}
