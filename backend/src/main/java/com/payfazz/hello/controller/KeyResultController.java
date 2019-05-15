package com.payfazz.hello.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.payfazz.hello.model.KeyResultModel;
import com.payfazz.hello.model.ObjectiveModel;
import com.payfazz.hello.repository.KeyResultDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.KeyResultService;
import com.payfazz.hello.service.ObjectiveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class KeyResultController {
    @Autowired
    private KeyResultService KeyResultService;

    @Autowired
	private ObjectiveService objectiveService;

	@Autowired
	private KeyResultDB keyResultDb;

    @PostMapping(value = "/objective/update-kr-progress/{id}")
	public BaseResponse<KeyResultModel> updateKRProgress(@PathVariable long id, @RequestBody KeyResultModel keyResult, BindingResult bindingResult) {
        BaseResponse<KeyResultModel> response = new BaseResponse<KeyResultModel>();

        Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		
		KeyResultModel kr = KeyResultService.findKeyResultById(id);

        kr.setProgress(keyResult.getProgress());
       	kr.setDocument(keyResult.getDocument());
        kr.setLastModifiedDate(currentTime);

        KeyResultService.addKR(kr);

        response.setStatus(200);
		response.setMessage("Succeess");
		response.setResult(keyResult);

		return response;

    }

    @PostMapping(value = "/objective/addKRlist/{id}")
    public BaseResponse<List<KeyResultModel>> addKRlist(@RequestBody List<KeyResultModel> listKR, @PathVariable long id) {
        BaseResponse<List<KeyResultModel>> response = new BaseResponse<List<KeyResultModel>>();
        ObjectiveModel objective = objectiveService.getObjectiveDetailById(id);
        Date date = new Date();
        List<KeyResultModel> listKRforObj = new ArrayList<KeyResultModel>();

		for(int i = 0; i < listKR.size(); i++) {
			KeyResultModel keyResult = new KeyResultModel();
			
			keyResult.setDescription(listKR.get(i).getDescription());
			keyResult.setObjectiveKR(objective);
			keyResult.setProgress(listKR.get(i).getProgress());
			keyResult.setLastModifiedDate(new Timestamp(date.getTime()));
			keyResult.setDocument(listKR.get(i).getDocument());

			KeyResultService.addKR(keyResult);
			//listKRforObj.add(keyResult);
		}

        //objective.setListKR(listKRforObj);
        
        response.setStatus(200);
		response.setMessage("Success");
		response.setResult(listKRforObj);

		return response;
    }

    @GetMapping(value = "/keyresult/{id}")
	private BaseResponse<KeyResultModel> viewKRById(@PathVariable(value = "id") long id, Model model)
			throws IOException {
		KeyResultModel archive = KeyResultService.findKeyResultById(id);

		BaseResponse<KeyResultModel> response = new BaseResponse<KeyResultModel>();
		response.setStatus(200);
		response.setMessage("Success");
		response.setResult(archive);

		return response;
	}

	@PostMapping(value = "/objective/updateKRlist/{id}")
    public BaseResponse<List<KeyResultModel>> updateKRlist(@RequestBody List<KeyResultModel> listKR, @PathVariable long id) {
        BaseResponse<List<KeyResultModel>> response = new BaseResponse<List<KeyResultModel>>();
        ObjectiveModel objective = objectiveService.getObjectiveDetailById(id);

		Date date = new Date();

		for(KeyResultModel keyResult : listKR) {

			if(keyResult.getId() == 0){
				KeyResultModel keyResultBaru = new KeyResultModel();
				
				keyResultBaru.setDescription(keyResult.getDescription());
				keyResultBaru.setObjectiveKR(objective);
				keyResultBaru.setProgress(keyResult.getProgress());
				keyResultBaru.setLastModifiedDate(new Timestamp(date.getTime()));
				keyResultBaru.setDocument(keyResult.getDocument());

				KeyResultService.addKR(keyResultBaru);
			} else{
				KeyResultModel keyResultLama = KeyResultService.findKeyResultById(keyResult.getId());

				keyResultLama.setDescription(keyResult.getDescription());
				//keyResultLama.setObjectiveKR(objective);
				keyResultLama.setProgress(keyResult.getProgress());
				keyResultLama.setLastModifiedDate(new Timestamp(date.getTime()));
				keyResultLama.setDocument(keyResult.getDocument());

				KeyResultService.addKR(keyResultLama);
			}
		}
        
        response.setStatus(200);
		response.setMessage("Success");
		response.setResult(listKR);

		return response;
	}
	
	@PostMapping(value = "/objective/deleteKR/{id}")
	public BaseResponse<List<KeyResultModel>> deleteKR(@PathVariable long id) {
		BaseResponse<List<KeyResultModel>> response = new BaseResponse<List<KeyResultModel>>();

		keyResultDb.deleteById(id);
		
		response.setStatus(200);
		response.setMessage("Success");
		return response;
	}
}
