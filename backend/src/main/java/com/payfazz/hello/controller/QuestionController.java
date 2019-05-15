package com.payfazz.hello.controller;

import java.util.List;

import com.payfazz.hello.model.QuestionModel;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class QuestionController {
    @Autowired
	private QuestionService questionService;
   
    @GetMapping(value= "/question/all")
    private BaseResponse<List<QuestionModel>> getAllObjective() {
        BaseResponse<List<QuestionModel>> response = new BaseResponse<List<QuestionModel>> ();
        response.setStatus(200);
        response.setResult(questionService.getAllQuestion());
        return response;
    }


}