package com.gpt.Filter;

import com.alibaba.fastjson2.JSON;
import com.gpt.Common.BaseContextCommon;
import com.gpt.Common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-04  17:06
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */


// 检查用户是否完成登录操作
@Slf4j
@WebFilter(filterName = "loginCheckFilter")
public class LoginCheckFilter implements Filter {
    // 路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 1、获取本次请求的url
        String requestURI = request.getRequestURI();
        log.info("拦截的请求:{}", requestURI);

        String[] urls=new String[]{
                "/employee/login",
                "/employee/layout",
                "/backend/**",
                "/common/**",
                "/front/**",
                "/swagger-ui.html",
                "/user/code",
                "/user/sendMsg",
                "/user/login"
        };

        // 2、判断本次是否需要处理
        boolean check = check(urls, requestURI);

        // 3、如果不需要处理，则直接放行
        if (check) {
            log.info("放行:{}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 4-1、判断登陆状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employee")!=null){
            log.info("已登录,id为{}",request.getSession().getAttribute("employee"));

            Long empID = (Long) request.getSession().getAttribute("employee");
            BaseContextCommon.setCurrentUserId(empID);

            long id = Thread.currentThread().getId();
            log.info("线程id为{}:",id);

            filterChain.doFilter(request, response);
            return;
        }

        // 4-2、判断移动端登陆状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user")!=null){
            log.info("已登录,id为{}",request.getSession().getAttribute("user"));

            Long userID = (Long) request.getSession().getAttribute("user");
            BaseContextCommon.setCurrentUserId(userID);

            filterChain.doFilter(request, response);
            return;
        }

        // 5、如果未登录则返回未登录结果
        // 通过输出流向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("收到拦截请求：{}",request.getRequestURI());
    }

    // 路径匹配，检查当前路径是否需要放行
    public  boolean check(String[] urls,String requestURI){
        for (String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
