package com.jasamarga.selfservice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jasamarga.selfservice.customwiget.CustomTextView;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.events.ErrorEvent;
import com.jasamarga.selfservice.events.LoginEvent;
import com.jasamarga.selfservice.models.LoginDao;
import com.jasamarga.selfservice.utility.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 12/6/16.
 */

public class LoginActivity extends BaseTabActivity {

    @InjectView(android.R.id.tabhost)
    TabHost tabhost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        if (!PreferenceUtility.getInstance().loadDataString(LoginActivity.this, PreferenceUtility.API_TOKEN).equals("")) {
            Bundle loginData = new Bundle();
            loginData.putSerializable("loginData", new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.USER_DATA), LoginDao.class));
            startActivity(new Intent(this, MainActivity.class).putExtra("bundleLogin", loginData));
            LoginActivity.this.finish();
        }else{
            setupLoginTab();
        }
    }

    private void setupLoginTab() {
        TabHost.TabSpec tab1 = tabhost.newTabSpec("login");
        TabHost.TabSpec tab2 = tabhost.newTabSpec("lupa password");

        tab1.setIndicator(createTabView(LoginActivity.this, "PT. Jasa Marga Tbk"));
        tab1.setContent(new Intent(this, ContentLoginActivity.class));

//        tab2.setIndicator(createTabView(LoginActivity.this, "Lupa Password"));
//        tab2.setContent(new Intent(this, LupaPassActivity.class));

        tabhost.addTab(tab1);
//        tabhost.addTab(tab2);

        tabhost.setCurrentTab(0);
    }

    private static View createTabView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tabwidget, null);
        CustomTextView tv = (CustomTextView) view.findViewById(R.id.tvTitleTabWidget);
        tv.setText(text);
        return view;
    }
}
