package com.example.demo.domain.item;

import lombok.Data;

@Data
public class Item {

    private String itemNum;//상품일련번호
    private String itemCode;//상품분류
    private String itemName;//상품이름
    private String itemSalePercent;//할인률
    private String itemSale;//할인가
    private String itemPrice;//정상가
    private String saleUnit; //판매단위
    private String weight;//중량/용량
    private String imgSrc;

    public Item() {
    }

    public Item(String itemNum, String itemCode, String itemName, String itemSalePercent,
                String itemSale, String itemPrice, String saleUnit, String weight,String imgSrc) {
        this.itemNum = itemNum;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemSalePercent = itemSalePercent;
        this.itemSale = itemSale;
        this.itemPrice = itemPrice;
        this.saleUnit = saleUnit;
        this.weight = weight;
        this.imgSrc = imgSrc;
    }
}
