package com.example.demo.web.item;

import com.example.demo.domain.item.Item;
import com.example.demo.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PageController {

    private final ItemRepository itemRepository;

    @GetMapping("/{itemCode}")
    public String vegetable(@PathVariable String itemCode, Model model){
        Map<String,String> changeWord = new HashMap<>();
        List<Item> items = itemRepository.findByCode(itemCode);
        Map<Integer,List<Item>>map = new HashMap<>();
        //상품출력시 3개식 출력하기 위한 과정
        makeMap(items, map);
        //itemCode 영어를 한국어로 바꾸는 과정
        changeKorean(changeWord);

        model.addAttribute("itemCode",itemCode);
        model.addAttribute("title",changeWord.get(itemCode));
        model.addAttribute("map",map);
        return "page/categories";
    }

    @GetMapping("/{itemCode}/{price}")
    public String filterPrice(@PathVariable String itemCode,@PathVariable Integer price,Model model){
        log.info("itemCode: "+itemCode+" price: "+price);
        Map<String,String> changeWord = new HashMap<>();
        List<Item> items = itemRepository.findByCode(itemCode);
        Map<Integer,List<Item>>map = new HashMap<>();

        List<Item> filters = new ArrayList<>();
        if(price == 90000 || price == 0){
            for (Item item : items) {
                int itemPrice = Integer.parseInt(item.getItemSale().replaceAll(",",""));
                if(itemPrice>=price)
                    filters.add(item);
            }
        }else{
            for (Item item : items) {
                int itemPrice = Integer.parseInt(item.getItemSale().replaceAll(",",""));
                if(itemPrice<=price)
                    filters.add(item);
            }
        }

        //상품출력시 3개식 출력하기 위한 과정
        makeMap(filters, map);
        //itemCode 영어를 한국어로 바꾸는 과정
        changeKorean(changeWord);
        checkId(price, model);

        model.addAttribute("itemCode",itemCode);
        model.addAttribute("title",changeWord.get(itemCode));
        model.addAttribute("map",map);
        return "page/categories";
    }

    private void checkId(Integer price, Model model) {
        switch(price){
            case 9999:
                model.addAttribute("checkId","price1");
                break;
            case 19999:
                model.addAttribute("checkId","price2");
                break;
            case 29999:
                model.addAttribute("checkId","price3");
                break;
            case 39999:
                model.addAttribute("checkId","price4");
                break;
            case 49999:
                model.addAttribute("checkId","price5");
                break;
            case 59999:
                model.addAttribute("checkId","price6");
                break;
            case 69999:
                model.addAttribute("checkId","price7");
                break;
            case 79999:
                model.addAttribute("checkId","price8");
                break;
            case 89999:
                model.addAttribute("checkId","price9");
                break;
            case 90000:
                model.addAttribute("checkId","price10");
                break;
            case 0:
                model.addAttribute("checkId","priceAll");
                break;
        }
    }

    private void changeKorean(Map<String, String> changeWord) {
        changeWord.put("vegetable","채소");
        changeWord.put("fruit","과일 견과 쌀");
        changeWord.put("seafood","수산 해산 건어물");
        changeWord.put("meat","정육 계란");
        changeWord.put("mainCook","국 반찬 메인요리");
        changeWord.put("salad","샐러드 간편식");
        changeWord.put("noodle","면 양념 오일");
        changeWord.put("drink","생수 음료 우유 커피");
        changeWord.put("snack","간식 과자 떡");
        changeWord.put("bakery","베이커리 치즈델리");
        changeWord.put("health","건강식품");
        changeWord.put("wine","와인 위스키");
        changeWord.put("traditional","전통주");
        changeWord.put("newGoods","신상품");
        changeWord.put("bestGoods","베스트");
        changeWord.put("saleGoods","알뜰쇼핑");
    }

    private void makeMap(List<Item> items, Map<Integer, List<Item>> map) {
        int key = 1;
        int count = 0;
        int index = 0;
        int max = 0;
        if(items.size()%3==0){
            max = items.size()/3;
        }else{
            max = items.size()/3+1;
        }
        while(key<=max){
            map.put(key,new ArrayList<>());
            while(count<3 && index<items.size()){
                map.get(key).add(items.get(index));
                index++;
                count++;
            }
            count=0;
            key++;
        }
    }
}
