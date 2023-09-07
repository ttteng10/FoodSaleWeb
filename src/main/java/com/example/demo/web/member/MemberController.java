package com.example.demo.web.member;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberRepository;
import com.example.demo.jdbc.connection.DBConnectionUtil;
import com.example.demo.repository.mybatis.MemberMapper;
import com.example.demo.session.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    @GetMapping("/newaccount")
    public String newAccount(){
        return "page/newaccount";
    }

    @PostMapping("/newaccount")
    public String newAccountSave(@ModelAttribute Member member,Model model) throws SQLException {
        
        boolean check = false;
        if(member.getId().isEmpty()){
            model.addAttribute("messageId","아이디 입력 필수");
            check = true;
        }
        if (member.getPassword().isEmpty()) {
            model.addAttribute("messagePw","비밀번호 입력 필수");
            check = true;
        }
        if (member.getName().isEmpty()) {
            model.addAttribute("messageName","이름 입력 필수");
            check = true;
        }
        if (member.getEmail().isEmpty()) {
            model.addAttribute("messageEmail","이메일 입력 필수");
            check = true;
        }
        if (member.getAddress().isEmpty()) {
            model.addAttribute("messageAddress","주소 입력 필수");
            check = true;
        }
        if(memberMapper.findById(member.getId())!=null){
            model.addAttribute("messageId","아이디가 이미 존재합니다.");
            check=true;
        }
        if(check){
            return "page/newaccount";
        }
        memberMapper.save(member);
        memberRepository.save(member);
//        System.out.println(member);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "page/login";
    }

    @PostMapping("/login")
    public String loginCheck(@RequestParam("id") String id, @RequestParam("password") String password,
                             HttpServletRequest request,
                             Model model){
        Member member = memberRepository.findById(id);
        if(member == null || !member.getId().equals(id)){
            if(!member.getPassword().equals(password)){
                return "redirect:/login";
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,member);
        return "page/loginhome";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
        return "index";
    }

    @PostConstruct
    public void init() throws SQLException {
        memberRepository.save(new Member("aaa","aaa","kim","abc@naver.com",
                "ilsan","man","1998-10-12"));
//        String sql = "select * from member";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//            while(rs.next()){
//                Member member = new Member(
//                        rs.getString("memberId"),
//                        rs.getString("password"),
//                        rs.getString("name"),
//                        rs.getString("email"),
//                        rs.getString("address"),
//                        rs.getString("gender"),
//                        rs.getString("birth"));
//                memberRepository.save(member);
//            }
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        List<Member> members = memberMapper.findAll();
        for (Member member : members) {
            memberRepository.save(member);
        }
    }
    @PreDestroy
    public void destroy() throws SQLException {
//        String sql = "delete from member";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
        memberMapper.deleteMember();
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
    //        String sql = "insert into member values(?,?,?,?,?,?,?)";
//        try{
//            con = getConnection();
//            pstmt = con.prepareStatement(sql);
//            pstmt.setString(1,member.getId());
//            pstmt.setString(2,member.getPassword());
//            pstmt.setString(3,member.getName());
//            pstmt.setString(4,member.getEmail());
//            pstmt.setString(5,member.getAddress());
//            pstmt.setString(6,member.getGender());
//            pstmt.setString(7,member.getBirth());
//            pstmt.executeUpdate();
//        }catch (SQLException e){
//            throw e;
//        } finally{
//            close(con,pstmt,rs);
//        }
}
