package com.payfazz.hello.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payfazz.hello.model.CompMonthSumModel;
import com.payfazz.hello.model.DeptMonthHistoryModel;
import com.payfazz.hello.model.KeyResultModel;
import com.payfazz.hello.model.ObjectiveModel;
import com.payfazz.hello.model.RoleModel;
import com.payfazz.hello.repository.CompMonthSumDB;
import com.payfazz.hello.repository.KeyResultDB;
import com.payfazz.hello.repository.ObjectiveDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.CompMonthSumService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CompanyController {

	@Autowired
	private CompMonthSumService compMonthSumService;
	
	@Autowired
	private CompMonthSumDB compMonthSumDb; 
	
	@Autowired
	private ObjectiveDB objectiveDb;
	
	@Autowired
	private KeyResultDB keyResultDb;
	
	@GetMapping(value = "/sumComp")
    public BaseResponse<List<CompMonthSumModel>> getAllSum() {
        BaseResponse<List<CompMonthSumModel>> response= new BaseResponse<List<CompMonthSumModel>>();

        if(compMonthSumDb.findAll().isEmpty()) {
            response.setStatus(500);
            response.setMessage("not found");
        }
        else {
            response.setStatus(200);
            response.setMessage("Success");
            response.setResult(compMonthSumDb.findAll());
        }
       
        return response;
    }
	
	@PostMapping(value="/submitSumComp")
	public BaseResponse<CompMonthSumModel> submitMonthHistory(){
		BaseResponse<CompMonthSumModel> response = new BaseResponse<CompMonthSumModel>();
		List<ObjectiveModel> getObjective = objectiveDb.findAll();
		
		for(ObjectiveModel obj : getObjective) {
			CompMonthSumModel submitObjComp = new CompMonthSumModel();
			System.out.println(obj.getDescription());
			String roleName = "";
			for(RoleModel role : obj.getUserObjective().getRoles()) {
				roleName=role.getName();
			}
			if(roleName.equalsIgnoreCase("manager")) {
				System.out.println("masuk manager");
				if(obj.getApprovalStatus().getId()==3) {
					System.out.println("masuk approved");
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					System.out.println(timestamp);
					
					int calculateProgress = 0;
					int amountKR = 0;
					for(KeyResultModel kr : keyResultDb.findAll()) {
						if(kr.getObjectiveKR().getId()==obj.getId()) {
							System.out.println(kr.getDescription());
							calculateProgress =+  kr.getProgress();
							amountKR++;
							System.out.println(calculateProgress);
						}
					}
					submitObjComp.setDate(timestamp);
					submitObjComp.setAvgProgress(calculateProgress/amountKR);
					submitObjComp.setCompObjective(obj);
					compMonthSumService.addCompSummary(submitObjComp);
				}
				
			}
		}
		
		response.setMessage("Success");
		response.setStatus(200);
		return response;
		
	}
}
