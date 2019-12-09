package com.example.demo3.bysj.controller.basic.department;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.bysj.domain.Department;
import com.example.demo3.bysj.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@RestController
public class DepartmentController extends HttpServlet {
    @RequestMapping(value = "/department.ctl",method = RequestMethod.POST)
    protected void doPost(@RequestBody String departmentReq,
                          HttpServletResponse response)
            throws ServletException, IOException {
        Department departmentToAdd = JSON.parseObject(departmentReq, Department.class);
        System.out.println(departmentToAdd);
        JSONObject message = new JSONObject();
        try {
            DepartmentService.getInstance().add(departmentToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        response.getWriter().println(message);
    }


    @RequestMapping(value = "/department.ctl",method = RequestMethod.DELETE)
    protected void doDelete(
            @RequestParam(value = "id") String id,
            HttpServletResponse response) throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        int id_Int = Integer.parseInt(id);
        try{
            DepartmentService.getInstance().delete(id_Int);
            //加入数据信息
            message.put("message", "删除成功");
        }catch (SQLException e){
            //加入数据信息
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch (Exception e){
            //加入数据信息
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @RequestMapping(value = "/department.ctl",method = RequestMethod.PUT)
    public void doPut(
            @RequestBody String departmentToUpdate,
            HttpServletResponse response) throws IOException{
        Department departmentUpDate = JSON.parseObject(departmentToUpdate, Department.class);
        JSONObject message = new JSONObject();
        try{
            DepartmentService.getInstance().update(departmentUpDate);
            message.put("message", "更新成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        response.getWriter().println(message);
    }

    @RequestMapping(value = "/department.ctl",method = RequestMethod.GET)
    public void doGet(@RequestParam(value = "id",required = false)String id,HttpServletResponse response) throws IOException{
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try{
            if(id == null){
                responseDepartments(response);
            }else{
                int id_Int = Integer.parseInt(id);
                responseDepartment(id_Int, response);
            }}catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }

    private void responseDepartment(int id, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            //根据id查找学院
            Department department = DepartmentService.getInstance().find(id);
            String department_json = JSON.toJSONString(department);
            //响应message到前端
            response.getWriter().println(department_json);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //响应所有学位对象
    private void responseDepartments(HttpServletResponse response)
            throws ServletException, IOException {
        //获得所有学院
        Collection<Department> departments = DepartmentService.getInstance().findAll();
        String departments_json = JSON.toJSONString(departments);
        //响应到前端
        response.getWriter().println(departments_json);
    }
}
