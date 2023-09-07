package com.example.demo.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<String,Item> store = new HashMap<>();

    public Item save(Item item){
        store.put(item.getItemNum(),item);
        return item;
    }

    public Item findById(String id) {
        return store.get(id);
    }
    public List<Item> findByCode(String itemCode){
        List<Item> itemList = new ArrayList<>();
        for (Item item : store.values()) {
            if(item.getItemCode().contains(itemCode)){
                itemList.add(item);
            }
        }
        return itemList;
    }
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }
    public void clearStore() {
        store.clear();
    }


}
