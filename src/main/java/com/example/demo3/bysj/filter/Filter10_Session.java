package com.example.demo3.bysj.filter;



import com.alibaba.fastjson.JSONObject;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//@Order
//@WebFilter(filterName = "Filter10_Session",urlPatterns = {"/*"})
public class Filter10_Session implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 10 - Session begins");
        resp.setContentType("text/html;charset=UTF-8");
        HttpServletRequest request = (HttpServletRequest)req;
        String path= request.getRequestURI();
        if (path.contains("/login") || path.contains("/mySchool") || path.contains("/GetSession") || path.contains("/ShowCookies")){
            System.out.println("不对该页面进行验证");
        }else {
            //创建JSON对象message，以便往前端响应信息
            JSONObject message = new JSONObject();
            //访问权限验证
            HttpSession session = request.getSession(false);
            if (session==null || session.getAttribute("currentUser")==null){
                message.put("message", "请登录或重新登录");
                //响应message到前端
                resp.getWriter().println(message);
                return;
            }
            System.out.println("验证通过");
        }
        chain.doFilter(req, resp);
        System.out.println("Filter 10 - Session ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
