package com.bitera.ethdemo.domain;

import lombok.Getter;


public class DefaultResponse {
    @Getter
    private String msg;

    public DefaultResponse(String msg){
        this.msg = msg;
    }
}

