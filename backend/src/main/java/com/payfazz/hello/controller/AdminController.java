package com.payfazz.hello.controller;

import com.payfazz.hello.model.DepartmentModel;
import com.payfazz.hello.model.RoleModel;
import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.DepartmentService;
import com.payfazz.hello.service.RoleService;
import com.payfazz.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping(value="/get-roles")
    public BaseResponse<List<RoleModel>> getPhase(){
        BaseResponse<List<RoleModel>> response = new BaseResponse<>();
        List<RoleModel> roleList = roleService.findAll();

        response.setResult(roleList);
        response.setStatus(200);
        response.setMessage("success");
        return response;
    }

    @GetMapping(value="/get-manager/{id-department}")
    public BaseResponse<List<UserModel>> getManager(@PathVariable(value="id-department") long idDept ){
        BaseResponse<List<UserModel>> response = new BaseResponse<>();
        List<UserModel> mngList = userService.findManagerByDept(idDept);
        response.setResult(mngList);
        response.setStatus(200);
        response.setMessage("success");
        return response;
    }
}
