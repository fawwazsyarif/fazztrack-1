package com.payfazz.hello.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payfazz.hello.model.CompMonthSumModel;
import com.payfazz.hello.model.DepartmentModel;
import com.payfazz.hello.model.DeptMonthHistoryModel;
import com.payfazz.hello.model.KeyResultModel;
import com.payfazz.hello.model.ObjectiveModel;
import com.payfazz.hello.model.RoleModel;
import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.DepartmentDB;
import com.payfazz.hello.repository.DeptMonthHistoryDB;
import com.payfazz.hello.repository.KeyResultDB;
import com.payfazz.hello.repository.ObjectiveDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.CompMonthSumService;
import com.payfazz.hello.service.DepartmentService;
import com.payfazz.hello.service.DeptMonthHistoryService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class DepartmentController {
	
	@Autowired
	private DeptMonthHistoryService deptMonthHistoryService;
	
	@Autowired
	private DeptMonthHistoryDB deptMonthHistoryDb ;

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ObjectiveDB objectiveDb;
	
	@Autowired
	private KeyResultDB keyResultDb;
	
	
//	@GetMapping(value="/sumDept")
//	public BaseResponse<List<DeptMonthHistoryModel>> sumDept (){
//		BaseResponse <List<DeptMonthHistoryModel>> response = new BaseResponse<List<DeptMonthHistoryModel>>();
//		List<DeptMonthHistoryModel> listDeptHistory = deptMonthHistoryDb.findAll();		
//		response.setStatus(200);
//		response.setMessage("Success");
//		response.setResult(listDeptHistory);
//		return response;
//		
//	}
	
	@GetMapping(value = "/sumDept")
    public BaseResponse<List<DeptMonthHistoryModel>> getAllHistory() {
        BaseResponse<List<DeptMonthHistoryModel>> response= new BaseResponse<List<DeptMonthHistoryModel>>();

        if(deptMonthHistoryDb.findAll().isEmpty()) {
            response.setStatus(500);
            response.setMessage("not found");
        }
        else {
            response.setStatus(200);
            response.setResult(deptMonthHistoryDb.findAll());
        }
       
        return response;
    }
	
//	@PostMapping(value = "/sumDept")
//	public BaseResponse<DeptMonthHistoryModel> deptHistory (@RequestBody DeptMonthHistoryModel deptHistory){
//		BaseResponse<DeptMonthHistoryModel> response = new BaseResponse<DeptMonthHistoryModel>();
//		
//		deptHistory.setDate(deptHistory.getDate());
//		deptHistory.setDepartment(deptHistory.getDepartment());
//		deptHistory.setObjective(deptHistory.getObjective());
//		deptHistory.setProgress(deptHistory.getProgress());
//		deptMonthHistoryService.addDeptHistory(deptHistory);
//		response.setMessage("Success");
//		response.setStatus(200);
//		response.setResult(deptHistory);
//		return response;
//		
//	}
	@PostMapping(value="/submitSumDept")
	public BaseResponse<DeptMonthHistoryModel> submitDeptHistory(){
		BaseResponse<DeptMonthHistoryModel> response = new BaseResponse<DeptMonthHistoryModel>();
		List<ObjectiveModel> getObjective = objectiveDb.findAll();
//		List<DeptMonthHistoryModel> getHistory = deptMonthHistoryDb.findAll();
		
		for(ObjectiveModel obj : getObjective) {
			DeptMonthHistoryModel submitObjDept = new DeptMonthHistoryModel();
			System.out.println(obj.getDescription());
			String roleName = "";
			for(RoleModel role : obj.getUserObjective().getRoles()) {
				roleName=role.getName();
			}
			if(roleName.equalsIgnoreCase("employee")) {
				System.out.println("masuk employee");
				if(obj.getApprovalStatus().getId()==3) {
					System.out.println("masuk approved");
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					int calculateProgress = 0;
					int amountKR = 0;
					for(KeyResultModel kr : keyResultDb.findAll()) {
						System.out.println(kr.getDescription());
						if(kr.getObjectiveKR().getId()==obj.getId()) {
							System.out.println(kr.getDescription());
							calculateProgress =+  kr.getProgress();
							amountKR++;
							System.out.println("masuk key result sini");
						}
					}
					System.out.println(calculateProgress/amountKR);
					submitObjDept.setDate(timestamp);
					submitObjDept.setProgress(calculateProgress/amountKR);
					submitObjDept.setHistoryDepartment(obj.getUserObjective().getUserDepartment());
					submitObjDept.setObjective(obj);
					deptMonthHistoryService.addDeptHistory(submitObjDept);
				}
			}
		}
		response.setMessage("Success");
		response.setStatus(200);
		return response;
		
	}
	
	@GetMapping(value="/get-departments")
    public BaseResponse<List<DepartmentModel>> getDepartment(){
        BaseResponse<List<DepartmentModel>> response = new BaseResponse<>();
        List<DepartmentModel> departmentList = departmentService.findAll();

        response.setResult(departmentList);
        response.setStatus(200);
        response.setMessage("success");
        return response;
    }
}
