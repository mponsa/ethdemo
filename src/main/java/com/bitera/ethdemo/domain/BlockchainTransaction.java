package com.bitera.ethdemo.domain;


import lombok.Getter;
import lombok.Setter;

// en grl podrias haber imitado la estructura real de una tx: https://medium.com/@codetractio/inside-an-ethereum-transaction-fa94ffca912f
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
        this.toId = toId;
        // esto deberia venir desde config del app.prop:
        this.fromId = "0xE09BF1d2c3E4aBC3B906a16aaD203597Bf472F24";
        this.value = 0.01;
    }
}
