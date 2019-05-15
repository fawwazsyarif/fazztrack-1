package com.payfazz.hello.controller;

import java.util.Date;
import java.sql.Timestamp;

import com.payfazz.hello.model.ReviewModel;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReviewQuestionController {
	// @Autowired
	// private ReviewService reviewService;

    @PostMapping(value = "/review/answered-question/add")
	public BaseResponse<ReviewModel> addAnsweredQuestion(@RequestBody ReviewModel selfReview, BindingResult bindingResult) {
		BaseResponse<ReviewModel> response = new BaseResponse<ReviewModel>();
		
		return response;
	}
}