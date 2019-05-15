package com.payfazz.hello.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payfazz.hello.model.DepartmentModel;
import com.payfazz.hello.model.ReviewModel;
import com.payfazz.hello.model.ReviewQuestionModel;
import com.payfazz.hello.repository.ReviewDB;
import com.payfazz.hello.repository.ReviewQuestionDB;
import com.payfazz.hello.rest.BaseResponse;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class FinalReportResultController {
	
	@Autowired
	private ReviewDB reviewDb;
	
	@Autowired
	private ReviewQuestionDB reviewQuestionDb;
	
	@GetMapping(value="/get-result/{userId}")
	public BaseResponse<List<ReviewModel>> getresult(@PathVariable(name = "userId", required= true) Long userId){
		 BaseResponse<List<ReviewModel>> response = new BaseResponse<>();
	     List<ReviewModel> reviewList = new ArrayList<>();
	     System.out.println("udah masuk");
	     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	     for (ReviewModel review : reviewDb.findAll()){
	    	 if(review.getReviewee().getId()== userId) {
	    		 System.out.println(timestamp.getYear()); 
	    		 if(timestamp.getYear() == review.getDate().getYear()) {
	    			 System.out.println("sini masuk gan");
		    		 reviewList.add(review);
	    		 }
	    		 
	        	}
	        }
	     response.setResult(reviewList);
	     response.setStatus(200);
	     response.setMessage("success");
	     return response;
	 }
	
	@GetMapping(value="/get-result/{userId}/self/{id}")
	public BaseResponse<List<ReviewQuestionModel>> getresult1(@PathVariable(name = "userId", required= true) Long userId,  @PathVariable(name = "id", required=true) Long id){
		 BaseResponse<List<ReviewQuestionModel>> response = new BaseResponse<>();
	     List<ReviewQuestionModel> reviewQuestion = new ArrayList<>();
	     System.out.println(id);
	     for (ReviewModel review : reviewDb.findAll()){
	    	 if(review.getReviewee().getId()== userId) {
	    		 if(review.getType().equalsIgnoreCase("self-review")) {
	    			 for (ReviewQuestionModel rq : reviewQuestionDb.findAll()) {
	    		    	 if(rq.getReview().getId()==review.getId() && rq.getReview().getId()==id) {
	    		    		 if(rq.getReview().getDate()==review.getDate()) {
	    		    			 reviewQuestion.add(rq);
	    		    		 } 
	    		    	 }
	    		     }
	    		 }
	        }
	     }
	     response.setResult(reviewQuestion);
	     response.setStatus(200);
	     response.setMessage("success");
	     return response;
	 }
	
	@GetMapping(value="/get-result/{userId}/manager/{id}")
	public BaseResponse<List<ReviewQuestionModel>> getresult2(@PathVariable(name = "userId", required= true) Long userId, @PathVariable(name = "id", required=true) Long id){
		BaseResponse<List<ReviewQuestionModel>> response = new BaseResponse<>();
	     List<ReviewQuestionModel> reviewQuestion = new ArrayList<>();
	     System.out.println(id);
	     for (ReviewModel review : reviewDb.findAll()){
	    	 if(review.getReviewee().getId()== userId) {
	    		 if(review.getType().equalsIgnoreCase("manager-review")) {
	    			 for (ReviewQuestionModel rq : reviewQuestionDb.findAll()) {
	    				 if(rq.getReview().getId()==review.getId() && rq.getReview().getId()==id) {
	    		    		 if(rq.getReview().getDate()==review.getDate()) {
	    		    			 reviewQuestion.add(rq);
	    		    		 } 
	    		    	 }
	    		     }
	    		 }
	        }
	     }
	     response.setResult(reviewQuestion);
	     response.setStatus(200);
	     response.setMessage("success");
	     return response;
	 }
	
	@GetMapping(value="/get-result/{userId}/peer-{id}")
	public BaseResponse<List<ReviewQuestionModel>> getresult3(@PathVariable(name = "userId", required= true) Long userId, @PathVariable(name = "id", required = true)Long id){
		BaseResponse<List<ReviewQuestionModel>> response = new BaseResponse<>();
	     List<ReviewQuestionModel> reviewQuestion = new ArrayList<>();
	     
	     for (ReviewModel review : reviewDb.findAll()){
	    	 if(review.getReviewee().getId()== userId) {
	    		 if(review.getType().equalsIgnoreCase("peer-review")) {
	    			 for (ReviewQuestionModel rq : reviewQuestionDb.findAll()) {
	    		    	 if(rq.getReview().getId()==id && review.getId()==id && rq.getReview().getDate()==review.getDate()) {
	    		    		 reviewQuestion.add(rq);
	    		    	 }
	    		     }
	    		 }
	        }
	     }
	     response.setResult(reviewQuestion);
	     response.setStatus(200);
	     response.setMessage("success");
	     return response;
	 }
	
	
//	@GetMapping(value = "/get-year")
//	public BaseResponse<List<Integer>> getYear(){
//        BaseResponse<List<Integer>> response = new BaseResponse<>();
//        List<Integer> yearList = new ArrayList<>();
//
//        for (ReviewModel review : reviewDb.findAll()) {
//			int year = (int)((((float)(review.getDate().getYear())/100f)-1)*100)+2000;
//        	if(!yearList.contains(year)) {
//        		yearList.add(year);
//        	}
//        }
//        
//        response.setResult(yearList);
//        response.setStatus(200);
//        response.setMessage("success");
//        return response;
//    }

}
