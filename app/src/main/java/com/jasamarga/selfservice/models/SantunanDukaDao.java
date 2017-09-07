package com.jasamarga.selfservice.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apridosandyasa on 3/2/17.
 */

public class SantunanDukaDao {

    @SerializedName("period")
    private String period;
    @SerializedName("keterangan")
    private String keterangan;

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getPeriod() {
        return period;
    }

    public String getKeterangan() {
        return keterangan;
    }

}
