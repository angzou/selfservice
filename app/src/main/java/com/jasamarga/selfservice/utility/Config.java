package com.jasamarga.selfservice.utility;

import static com.jasamarga.selfservice.BuildConfig.BASE_URL;

/**
 * Created by Dede Pradana on 1/15/2015.
 **/
public interface Config {
    String SERVER_NAME = BASE_URL;
    String API_POST_LOGIN = "/api/login";
    String API_POST_RINCIAN_PENERIMAAN = "/api/slipgaji/rincianpenerimaan";
    String API_POST_RINCIAN_POTONGAN = "/api/slipgaji/rincianpotongan";
    String API_POST_PERIOD = "/api/period";
    String API_POST_PERSONAL_INFO = "/api/personalinfo";
    String API_POST_PERSONAL_INFO_ATASAN = "/api/getatasan";
    String API_POST_PERSONAL_INFO_BAWAHAN = "/api/getbawahan";
    String API_POST_PERSONAL_INFO_PEER = "/api/getpeer";
    String API_POST_SANTUNAN_DUKA = "/api/santunanduka";
    String URL_IMAGE = "http://www.jasamarga.com/SMARTBOOK/";
    String LOGIN_API_KEY = "$2y$11$.4d4l4n61tb1rud13r0p4.sn8QnOtyjQOJWnwq9aDQhKZTPQXKANq";
}

