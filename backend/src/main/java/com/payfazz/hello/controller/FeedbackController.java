package com.payfazz.hello.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.payfazz.hello.model.FeedbackModel;
import com.payfazz.hello.repository.FeedbackDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.FeedbackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FeedbackController{
    @Autowired
    public FeedbackDB feedbackDb;

    @Autowired
    public FeedbackService feedbackService;

    @PostMapping(value="/feedback/add")
    public BaseResponse<FeedbackModel> addFeedback(@RequestBody FeedbackModel feedback, BindingResult bindingResult){
        BaseResponse<FeedbackModel> response = new BaseResponse<FeedbackModel>();
        Date date = new Date();

        feedback.setId(feedback.getId());
        feedback.setUserObjective(feedback.getUserObjective());
        feedback.setDate(new Timestamp(date.getTime()));
        feedback.setDescription(feedback.getDescription());

        feedbackService.addFeedback(feedback);
        response.setStatus(200);
        response.setMessage("Success");
        response.setResult(feedback);

        return response;
    }

    @GetMapping(value="/feedback")
    public BaseResponse<List<FeedbackModel>> getAllFeedback(){
        BaseResponse<List<FeedbackModel>> response = new BaseResponse<>();

        List<FeedbackModel> listFeedback = feedbackService.getAllFeedback();
        if (listFeedback.size() < 1){
            response.setStatus(500);
            response.setMessage("No user found");
        }
        else {
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(listFeedback);
        }
        return response;
    }
}