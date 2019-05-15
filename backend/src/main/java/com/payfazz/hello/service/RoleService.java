package com.payfazz.hello.service;

import com.payfazz.hello.model.RoleModel;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public interface RoleService {
    List<RoleModel> findAll();
    RoleModel findById(long id);
}
