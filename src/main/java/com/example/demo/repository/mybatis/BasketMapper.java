package com.example.demo.repository.mybatis;

import com.example.demo.domain.basket.BasketDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BasketMapper {

    void saveLike(@Param("id") String memberId,@Param("itemNum") String itemNum);
    List<String> findLikeAll(@Param("id") String memberId);

    void deleteLike(@Param("id") String memberId,@Param("itemNum") String itemNum);

    void saveBasket(@Param("id") String memberId,@Param("itemNum") String itemNum,@Param("quantity") int quantity);

    void updateBasket(@Param("quantity") int quantity,@Param("itemNum") String itemNum,@Param("id") String memberId);

    void deleteBasket(@Param("id") String memberId,@Param("itemNum") String itemNum);
    void order(@Param("id") String memberId);

    List<BasketDB> findBasketAll(@Param("id") String memberId);

}
