package com.jasamarga.selfservice.events;

/**
 * Created by dedepradana on 6/5/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class BaseEvent {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
