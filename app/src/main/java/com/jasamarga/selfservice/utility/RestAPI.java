package com.jasamarga.selfservice.utility;

import android.os.StrictMode;
import android.support.v4.util.ArrayMap;

import com.jasamarga.selfservice.events.AtasanInfoEvent;
import com.jasamarga.selfservice.events.BawahanInfoEvent;
import com.jasamarga.selfservice.events.DetailPersonalInfoEvent;
import com.jasamarga.selfservice.events.LoginEvent;
import com.jasamarga.selfservice.events.MorePersonalInfoEvent;
import com.jasamarga.selfservice.events.PeerInfoEvent;
import com.jasamarga.selfservice.events.PenerimaanEvent;
import com.jasamarga.selfservice.events.PeriodEvent;
import com.jasamarga.selfservice.events.PersonalInfoEvent;
import com.jasamarga.selfservice.events.PotonganEvent;
import com.jasamarga.selfservice.events.SantunanDukaEvent;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Part;
import rx.Observable;

/**
 * Created by apridosandyasa on 12/5/16.
 */

public class RestAPI {

    private interface RestService {

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_LOGIN)
        Observable<LoginEvent> postLogin(@Field("npp") String npp, @Field("password") String password, @Field("apikey") String apikey);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_RINCIAN_PENERIMAAN)
        Observable<PenerimaanEvent> postRincianPenerimaan(@Field("npp") String npp, @Field("period") String period, @Field("api_token") String api_token);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_RINCIAN_POTONGAN)
        Observable<PotonganEvent> postRincianPotongan(@Field("npp") String npp, @Field("period") String period, @Field("api_token") String api_token);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_PERIOD)
        Observable<PeriodEvent> postPeriod(@Field("npp") String npp, @Field("api_token") String api_token);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_PERSONAL_INFO)
        Observable<PersonalInfoEvent> postPersonalInfo(@Field("npp") String npp, @Field("api_token") String api_token, @Field("word") String word, @Field("start") String start, @Field("limit") String limit);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_PERSONAL_INFO)
        Observable<MorePersonalInfoEvent> postMorePersonalInfo(@Field("npp") String npp, @Field("api_token") String api_token, @Field("word") String word, @Field("start") String start, @Field("limit") String limit);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_PERSONAL_INFO_ATASAN)
        Observable<DetailPersonalInfoEvent> postDetailPersonalInfo(@Field("npp") String npp, @Field("api_token") String api_token, @Field("nppreq") String nppreq);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_PERSONAL_INFO_ATASAN)
        Observable<AtasanInfoEvent> postAtasanInfo(@Field("npp") String npp, @Field("api_token") String api_token, @Field("nppreq") String nppreq);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_PERSONAL_INFO_BAWAHAN)
        Observable<BawahanInfoEvent> postBawahanInfo(@Field("npp") String npp, @Field("api_token") String api_token, @Field("nppreq") String nppreq);

//        @Headers("Content-Type: " + "application/x-www-form-urlencoded")
        @FormUrlEncoded
        @POST(Config.API_POST_PERSONAL_INFO_PEER)
        Observable<PeerInfoEvent> postPeerInfo(@Field("npp") String npp, @Field("api_token") String api_token, @Field("nppreq") String nppreq);

        @FormUrlEncoded
        @POST(Config.API_POST_SANTUNAN_DUKA)
        Observable<SantunanDukaEvent> postSantunanDUka(@Field("npp") String npp, @Field("api_token") String api_token, @Field("period") String period);

    }

    Map<String, Object> params = new ArrayMap<>();

    private OkHttpClient okHttpClient;

    private RestAdapter.Builder builder;

    private RestAdapter restAdapter;

    public RestAPI() {
        setupRestAdapter();
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }

    public void setupRestAdapter() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //System.setProperty("http.keepAlive", "false");
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
            okHttpClient.setConnectionPool(new ConnectionPool(100,10000));

            if (builder == null) {
                builder = new RestAdapter.Builder()
                        .setEndpoint(Config.SERVER_NAME)
                        .setClient(new OkClient(okHttpClient))
                        .setLogLevel(RestAdapter.LogLevel.FULL);

                restAdapter = builder.build();
            }
        }
    }

    public Observable<LoginEvent> postLogin(String npp, String password, String apikey) {
        return getRestAdapter().create(RestService.class).postLogin(npp, password, apikey).cache();
    }

    public Observable<PenerimaanEvent> postRincianPenerimaan(String npp, String period, String api_token) {
        return getRestAdapter().create(RestService.class).postRincianPenerimaan(npp, period, api_token).cache();
    }

    public Observable<PotonganEvent> postRincianPotongan(String npp, String period, String api_token) {
        return getRestAdapter().create(RestService.class).postRincianPotongan(npp, period, api_token).cache();
    }

    public Observable<PeriodEvent> postPeriod(String npp, String api_token) {
        return getRestAdapter().create(RestService.class).postPeriod(npp, api_token).cache();
    }

    public Observable<PersonalInfoEvent> postPersonalInfo(String npp, String api_token, String word, String start, String limit) {
        return getRestAdapter().create(RestService.class).postPersonalInfo(npp, api_token, word, start, limit).cache();
    }

    public Observable<MorePersonalInfoEvent> postMorePersonalInfo(String npp, String api_token, String word, String start, String limit) {
        return getRestAdapter().create(RestService.class).postMorePersonalInfo(npp, api_token, word, start, limit).cache();
    }

    public Observable<DetailPersonalInfoEvent> postDetailPersonalInfo(String npp, String api_token, String nppreq) {
        return getRestAdapter().create(RestService.class).postDetailPersonalInfo(npp, api_token, nppreq).cache();
    }

    public Observable<AtasanInfoEvent> postAtasanInfo(String npp, String api_token, String nppreq) {
        return getRestAdapter().create(RestService.class).postAtasanInfo(npp, api_token, nppreq).cache();
    }

    public Observable<BawahanInfoEvent> postBawahanInfo(String npp, String api_token, String nppreq) {
        return getRestAdapter().create(RestService.class).postBawahanInfo(npp, api_token, nppreq).cache();
    }

    public Observable<PeerInfoEvent> postPeerInfo(String npp, String api_token, String nppreq) {
        return getRestAdapter().create(RestService.class).postPeerInfo(npp, api_token, nppreq).cache();
    }

    public Observable<SantunanDukaEvent> postSantunanDuka(String npp, String api_token, String period) {
        return getRestAdapter().create(RestService.class).postSantunanDUka(npp, api_token, period);
    }
    
}
