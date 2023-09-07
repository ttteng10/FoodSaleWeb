package com.example.demo.session.web.filter;

import com.example.demo.session.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    //장바구니랑 좋아요 못들어가게하기
    private static final String[] blacklist = {"/like","/shopBasket/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
//        log.info(requestURI);

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            if (isLoginCheckPath(requestURI)){
                log.info("isLoginCheckPath");
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("session null");
                    httpResponse.sendRedirect("/login");
                    return;
                }
            }
            chain.doFilter(request,response);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(blacklist, requestURI);
    }
}
