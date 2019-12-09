package com.example.demo3.bysj.controller.basic.degree;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo3.bysj.domain.Degree;
import com.example.demo3.bysj.service.DegreeService;
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
public class DegreeController extends HttpServlet {

    @RequestMapping(value = "/degree.ctl",method = RequestMethod.POST)
    protected void doPost(@RequestBody String degreeReq,
             HttpServletResponse response)
            throws ServletException, IOException {
        //将JSON字串解析为Degree对象
        Degree degreeToAdd = JSON.parseObject(degreeReq, Degree.class);
        System.out.println(degreeToAdd);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //增加加Degree对象
            DegreeService.getInstance().add(degreeToAdd);
            //加入数据信息
            message.put("message", "增加成功");
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


    @RequestMapping(value = "/degree.ctl",method = RequestMethod.DELETE)
    protected void doDelete(
            @RequestParam(value = "id") String id,
            HttpServletResponse response) throws ServletException, IOException {
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        int id_Int = Integer.parseInt(id);
        try{
            //到数据库表中删除对应的学院
            DegreeService.getInstance().delete(id_Int);
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

    @RequestMapping(value = "/degree.ctl",method = RequestMethod.PUT)
    public void doPut(
            @RequestBody String degreeToUpdate,
            HttpServletResponse response) throws IOException{
        Degree degreeUpDate = JSON.parseObject(degreeToUpdate, Degree.class);
        JSONObject message = new JSONObject();
        try{
            //修改Degree对象
            DegreeService.getInstance().update(degreeUpDate);
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

    @RequestMapping(value = "/degree.ctl",method = RequestMethod.GET)
    public void doGet(@RequestParam(value = "id",required = false)String id,HttpServletResponse response) throws IOException{
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try{
            //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
            if(id == null){
                responseDegrees(response);
            }else{
                int id_Int = Integer.parseInt(id);
                responseDegree(id_Int, response);
            }}catch (Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }

    //响应一个学位对象
    private void responseDegree(int id, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            //根据id查找学院
            Degree degree = DegreeService.getInstance().find(id);
        String degree_json = JSON.toJSONString(degree);
        //响应message到前端
        response.getWriter().println(degree_json);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //响应所有学位对象
    private void responseDegrees(HttpServletResponse response)
            throws ServletException, IOException {
        //获得所有学院
        Collection<Degree> degrees = DegreeService.getInstance().findAll();
        String degrees_json = JSON.toJSONString(degrees);
        //响应到前端
        response.getWriter().println(degrees_json);
    }
}
