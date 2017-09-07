package com.jasamarga.selfservice.events;

import com.jasamarga.selfservice.models.LoginDao;

import java.util.List;

/**
 * Created by apridosandyasa on 12/28/16.
 */

public class LoginEvent extends BaseEvent {

    private List<LoginDao> data;

    public List<LoginDao> getData() {
        return data;
    }

    public void setData(List<LoginDao> data) {
        this.data = data;
    }

}
