package com.jasamarga.selfservice.events;

import com.jasamarga.selfservice.models.PersonalInfoDao;

import java.util.List;

/**
 * Created by apridosandyasa on 12/30/16.
 */

public class MorePersonalInfoEvent extends BaseEvent {

    private List<PersonalInfoDao> data;

    public List<PersonalInfoDao> getData() {
        return data;
    }

    public void setData(List<PersonalInfoDao> data) {
        this.data = data;
    }

}
