package com.jasamarga.selfservice.events;

import com.jasamarga.selfservice.models.SantunanDukaDao;

import java.util.List;

/**
 * Created by apridosandyasa on 3/2/17.
 */

public class SantunanDukaEvent extends BaseEvent {

    private List<SantunanDukaDao> data;

    public List<SantunanDukaDao> getData() {
        return data;
    }

    public void setData(List<SantunanDukaDao> data) {
        this.data = data;
    }

}
