package com.jasamarga.selfservice.events;

import com.jasamarga.selfservice.models.PersonalInfoDao;

import java.util.List;

/**
 * Created by apridosandyasa on 12/29/16.
 */

public class PersonalInfoEvent extends BaseEvent {

    private List<PersonalInfoDao> data;

    public List<PersonalInfoDao> getData() {
        return data;
    }

    public void setData(List<PersonalInfoDao> data) {
        this.data = data;
    }

}
