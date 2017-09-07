package com.jasamarga.selfservice.dagger.components;

import com.jasamarga.selfservice.ContentLoginActivity;
import com.jasamarga.selfservice.LoginActivity;
import com.jasamarga.selfservice.MainActivity;
import com.jasamarga.selfservice.dagger.modules.AppModule;
import com.jasamarga.selfservice.fragment.DetailPersonalInfoFragment;
import com.jasamarga.selfservice.fragment.LemburFragment;
import com.jasamarga.selfservice.fragment.LogoutFragment;
import com.jasamarga.selfservice.fragment.PersonalInfoFragment;
import com.jasamarga.selfservice.fragment.ProfilFragment;
import com.jasamarga.selfservice.fragment.RincianPendapatanFragment;
import com.jasamarga.selfservice.fragment.RincianPotonganFragment;
import com.jasamarga.selfservice.fragment.SantunanDukaFragment;
import com.jasamarga.selfservice.fragment.SlipGajiFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by apridosandyasa on 12/4/16.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(ContentLoginActivity contentLoginActivity);
    void inject(ProfilFragment profilFragment);
    void inject(SlipGajiFragment slipGajiFragment);
    void inject(RincianPendapatanFragment rincianPendapatanFragment);
    void inject(RincianPotonganFragment rincianPotonganFragment);
    void inject(SantunanDukaFragment santunanDukaFragment);
    void inject(PersonalInfoFragment personalInfoFragment);
    void inject(DetailPersonalInfoFragment detailPersonalInfoFragment);
    void inject(LogoutFragment logoutFragment);
    void inject(LemburFragment lemburFragment);

}
