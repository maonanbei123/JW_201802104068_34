package com.example.demo3.bysj.service;



import com.example.demo3.bysj.dao.TeacherDao;
import com.example.demo3.bysj.domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Collection;

public final class TeacherService {
	private static TeacherDao teacherDao= TeacherDao.getInstance();
	private static TeacherService teacherService=new TeacherService();
	private TeacherService(){}
	public static TeacherService getInstance(){
		return teacherService;
	}
	public Collection<Teacher> findAll(){
		return teacherDao.getInstance().findAll();
	}
	public Teacher find(Integer id)throws SQLException{
		return teacherDao.getInstance().find(id);
	}
	public boolean update(Teacher teacher)throws SQLException,ClassNotFoundException {
		return teacherDao.getInstance().update(teacher);
	}
	public boolean add(Teacher teacher)throws SQLException,ClassNotFoundException{
		return teacherDao.getInstance().add(teacher);
	}
	public boolean delete(Integer id)throws SQLException,ClassNotFoundException{
		return teacherDao.getInstance().delete(id);
	}
}
