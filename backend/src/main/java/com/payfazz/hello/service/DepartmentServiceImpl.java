package com.payfazz.hello.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.payfazz.hello.model.DepartmentModel;
import com.payfazz.hello.repository.DepartmentDB;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDB departmentDb;

    @Override
    public DepartmentModel getDepartmentByName(String name) {
        return departmentDb.findByNameIgnoreCase(name);
    }

    @Override
    public DepartmentModel getById(long id) {
        return departmentDb.findById(id).get();
    }

    @Override
    public List<DepartmentModel> findAll(){
        return departmentDb.findAll();
    }
}
