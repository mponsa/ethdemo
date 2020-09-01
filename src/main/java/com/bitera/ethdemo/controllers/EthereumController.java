package com.bitera.ethdemo.controllers;

import java.util.HashMap;
import java.util.Map;

public interface EthereumController {
    final Map<String, String> accounts = new HashMap<String,String>(){{
        put("0xE09BF1d2c3E4aBC3B906a16aaD203597Bf472F24","a0cce92cf7d43649a5410b23466931f51f0309089b6d27a2ab3cd4d016ce31eb");
    }};

    public void sendEth(String addr);
}
