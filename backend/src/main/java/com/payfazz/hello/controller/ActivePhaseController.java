package com.payfazz.hello.controller;

import com.payfazz.hello.model.ActivePhaseModel;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.ActivePhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ActivePhaseController {
    @Autowired
    private ActivePhaseService activePhaseService;

    @GetMapping(value="/get-phases")
    public BaseResponse<List<ActivePhaseModel>> getPhase(){
        BaseResponse<List<ActivePhaseModel>> response = new BaseResponse<>();
        List<ActivePhaseModel> phaseList = new ArrayList<>();

        for (ActivePhaseModel phase : activePhaseService.findAll()){
            phaseList.add(phase);
        }

        response.setResult(phaseList);
        response.setStatus(200);
        response.setMessage("success");
        return response;
    }
    @PostMapping(value="/change-active-phase")
    public BaseResponse<ActivePhaseModel> changePhase(@RequestParam(value = "id") long id)  {

        BaseResponse<ActivePhaseModel> response = new BaseResponse<>();

        ActivePhaseModel executionPhase = activePhaseService.findActivePhaseById(1);
        ActivePhaseModel evaluationPhase = activePhaseService.findActivePhaseById(2);

        if (id==1){
            executionPhase.setStatus("active");
            evaluationPhase.setStatus("inactive");
            response.setResult(executionPhase);
        }
        else {
            executionPhase.setStatus("inactive");
            evaluationPhase.setStatus("active");
            response.setResult(evaluationPhase);
        }

        activePhaseService.addPhase(executionPhase);
        activePhaseService.addPhase(evaluationPhase);
        response.setStatus(200);
        response.setMessage("success");
        return response;
    }
    @GetMapping(value="/get-active-phase")
    public BaseResponse<ActivePhaseModel> getActivePhase() {
        BaseResponse<ActivePhaseModel> response = new BaseResponse<>();
        response.setResult(activePhaseService.findActivePhaseByStatus("active"));
        return response;

    }
}
