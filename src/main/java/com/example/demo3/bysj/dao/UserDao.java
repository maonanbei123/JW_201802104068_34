package com.example.demo3.bysj.dao;




import com.example.demo3.bysj.domain.Teacher;
import com.example.demo3.bysj.domain.User;
import com.example.demo3.util.JdbcHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;


public final class UserDao {
	/*private static UserDao userDao=new UserDao();
	private UserDao(){}
	public static UserDao getInstance(){
		return userDao;
	}*/
	@Autowired
	TeacherDao teacherDao;
	@Autowired
	UserDao userDao;

	public Collection<User> findAll(){
		Collection<User> users = new TreeSet<User>();
		try{
			//获得数据库连接对象
			Connection connection = JdbcHelper.getConn();
			//在该连接上创建语句盒子对象
			Statement stmt = connection.createStatement();
			//执行SQL查询语句并获得结果集对象
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM User");
			//若结果存在下一条，执行循环体
			while (resultSet.next()) {
				//根据数据库中的数据,创建User类型的对象
				User user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getDate("loginTime"),teacherDao.find(resultSet.getInt("teacher_id")));
				//添加到集合users中
				users.add(user);
			}
			connection.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return users;
	}
	
	public User find(Integer id) throws SQLException{
		//声明一个User类型的变量
		User user = null;
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		String findUserById_sql = "SELECT * FROM USER WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findUserById_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getDate("loginTime"),teacherDao.find(resultSet.getInt("teacher_id")));
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return user;
	}

	public User findByUsername(String username) throws SQLException{
		//声明一个User类型的变量
		User user = null;
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		String findByUsername_sql = "SELECT * FROM USER WHERE username=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findByUsername_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,username);
		//执行预编译语句
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getDate("loginTime"),teacherDao.find(resultSet.getInt("teacher_id")));
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return user;
	}
	public User login(String username,String password) throws SQLException{
		//声明一个User类型的变量
		User user = null;
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();

        System.out.println(username+"+++"+password);

		String findByUsername_sql = "SELECT * FROM USER WHERE username=? AND password=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(findByUsername_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,username);
		preparedStatement.setString(2, password);
		//执行预编译语句
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			user = new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getDate("loginTime"),teacherDao.find(resultSet.getInt("teacher_id")));
		}
        System.out.println(user);
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return user;
	}
	public boolean changePassword(User user) throws SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		String updatePassword_sql = " update user set password=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updatePassword_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,user.getPassword());
		preparedStatement.setInt(2,user.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("修改了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
	
}
