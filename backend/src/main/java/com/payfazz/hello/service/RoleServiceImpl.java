package com.payfazz.hello.service;

import com.payfazz.hello.model.RoleModel;
import com.payfazz.hello.repository.RoleDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDB roleDB;

    public List<RoleModel> findAll(){
        return roleDB.findAll();
    }

    public RoleModel findById(long id){
        return roleDB.findById(id).get();
    }
}
