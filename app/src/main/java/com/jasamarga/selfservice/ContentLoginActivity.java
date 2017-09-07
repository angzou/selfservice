package com.jasamarga.selfservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jasamarga.selfservice.customwiget.CustomButton;
import com.jasamarga.selfservice.customwiget.CustomEditText;
import com.jasamarga.selfservice.dagger.DaggerInjection;
import com.jasamarga.selfservice.events.ErrorEvent;
import com.jasamarga.selfservice.events.LoginEvent;
import com.jasamarga.selfservice.models.LoginDao;
import com.jasamarga.selfservice.presenters.MainPresenter;
import com.jasamarga.selfservice.utility.Config;
import com.jasamarga.selfservice.utility.PreferenceUtility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 12/6/16.
 */

public class ContentLoginActivity extends BaseActivity {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.llContentLogin)
    LinearLayout llContentLogin;

    @InjectView(R.id.edNpp)
    CustomEditText edNpp;

    @InjectView(R.id.edPass)
    CustomEditText edPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        edPass.setOnEditorActionListener(new LoginFromSoftKeyboard());
    }

    @OnClick(R.id.btnLogin)
    public void Login() {
        showLoadingDialog();
        if (!edNpp.getText().toString().equals("") && !edPass.getText().toString().equals("")) {
            mainPresenter.postLogin(edNpp.getText().toString(), edPass.getText().toString(), Config.LOGIN_API_KEY);
        }else{
            if (edNpp.getText().toString().equals("") && edPass.getText().toString().equals("")) {
                Snackbar.make(llContentLogin, "Mohon isi npp dan kata sandi.", Snackbar.LENGTH_SHORT).show();
            }else if (!edNpp.getText().toString().equals("") && edPass.getText().toString().equals("")) {
                Snackbar.make(llContentLogin, "Mohon isi kata sandi.", Snackbar.LENGTH_SHORT).show();
            }else{
                Snackbar.make(llContentLogin, "Mohon isi npp.", Snackbar.LENGTH_SHORT).show();
            }
            dismissLoadingDialog();
        }

    }

    @Subscribe
    public void onEventThread(LoginEvent event) {
        Log.d("TAG", "message " + event.getMessage());
        dismissLoadingDialog();
        Bundle loginData = new Bundle();
        loginData.putSerializable("loginData", event.getData().get(0));
        PreferenceUtility.getInstance().saveData(ContentLoginActivity.this, PreferenceUtility.API_TOKEN, event.getData().get(0).getApi_token());
        PreferenceUtility.getInstance().saveData(ContentLoginActivity.this, PreferenceUtility.USER_DATA, new Gson().toJson(event.getData().get(0), LoginDao.class));
        startActivity(new Intent(this, MainActivity.class).putExtra("bundleLogin", loginData));
        finish();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        if (event.getMessage().toLowerCase().contains("econnreset")) {
            mainPresenter.postLogin(edNpp.getText().toString(), edPass.getText().toString(), Config.LOGIN_API_KEY);
        }else{
            dismissLoadingDialog();
            Snackbar.make(llContentLogin, event.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private class LoginFromSoftKeyboard implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showLoadingDialog();
                mainPresenter.postLogin(edNpp.getText().toString(), edPass.getText().toString(), Config.LOGIN_API_KEY);
            }

            return true;
        }
    }

}
