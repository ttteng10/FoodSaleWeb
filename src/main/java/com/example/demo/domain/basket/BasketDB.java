package com.example.demo.domain.basket;

import lombok.Data;

@Data
public class BasketDB {

    private String memberId;
    private String itemNum;
    private int quantity;

    public BasketDB(){}

    public BasketDB(String memberId, String itemNum, int quantity) {
        this.memberId = memberId;
        this.itemNum = itemNum;
        this.quantity = quantity;
    }
}
