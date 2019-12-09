package com.example.demo3.bysj.dao;



import com.example.demo3.bysj.domain.ProfTitle;
import com.example.demo3.util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class ProfTitleDao {
	private static ProfTitleDao profTitleDao=
			new ProfTitleDao();
	private ProfTitleDao(){}
	public static ProfTitleDao getInstance(){
		return profTitleDao;
	}
	//返回结果集对象
	public Collection<ProfTitle> findAll(){
		Collection<ProfTitle> profTitles = new TreeSet<ProfTitle>();
		try{
			//获得数据库连接对象
			Connection connection = JdbcHelper.getConn();
			//在该连接上创建语句盒子对象
			Statement stmt = connection.createStatement();
			//执行SQL查询语句并获得结果集对象
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM ProfTitle");
			//若结果存在下一条，执行循环体
			while (resultSet.next()) {
				//打印结果集中记录的id字段
				System.out.print(resultSet.getInt("id"));
				System.out.print(",");
				//打印结果集中记录的no字段
				System.out.print(resultSet.getString("no"));
				System.out.print(",");
				//打印结果集中记录的description字段
				System.out.print(resultSet.getString("description"));
				System.out.print(",");
				//打印结果集中记录的remarks字段
				System.out.print(resultSet.getString("remarks"));
				//根据数据库中的数据,创建ProfTitle类型的对象
				ProfTitle profTitle = new ProfTitle(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("no"), resultSet.getString("remarks"));
				//添加到集合profTitle中
				profTitles.add(profTitle);
			}
			connection.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return profTitles;
	}
	public ProfTitle find(Integer id) throws SQLException{
		//声明一个ProfTitle类型的变量
		ProfTitle profTitle = null;
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		String deleteProfTitle_sql = "SELECT * FROM profTitle WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteProfTitle_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			profTitle = new ProfTitle(resultSet.getInt("id"),resultSet.getString("description"),resultSet.getString("no"),resultSet.getString("remarks"));
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return profTitle;
	}
	public boolean add(ProfTitle profTitle) throws SQLException,ClassNotFoundException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		String addDegree_sql = "INSERT INTO profTitle (description,no,remarks) VALUES"+" (?,?,?)";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(addDegree_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,profTitle.getDescription());
		preparedStatement.setString(2,profTitle.getNo());
		preparedStatement.setString(3,profTitle.getRemarks());
		//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
		int affectedRowNum=preparedStatement.executeUpdate();
		System.out.println("添加了"+affectedRowNum+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRowNum>0;
	}
	//delete方法，根据degree的id值，删除数据库中对应的profTitle对象
	public boolean delete(int id) throws ClassNotFoundException,SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		String deleteProfTitle_sql = "DELETE FROM profTitle WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteProfTitle_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("删除了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
	public boolean update(ProfTitle profTitle) throws ClassNotFoundException,SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		String updateProfTitle_sql = " update profTitle set description=?,no=?,remarks=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateProfTitle_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,profTitle.getDescription());
		preparedStatement.setString(2,profTitle.getNo());
		preparedStatement.setString(3,profTitle.getRemarks());
		preparedStatement.setInt(4,profTitle.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("修改了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
}