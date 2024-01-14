package com.itcast.reggle.filter;

import com.alibaba.fastjson.JSON;
import com.itcast.reggle.common.BaseContext;
import com.itcast.reggle.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "checkLoginFilter", urlPatterns = "/*")
public class CheckLogin implements Filter {
//    path matcher
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        1. get request uri
        String requestURI = request.getRequestURI();
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

//        2. check if request needed to be filtered, no then pass, if login already, also pass
        if (checkURL(urls, requestURI) || request.getSession().getAttribute("employee") != null) {
//            then pass
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }

//        mobile
        if(request.getSession().getAttribute("user") != null) {
            Long usrId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(usrId);
            filterChain.doFilter(request, response);
            return;
        }

//        3. not login yet, return login result
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * path matcher in list
     * @param requestURI
     * @return
     */
    public boolean checkURL(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) return true;
        }
        return false;
    }
}
