package com.example.demo3.bysj.controller.security;



import com.alibaba.fastjson.JSONObject;
import com.example.demo3.bysj.domain.User;
import com.example.demo3.bysj.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.LogManager;
import java.util.logging.Logger;


@WebServlet("/login")
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try{
            User loggedUser = UserService.getInstance().login(username, password);
            if(loggedUser != null){
                message.put("message", "登录成功");
                HttpSession session = request.getSession();
                //10秒钟没有操作，则使session失效
                session.setMaxInactiveInterval(10);
                session.setAttribute("currentUser",loggedUser);
                response.getWriter().println(message);
                //此处应重定向到索引页（菜单页）
                return;
            }else {
                message.put("message", "用户名或密码错误");
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
