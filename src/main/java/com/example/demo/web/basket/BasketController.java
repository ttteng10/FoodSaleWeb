package com.example.demo.web.basket;

import com.example.demo.domain.basket.BasketDB;
import com.example.demo.domain.item.Item;
import com.example.demo.domain.item.ItemRepository;
import com.example.demo.domain.member.Member;
import com.example.demo.jdbc.connection.DBConnectionUtil;
import com.example.demo.repository.mybatis.BasketMapper;
import com.example.demo.session.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BasketController {

    private final ItemRepository itemRepository;
    private final BasketMapper basketMapper;
    private static Map<Item,Integer> itemCount = new HashMap<>();
    private static Set<Item> items = new HashSet<>();


    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    @GetMapping("/shopBasket/like")
    public String like(HttpServletRequest request,
                       Model model) throws SQLException {
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        items.clear();
        List<String> likeAll = basketMapper.findLikeAll(member.getId());
        for (String itemNum : likeAll) {
            Item item = itemRepository.findById(itemNum);
            items.add(item);
        }
//        String sql = "select itemNum from likeBox where memberId = ?";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,member.getId());
//            rs = pstmt.executeQuery();
//            while(rs.next()){
//                String itemNum = rs.getString("itemNum");
//                Item item = itemRepository.findById(itemNum);
//                items.add(item);
//            }
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        model.addAttribute("CountItems",items.size());
        model.addAttribute("items",items);
        return "page/likepage";
    }

    @GetMapping("/shopBasket/like/{itemNum}")
    public String chooseLike(@PathVariable String itemNum,
                             HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        basketMapper.saveLike(member.getId(),itemNum);
//        String sql = "insert into likeBox values(?,?)";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,member.getId());
//            pstmt.setString(2,itemNum);
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        return "redirect:/page/goods/"+itemNum;
    }

    @GetMapping("shopBasket/likeDelete/{itemNum}")
    public String deleteLike(@PathVariable String itemNum,
                             HttpServletRequest request,
                             Model model) throws SQLException {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Item item = itemRepository.findById(itemNum);
        items.remove(item);
        basketMapper.deleteLike(member.getId(),itemNum);
//        String sql = "delete from likeBox where memberId = ? and itemNum = ?";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,member.getId());
//            pstmt.setString(2,itemNum);
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        return "redirect:/shopBasket/like";
    }

    //장바구니 이모티콘 눌러서 들어왔을때
    @GetMapping("/shopBasket/img")
    public String basket(HttpServletRequest request,
                         Model model) throws SQLException {
        log.info("basket: "+model.getAttribute("saleChangePrice"));
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//        Basket basket = basketRepository.findById(member.getId());
//        log.info("shopBasket basket: "+basket);
        selectBasket(member);
        model.addAttribute("order","장바구니");

        return modelToShoppingBasket(model, member, itemCount);
    }



    //장바구니 담기 버튼눌렀을때
    @PostMapping("/shopBasket/btn")
    public String goBasket(@ModelAttribute Item item,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes) throws SQLException {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        redirectAttributes.addAttribute("itemNum",item.getItemNum());

        if(quantity == null) quantity=1;
        basketMapper.saveBasket(member.getId(),item.getItemNum(),quantity);
//        String sql = "insert into basket values(?,?,?)";
//
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,member.getId());
//            pstmt.setString(2,item.getItemNum());
//            pstmt.setInt(3,quantity);
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }

        return "redirect:/page/goods/{itemNum}";
    }

    @GetMapping("/shopBasket/down/{itemNum}")
    public String minusQuantity(@PathVariable String itemNum,
                                HttpServletRequest request,
                                Model model) throws SQLException {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Item findItem = null;
        for (Item item : itemCount.keySet()) {
            if(item.getItemNum().equals(itemNum))
                findItem = item;
        }
        Integer quantity = itemCount.get(findItem);
        if (quantity>1) quantity -= 1;


        basketMapper.updateBasket(quantity, findItem.getItemNum(), member.getId());
//        String sql = "update basket set quantity = ? where itemNum = ? and memberId = ?";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setInt(1,quantity);
//            pstmt.setString(2,findItem.getItemNum());
//            pstmt.setString(3,member.getId());
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        if(findItem != null){
            itemCount.put(findItem,quantity);
        }
        selectBasket(member);

        model.addAttribute("itemCount",itemCount);
        return "redirect:/shopBasket/img";
    }

    @GetMapping("/shopBasket/up/{itemNum}")
    public String plusQuantity(@PathVariable String itemNum,
                                HttpServletRequest request,
                                Model model) throws SQLException {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Item findItem = null;
        for (Item item : itemCount.keySet()) {
            if(item.getItemNum().equals(itemNum))
                findItem = item;
        }
        Integer quantity = itemCount.get(findItem)+1;

        basketMapper.updateBasket(quantity, findItem.getItemNum(), member.getId());
//        String sql = "update basket set quantity = ? where itemNum = ? and memberId = ?";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setInt(1,quantity);
//            pstmt.setString(2,findItem.getItemNum());
//            pstmt.setString(3,member.getId());
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        if(findItem != null){
            itemCount.put(findItem,quantity);
        }
        selectBasket(member);

        model.addAttribute("itemCount",itemCount);
        return "redirect:/shopBasket/img";
    }

    @PostMapping("/shopBasket/delete")
    public String deleteBasket(@ModelAttribute Item item,HttpServletRequest request,Model model) throws SQLException {

        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        log.info("delete"+item.toString());
        itemCount.remove(item);
        basketMapper.deleteBasket(member.getId(), item.getItemNum());
//        String sql = "delete from basket where memberId = ? and itemNum = ?";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,member.getId());
//            pstmt.setString(2,item.getItemNum());
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        selectBasket(member);

        return "redirect:/shopBasket/img";
    }

    @GetMapping("/shopBasket/order")
    public String order(HttpServletRequest request,Model model) throws SQLException {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        itemCount.clear();
        basketMapper.order(member.getId());
//        String sql = "delete from basket where memberId = ?";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,member.getId());
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        model.addAttribute("order","주문완료");
        return modelToShoppingBasket(model,member,itemCount);
//        return "redirect:/shopBasket/img";
    }

    private String modelToShoppingBasket(Model model, Member member, Map<Item,Integer> itemCount) {
        int totalPrice = 0;
        int finalPrice = 3000;
        String itemPrice = "0";
        Map<String,String> prices = new HashMap<>();
        if(itemCount.isEmpty()){
            model.addAttribute("buttonWord","상품을 담아주세요");
            model.addAttribute("buttonAddress","배송지 변경");
            model.addAttribute("memberAddress", member.getAddress());
            model.addAttribute("totalPrice",totalPrice);
            model.addAttribute("finalPrice",finalPrice);
            return "page/shoppingbasket";
        }
//        totalPrice = getTotalPrice(itemCount, totalPrice,itemPrice);
        for (Item item : itemCount.keySet()) {
            log.info("getTotalPrice item:"+item.getItemName()+" quantity: "+itemCount.get(item));
            Integer price = Integer.parseInt(item.getItemSale().replaceAll(",",""));
            Integer quantity = itemCount.get(item);
            Integer salePrice = price * quantity;
            itemPrice = NumberFormat.getInstance(Locale.KOREA).format(salePrice);
            totalPrice += price*quantity;
            prices.put(item.getItemNum(),itemPrice);
        }
        finalPrice += totalPrice;
        model.addAttribute("totalPrice",NumberFormat.getInstance(Locale.KOREA).format(totalPrice));
        model.addAttribute("finalPrice",NumberFormat.getInstance(Locale.KOREA).format(finalPrice));
        model.addAttribute("itemPrice",itemPrice);
        model.addAttribute("buttonWord","주문하기");
        model.addAttribute("buttonAddress","배송지 변경");
        model.addAttribute("memberAddress", member.getAddress());
        model.addAttribute("itemCount", itemCount);
        model.addAttribute("prices",prices);
        log.info("itemCount: "+ itemCount);

        return "page/shoppingbasket";
    }

    private void selectBasket(Member member) throws SQLException {
        itemCount.clear();
        List<BasketDB> basketAll = basketMapper.findBasketAll(member.getId());
        for (BasketDB basket : basketAll) {
            Item item = itemRepository.findById(basket.getItemNum());
            Integer quantity = basket.getQuantity();
            itemCount.put(item,quantity);
        }
//        String sql = "select * from basket where memberId=?";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, member.getId());
//            rs = pstmt.executeQuery();
//            while(rs.next()){
//                Item item = itemRepository.findById(rs.getString("itemNum"));
//                Integer quantity = rs.getInt("quantity");
//                itemCount.put(item,quantity);
//            }
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
