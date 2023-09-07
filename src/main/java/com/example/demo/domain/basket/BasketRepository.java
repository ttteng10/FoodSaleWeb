package com.example.demo.domain.basket;

import com.example.demo.domain.item.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BasketRepository {

    private static final Map<String, Basket> store = new ConcurrentHashMap<>();

    public Basket save(Basket basket){
        store.put(basket.getMemberId(), basket);
        return basket;
    }

    public Basket findById(String memberId){
        return store.get(memberId);
    }
}
