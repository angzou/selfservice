package com.jasamarga.selfservice.events;

import com.jasamarga.selfservice.models.LoginDao;
import com.jasamarga.selfservice.models.PenerimaanDao;

import java.util.List;

/**
 * Created by apridosandyasa on 12/28/16.
 */

public class PenerimaanEvent extends BaseEvent {

    private List<PenerimaanDao> data;

    public List<PenerimaanDao> getData() {
        return data;
    }

    public void setData(List<PenerimaanDao> data) {
        this.data = data;
    }

}
