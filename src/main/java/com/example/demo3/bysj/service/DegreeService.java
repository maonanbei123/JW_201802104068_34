package com.example.demo3.bysj.service;

import com.example.demo3.bysj.dao.DegreeDao;
import com.example.demo3.bysj.domain.Degree;

import java.sql.SQLException;
import java.util.Collection;
public final class DegreeService {
    private static DegreeDao degreeDao
            = DegreeDao.getInstance();
    private static DegreeService degreeService
            =new DegreeService();
    private DegreeService(){}

    public static DegreeService getInstance(){
        return degreeService;
    }

    public Collection<Degree> findAll(){
        return degreeDao.findAll();
    }

    public Degree find(Integer id)throws SQLException{
        return degreeDao.find(id);
    }
    public boolean update(Degree degree)throws SQLException,ClassNotFoundException{
        return degreeDao.update(degree);
    }
    public boolean add(Degree degree) throws SQLException,ClassNotFoundException {
        return degreeDao.add(degree);
    }
    public boolean delete(Integer id) throws SQLException,ClassNotFoundException{
        return degreeDao.delete(id);
    }
}

