package com.payfazz.hello.service;

import com.payfazz.hello.model.DepartmentModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService {
    DepartmentModel getById(long id);
    DepartmentModel getDepartmentByName(String name);
    List<DepartmentModel> findAll();
}
