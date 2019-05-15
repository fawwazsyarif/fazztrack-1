package com.payfazz.hello.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import com.payfazz.hello.model.ApprovalStatusModel;
import com.payfazz.hello.model.DepartmentModel;
import com.payfazz.hello.model.ObjectiveModel;

import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.ApprovalStatusDB;
import com.payfazz.hello.repository.DepartmentDB;
import com.payfazz.hello.repository.ObjectiveDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.DepartmentService;
import java.io.IOException;

import com.payfazz.hello.model.KeyResultModel;
import com.payfazz.hello.model.ListSelectedOKR;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.KeyResultService;
import com.payfazz.hello.service.ObjectiveService;
import com.payfazz.hello.service.UserService;

import org.hibernate.boot.archive.spi.ArchiveEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ObjectiveController {
	@Autowired
	private ObjectiveService objectiveService;

    @Autowired
    ObjectiveDB objectiveDb;

    @Autowired
    ApprovalStatusDB approvalStatusDb;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    UserService UserService;

	@Autowired
	private UserService userService;

	@Autowired
	private KeyResultService keyResultService;

	@GetMapping(value = "/objective/{id}")
	private BaseResponse<ObjectiveModel> viewObjectiveById(@PathVariable(value = "id") long id, Model model)
			throws IOException {
        BaseResponse<ObjectiveModel> response = new BaseResponse<ObjectiveModel>();
        try {
            ObjectiveModel archive = objectiveService.getObjectiveDetailById(id);
            response.setStatus(200);
            response.setMessage("Success");
            response.setResult(archive);

        } catch(Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
		
		return response;
    }
    
    // kecuali yang recommended
    @GetMapping(value= "/objective/user/{userId}")
    private BaseResponse<List<ObjectiveModel>> getMyObjective(@PathVariable(name = "userId", required= true) Long userId) {
        BaseResponse<List<ObjectiveModel>> response = new BaseResponse<List<ObjectiveModel>>();

        try {
            response.setStatus(200);
            response.setMessage("Success");
            response.setResult(objectiveService.filterAllObjectiveExcludeRecommended(userService.findUserObjective(userId)));
            // response.setResult(userService.findUserObjective(userId));
        } catch(Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // kecuali yang recommended
    @GetMapping(value = "/objective/manager/{userId}")
    private BaseResponse<List<ObjectiveModel>> getManagerObjective(@PathVariable(name="userId", required= true) Long userId) {
        BaseResponse<List<ObjectiveModel>> response = new BaseResponse<List<ObjectiveModel>>();
        List<ObjectiveModel> managerObjective = new ArrayList<>();


        try {
            for(ObjectiveModel managerObj: userService.findUserById(userId).getUserManager().getListObjective()) {
                managerObjective.add(managerObj);
            }
            response.setStatus(200);
		    response.setMessage("Success");
            response.setResult(objectiveService.filterAllObjectiveExcludeRecommended(managerObjective));
        
        } catch(Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return response;
    }

	@PostMapping(value = "/objective/add")
	public BaseResponse<ObjectiveModel> addObjective(@RequestBody ObjectiveModel objective, BindingResult bindingResult) {
        BaseResponse<ObjectiveModel> response = new BaseResponse<ObjectiveModel>();

        try{
            objectiveService.addObjective(objective);
            response.setStatus(200);
            response.setMessage("Success");
            response.setResult(objective);

        } catch (Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }

		return response;
	}

	@PostMapping(value = "/objective/delete/{id}") 
	public BaseResponse<ObjectiveModel> deleteObjective(@PathVariable Long id) {
		BaseResponse<ObjectiveModel> response = new BaseResponse<ObjectiveModel>();

        try {
            objectiveService.deleteObjective(id);
            response.setStatus(200);
            response.setMessage("Succeess");

        }catch (Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
		
		return response;
	}

	@PostMapping(value = "/objective/update/{id}")
	public BaseResponse<ObjectiveModel> updateObjective(@PathVariable long id, @RequestBody ObjectiveModel objective, BindingResult bindingResult) {
		BaseResponse<ObjectiveModel> response = new BaseResponse<ObjectiveModel>();

		ObjectiveModel obj = objectiveService.getObjectiveDetailById(id);
		obj.setDescription(objective.getDescription());
        obj.setApprovalStatus(objective.getApprovalStatus());
        obj.setAlignedObjective(objective.getAlignedObjective());

		objectiveService.editObjective(obj);

		response.setStatus(200);
		response.setMessage("Success");
		response.setResult(obj);

		return response;
    }
    
    @PostMapping(value ="/objective/update/selected")
    public BaseResponse<List<ObjectiveModel>> updateSelectedObjective(@RequestBody ListSelectedOKR listSelectedOKR, BindingResult bindingResult) {
        BaseResponse<List<ObjectiveModel>> response= new BaseResponse<List<ObjectiveModel>>();
        System.out.println(listSelectedOKR);
        for(ObjectiveModel objective: listSelectedOKR.getObjective()) {
            objectiveService.updateObjectiveStatus(objective);
        }

        response.setStatus(200);
		response.setMessage("Success");
        return response;
    }
   
    // kecuali yang recommended
    @GetMapping(value = "/get-all-objective")
    public BaseResponse<List<ObjectiveModel>> getAllObjective() {
        BaseResponse<List<ObjectiveModel>> response= new BaseResponse<List<ObjectiveModel>>();
        
        try {
            response.setResult(objectiveService.filterAllObjectiveExcludeRecommended(objectiveDb.findAll()));
        } catch (Error e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());

        }
        return response;
    }

    // kecuali yang recommended
    @GetMapping(value = "/get-all-objective/{departmentId}")
    public BaseResponse<List<ObjectiveModel>> getAllDepartmentObjective(@PathVariable(name = "departmentId", required= true) Long departmentId) {
        BaseResponse<List<ObjectiveModel>> response = new BaseResponse<List<ObjectiveModel>>();
        List<ObjectiveModel> departmentObjective = new ArrayList<>();

        try {
            List<UserModel> userDepartment = departmentService.getById(departmentId).getListUser();
            for(UserModel user : userDepartment) {
                for(ObjectiveModel obj : user.getListObjective()) {
                    departmentObjective.add(obj);
                }
            }
            response.setStatus(200);
            response.setResult(objectiveService.filterAllObjectiveExcludeRecommended(departmentObjective));
        } catch (Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return response;
    }
    
    @GetMapping(value="/get-objective-for-approval/company", produces="application/json")
    public BaseResponse<List<ObjectiveModel>> getAllObjectiveForApproval() {
        BaseResponse<List<ObjectiveModel>> response = new BaseResponse<List<ObjectiveModel>> ();
        ApprovalStatusModel adminApproval = approvalStatusDb.getOne((long) 2);
        try {
            response.setStatus(200);
		    response.setMessage("Success");
            response.setResult(adminApproval.getListObjective()); 
        } catch(Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping(value="/get-objective-for-approval/{departmentId}", produces="application/json")
    public BaseResponse<List<ObjectiveModel>> getDepartmentObjectiveForApproval(@PathVariable(name="departmentId", required= true) Long departmentId) {
        BaseResponse<List<ObjectiveModel>> response = new BaseResponse<List<ObjectiveModel>> ();
        List<ObjectiveModel> managerApproval = new ArrayList<>();

        try {
            List<UserModel> userDepartment = departmentService.getById(departmentId).getListUser();
            for(UserModel user : userDepartment) {
                for(ObjectiveModel obj : user.getListObjective()) {
                    if(obj.getApprovalStatus().getId() == 1) {
                        managerApproval.add(obj);
                    }
                }
            }
            response.setStatus(200);
            response.setResult(managerApproval);

        } catch (Error e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());

        }
        return response;
    }

    @PostMapping(value="/objective/update")
    public BaseResponse<ObjectiveModel> updateObjectiveStatus(@RequestBody ObjectiveModel objective){
        BaseResponse<ObjectiveModel> response = new BaseResponse<ObjectiveModel>();
        objectiveService.updateObjectiveStatus(objective);
        ObjectiveModel obj = objectiveService.getObjectiveDetailById(objective.getId());
		response.setResult(obj);
        return response;
    }

    @GetMapping(value="/get-recommended-okr/{managerId}", produces="application/json")
    public BaseResponse<List<ObjectiveModel>> getRecommendedOKR(@PathVariable(name="managerId", required= true) Long managerId) {
        BaseResponse<List<ObjectiveModel>> response = new BaseResponse<List<ObjectiveModel>> ();
        List<ObjectiveModel> managerOkrList = userService.findUserObjective(managerId);
        
        List<ObjectiveModel> recommendedOkrList = new ArrayList<ObjectiveModel>();
        
        for(int i = 0; i < managerOkrList.size(); i++) {
            ObjectiveModel tempOkr = managerOkrList.get(i);
            if(tempOkr.getApprovalStatus().getId() == 6) {
                recommendedOkrList.add(tempOkr);
            }
        }

        response.setResult(recommendedOkrList);

        return response;
    }

    @PostMapping(value = "/add-recommended-okr/{id}")
	public BaseResponse<ObjectiveModel> addRecommendedOKR(@PathVariable long id) {
		BaseResponse<ObjectiveModel> response = new BaseResponse<ObjectiveModel>();
        Date date = new Date();
        ApprovalStatusModel sendForApproval = approvalStatusDb.getOne((long) 1);

		ObjectiveModel obj = objectiveService.getObjectiveDetailById(id);
		obj.setApprovalStatus(sendForApproval);
		obj.setLastModifiedDate(new Timestamp(date.getTime()));

		objectiveService.addObjective(obj);

		response.setStatus(200);
		response.setMessage("Success");
		response.setResult(obj);

		return response;
	}

}
