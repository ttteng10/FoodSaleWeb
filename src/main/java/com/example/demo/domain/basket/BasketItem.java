package com.example.demo.domain.basket;

import com.example.demo.domain.item.Item;
import lombok.Data;

@Data
public class BasketItem {
    private Item item;
    private Integer quantity;

    public BasketItem() {
    }

    public BasketItem(Item item, Integer quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
