package com.bitera.ethdemo.domain;


import lombok.Getter;
import lombok.Setter;


public class BlockchainTransaction {
    @Getter
    private String fromId;
    @Getter
    private String toId;
    @Getter
    private Double value;
    @Getter
    @Setter
    private Boolean accepted;
    @Getter
    @Setter
    private String id;

    public BlockchainTransaction(String toId){
        this.fromId = "0xE09BF1d2c3E4aBC3B906a16aaD203597Bf472F24";
        this.toId = toId;
        this.value = 0.01;
    }
}
