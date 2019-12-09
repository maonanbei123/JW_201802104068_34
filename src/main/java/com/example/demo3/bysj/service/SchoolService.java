package com.example.demo3.bysj.service;



import com.example.demo3.bysj.dao.SchoolDao;
import com.example.demo3.bysj.domain.School;

import java.sql.SQLException;
import java.util.Collection;

public final class SchoolService {
	private static SchoolDao schoolDao= SchoolDao.getInstance();
	private static SchoolService schoolService=new SchoolService();
	public static SchoolService getInstance(){
		return schoolService;
	}
	public Collection<School> findAll(){
		return schoolDao.findAll();
	}

	public School find(Integer id)throws SQLException,ClassNotFoundException{
		return schoolDao.find(id);
	}
	public boolean update(School school)throws SQLException,ClassNotFoundException{
		return schoolDao.update(school);
	}
	public boolean add(School school) throws SQLException,ClassNotFoundException {
		return schoolDao.add(school);
	}
	public boolean delete(Integer id) throws SQLException,ClassNotFoundException{
		return schoolDao.delete(id);
	}
}

