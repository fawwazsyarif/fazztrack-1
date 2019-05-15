package com.payfazz.hello.controller;

import com.payfazz.hello.model.DepartmentModel;
import com.payfazz.hello.model.ReviewModel;
import com.payfazz.hello.model.RoleModel;
import com.payfazz.hello.service.DepartmentService;
import com.payfazz.hello.service.ReviewService;
import com.payfazz.hello.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.UserDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.UserService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserDB userDb;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/user/add")
    public BaseResponse<UserModel> addUser(@RequestBody UserModel user, BindingResult bindingResult) {
        BaseResponse<UserModel> response = new BaseResponse<UserModel>();
        Date date = new Date();

        user.setId(user.getId());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setUserDepartment(user.getUserDepartment());
        user.setIsActive(user.getIsActive());
        user.setUserManager(user.getUserManager());
        user.setName(user.getName());
        user.setNik(user.getNik());
        user.setRoles(user.getRoles());
        // user.setCreatedDate(new Timestamp(date.getTime()));
        // user.setUpdatedDate(new Timestamp(date.getTime()));
        userService.addUser(user);
        response.setStatus(200);
        response.setMessage("Success");
        response.setResult(user);
        return response;
    }

    @GetMapping(value = "/user")
    public BaseResponse<List<UserModel>> getAllUser() {
        List<UserModel> listUserResponse = new ArrayList<>();
        BaseResponse<List<UserModel>> response = new BaseResponse<>();

        List<UserModel> listUser = userService.findAll();
        if (listUser.size() < 1) {
            response.setStatus(500);
            response.setMessage("No user found");
        } else {
            for (UserModel user : listUser) {
                listUserResponse.add(user);
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(listUserResponse);
            }
        }
        return response;
    }

    @GetMapping(value = "/user/{id}")
    public BaseResponse<UserModel> getUser(@PathVariable(value = "id", required = true) long id) {
        BaseResponse<UserModel> response = new BaseResponse<>();

        UserModel user = userService.findUserById(id);

        if (user == null) {
            response.setStatus(500);
            response.setMessage("User not found!");
        } else {
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(user);
        }
        return response;
    }

    @PostMapping(value = "/user/{id}/update")
    public BaseResponse<UserModel> updateUser(@PathVariable("id") long id, @RequestBody UserModel user) {
        BaseResponse<UserModel> response = new BaseResponse<>();

        UserModel userTarget = userService.findUserById(id);

        if (user == null) {
            response.setStatus(500);
            response.setMessage("User not found!");
        } else {
            userTarget.setUsername(user.getUsername());
            userTarget.setPassword(user.getPassword());
            userTarget.setUserDepartment(user.getUserDepartment());
            userTarget.setUserManager(user.getUserManager());
            userTarget.setName(user.getName());
            userTarget.setNik(user.getNik());
            userTarget.setRoles(user.getRoles());
            // user.setCreatedDate(new Timestamp(date.getTime()));
            // user.setUpdatedDate(new Timestamp(date.getTime()));
            userService.addUser(userTarget);
            response.setStatus(200);
            response.setMessage("Success");
            response.setResult(userTarget);
            return response;
        }
        return response;
    }

    @GetMapping(value = "/user/{id}/change-status")
    public BaseResponse<UserModel> changeStatusUser(@PathVariable(value = "id", required = true) long id) {
        BaseResponse<UserModel> response = new BaseResponse<>();

        UserModel user = userService.findUserById(id);

        if (user == null) {
            response.setStatus(500);
            response.setMessage("User not found!");
        } else {
            /** userService.deleteUser(user); **/
            if (user.getIsActive() == 1) {
                user.setIsActive(0);
            } else if (user.getIsActive() == 0) {
                user.setIsActive(1);
            }
            userService.addUser(user);
            response.setResult(user);
            response.setStatus(200);
            response.setMessage("success");
        }
        return response;
    }

    @GetMapping(value = "/user/{username}/employees")
    public BaseResponse<List<UserModel>> getUserEmployee(
            @PathVariable(value = "username", required = true) String username) {
        BaseResponse<List<UserModel>> response = new BaseResponse<>();

        UserModel user = userService.findUserByUsername(username);

        if (user == null) {
            response.setStatus(500);
            response.setMessage("User not found!");
        } else {
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(user.getListManager());
        }
        return response;
    }

    @CrossOrigin
    @GetMapping(value = "/user/{username}/employees/{idEmployee}/exclude-peer")
    public BaseResponse<List<UserModel>> getUserEmployeeExcludeAssignedPeer(
            @PathVariable(value = "username", required = true) String username,
            @PathVariable(value = "idEmployee", required = true) long idEmployee) {
        BaseResponse<List<UserModel>> response = new BaseResponse<>();

        UserModel user = userService.findUserByUsername(username);

        if (user==null){
            response.setStatus(500);
            response.setMessage("User not found!");
        }
        else {
            List<UserModel> listEmployees = user.getListManager();
            List<ReviewModel> listRevieweeReview = reviewService.getPeerReviewee(userService.findUserById(idEmployee));
            
            for(ReviewModel review : listRevieweeReview){
                if(review.getType().equals("peer-review") && listEmployees.contains(review.getReviewee())){
                    listEmployees.remove(review.getReviewee());
                }
            }

            UserModel employee = userService.findUserById(idEmployee);
            listEmployees.remove(employee);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(listEmployees);
        }
        return response;
    }

}

