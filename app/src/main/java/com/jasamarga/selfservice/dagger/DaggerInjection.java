package com.jasamarga.selfservice.dagger;

import com.jasamarga.selfservice.dagger.components.AppComponent;
import com.jasamarga.selfservice.dagger.components.DaggerAppComponent;
import com.jasamarga.selfservice.dagger.modules.AppModule;

/**
 * Created by apridosandyasa on 12/5/16.
 */

public class DaggerInjection {
    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}