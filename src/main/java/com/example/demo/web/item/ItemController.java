package com.example.demo.web.item;

import com.example.demo.domain.item.Item;
import com.example.demo.domain.item.ItemRepository;
import com.example.demo.jdbc.connection.DBConnectionUtil;
import com.example.demo.repository.mybatis.ItemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @GetMapping("/page/goods/{itemNum}")
    public String findGoods(@PathVariable String itemNum, Model model){
        Item findItem = itemRepository.findById(itemNum);
        model.addAttribute("item",findItem);
        log.info("goods");
        return "page/goods";
    }

    @PostConstruct
    public void init() throws SQLException {
        String sql = "select * from item";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Item> items = itemMapper.findAll();
        for (Item item : items) {
            itemRepository.save(item);
        }
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//
//            while(rs.next()){
//                Item item = new Item();
//                item.setItemNum(rs.getString("ItemNum"));
//                item.setItemCode(rs.getString("ItemCode"));
//                item.setItemName(rs.getString("ItemName"));
//                item.setItemSalePercent(rs.getString("ItemSalePercent"));
//                item.setItemSale(rs.getString("ItemSale"));
//                item.setItemPrice(rs.getString("ItemPrice"));
//                item.setSaleUnit(rs.getString("SaleUnit"));
//                item.setWeight(rs.getString("weight"));
//                item.setImgSrc(rs.getString("imgSrc"));
//                itemRepository.save(item);
//            }
//
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            } }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        } }
    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
