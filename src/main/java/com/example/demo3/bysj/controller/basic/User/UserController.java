package com.example.demo3.bysj.controller.basic.User;




import com.alibaba.fastjson.JSONObject;
import com.example.demo3.bysj.domain.User;
import com.example.demo3.bysj.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username+"this is ctl");
        JSONObject message = new JSONObject();
        try{
        User user = UserService.getInstance().login(username,password);
        if(user!=null){
            System.out.println(user.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute("currentLoginUser",user);
            message.put("message", "登录成功");
            response.getWriter().println(message);
        }else{
            message.put("message", "用户名或密码有误");
            response.getWriter().println(message);
        } }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
