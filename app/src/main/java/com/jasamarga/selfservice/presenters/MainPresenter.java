package com.jasamarga.selfservice.presenters;

import com.jasamarga.selfservice.events.AtasanInfoEvent;
import com.jasamarga.selfservice.events.BawahanInfoEvent;
import com.jasamarga.selfservice.events.DetailPersonalInfoEvent;
import com.jasamarga.selfservice.events.ErrorEvent;
import com.jasamarga.selfservice.events.LoginEvent;
import com.jasamarga.selfservice.events.MorePersonalInfoEvent;
import com.jasamarga.selfservice.events.PeerInfoEvent;
import com.jasamarga.selfservice.events.PenerimaanEvent;
import com.jasamarga.selfservice.events.PeriodEvent;
import com.jasamarga.selfservice.events.PersonalInfoEvent;
import com.jasamarga.selfservice.events.PotonganEvent;
import com.jasamarga.selfservice.events.SantunanDukaEvent;
import com.jasamarga.selfservice.utility.RestAPI;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by apridosandyasa on 12/5/16.
 */

public class MainPresenter {

    RestAPI restAPI;

    @Inject
    public MainPresenter(RestAPI restAPI) {
        this.restAPI = restAPI;
    }

    public void postLogin(String npp, String password, String apikey) {
        restAPI.postLogin(npp, password, apikey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(LoginEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postRincianPenerimaan(String npp, String period, String api_token) {
        restAPI.postRincianPenerimaan(npp, period, api_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PenerimaanEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PenerimaanEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postRincianPotongan(String npp, String period, String api_token) {
        restAPI.postRincianPotongan(npp, period, api_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PotonganEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PotonganEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postPeriod(String npp, String api_token) {
        restAPI.postPeriod(npp, api_token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PeriodEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PeriodEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postPersonalInfo(String npp, String api_token, String word, String start, String limit) {
        restAPI.postPersonalInfo(npp, api_token, word, start, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PersonalInfoEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PersonalInfoEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postMorePersonalInfo(String npp, String api_token, String word, String start, String limit) {
        restAPI.postMorePersonalInfo(npp, api_token, word, start, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MorePersonalInfoEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(MorePersonalInfoEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postDetailPersonalInfo(String npp, String api_token, String nppreq) {
        restAPI.postDetailPersonalInfo(npp, api_token, nppreq).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DetailPersonalInfoEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(DetailPersonalInfoEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postAtasanInfo(String npp, String api_token, String nppreq) {
        restAPI.postAtasanInfo(npp, api_token, nppreq).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AtasanInfoEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(AtasanInfoEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postBawahanInfo(String npp, String api_token, String nppreq) {
        restAPI.postBawahanInfo(npp, api_token, nppreq).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BawahanInfoEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(BawahanInfoEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postPeerInfo(String npp, String api_token, String nppreq) {
        restAPI.postPeerInfo(npp, api_token, nppreq).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PeerInfoEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(PeerInfoEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postSantunanDuka(String npp, String api_token, String period) {
        restAPI.postSantunanDuka(npp, api_token, period).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SantunanDukaEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(SantunanDukaEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

}
