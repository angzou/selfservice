package com.jasamarga.selfservice.events;

/**
 * Created by dedepradana on 4/8/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ErrorEvent {

    private String message;
    private int code;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
