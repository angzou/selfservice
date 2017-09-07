package com.jasamarga.selfservice.dagger.modules;

import com.jasamarga.selfservice.utility.RestAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by apridosandyasa on 12/4/16.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    RestAPI providesRestApi() {
        return new RestAPI();
    }
}
