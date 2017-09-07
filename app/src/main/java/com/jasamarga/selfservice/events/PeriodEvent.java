package com.jasamarga.selfservice.events;

import com.jasamarga.selfservice.models.PeriodDao;

import java.util.List;

/**
 * Created by apridosandyasa on 12/28/16.
 */

public class PeriodEvent extends BaseEvent {

    private List<PeriodDao> data;

    public List<PeriodDao> getData() {
        return data;
    }

    public void setData(List<PeriodDao> data) {
        this.data = data;
    }

}
