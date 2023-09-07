package com.example.demo.repository.mybatis;

import com.example.demo.domain.item.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    List<Item> findAll();
}
