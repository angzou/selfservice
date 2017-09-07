package com.jasamarga.selfservice.events;

import com.jasamarga.selfservice.models.PotonganDao;

import java.util.List;

/**
 * Created by apridosandyasa on 12/28/16.
 */

public class PotonganEvent extends BaseEvent {

    private List<PotonganDao> data;

    public List<PotonganDao> getData() {
        return data;
    }

    public void setData(List<PotonganDao> data) {
        this.data = data;
    }

}
