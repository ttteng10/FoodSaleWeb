package com.example.demo.domain.basket;

import com.example.demo.domain.item.Item;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Basket {

    private String memberId;
    private Map<Item,Integer> basketMap;

    public Basket() {
    }

    public Basket(String memberId, Map<Item, Integer> basketMap) {
        this.memberId = memberId;
        this.basketMap = basketMap;
    }
}
