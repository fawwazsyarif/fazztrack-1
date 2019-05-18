package com.payfazz.hello.controller;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import com.payfazz.hello.model.ReviewModel;
import com.payfazz.hello.model.ReviewQuestionModel;
import java.util.Optional;

import com.payfazz.hello.repository.ReviewDB;
import com.payfazz.hello.rest.BaseResponse;
import com.payfazz.hello.service.ReviewQuestionService;
import com.payfazz.hello.service.ReviewService;
import com.payfazz.hello.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

    @PostMapping(value = "/review/add")
	public BaseResponse<ReviewModel> addSelfReview(@RequestBody ReviewModel review, BindingResult bindingResult) {
		BaseResponse<ReviewModel> response = new BaseResponse<ReviewModel>();
		Optional<ReviewModel> temp = reviewService.getReviewById(review.getId());
		ReviewModel reviewExist = null;

		try {
			if(temp.isPresent()){
				reviewExist = temp.get();
				reviewExist.setListReviewQuestion(review.getListReviewQuestion());
				reviewExist.setStatus("completed");
				review = reviewExist;
			}
			reviewService.addReview(review);
			response.setResult(reviewService.getReviewModel(review.getId()));
		} catch(Error e) {
			response.setStatus(404);
            response.setMessage(e.getMessage());
		}
		return response;
	}
	
	@GetMapping(value= "/review/get/{reviewType}/{userId}")
	public BaseResponse<List<ReviewModel>> getSelfReview(@PathVariable(name="reviewType", required= true) String reviewType, @PathVariable(name = "userId", required= true) Long userId) {
		BaseResponse<List<ReviewModel>> response = new BaseResponse<List<ReviewModel>>();
		System.out.println(reviewType);
		response.setResult(reviewService.getReviewByTypeAndReviewer(reviewType, userId));
		return response;
	}
	
	@Autowired
    ReviewDB reviewDb;

    @Autowired
    UserService userService;

    @PostMapping(value = "/peer-review/add-reviewee")
	public BaseResponse<ReviewModel> addRevieweeAndReviewer(@RequestBody ReviewModel review, BindingResult bindingResult) {
		BaseResponse<ReviewModel> response = new BaseResponse<ReviewModel>();
		try{
			reviewService.addRevieweeAndReviewer(review);

			response.setStatus(200);
			response.setMessage("Success");
			response.setResult(review);
		} catch(Error e){
			response.setStatus(404);
            response.setMessage(e.getMessage());
		}
		return response;
    }
    
    //buat nge-get 1 review
    @GetMapping(value= "/review/get/{reviewType}/{reviewerId}/{revieweeId}")
    private BaseResponse<ReviewModel> getOneReview(@PathVariable(name = "reviewerId", required= true) Long reviewerId, @PathVariable(name = "revieweeId", required= true) Long revieweeId, @PathVariable(name = "reviewType", required= true) String reviewType) {
        BaseResponse<ReviewModel> response = new BaseResponse<ReviewModel>();
        
        response.setStatus(200);
		response.setMessage("Success");
        response.setResult(reviewDb.findByTypeAndReviewerAndReviewee(reviewType, userService.findUserById(reviewerId), userService.findUserById(revieweeId)));
        return response;
	}
	
	@PostMapping(value = "/peer-review/delete/{idReview}")
	public BaseResponse<ReviewModel> deletePeerReview(@PathVariable(name = "idReview", required= true) Long idReview) {
		BaseResponse<ReviewModel> response = new BaseResponse<ReviewModel>();
		ReviewModel review = reviewService.getReviewModel(idReview);
		try{
			reviewService.deleteReview(review);
			response.setStatus(200);
			response.setMessage("Success");
			response.setResult(review);		
		} catch(Error e){
			response.setStatus(404);
            response.setMessage(e.getMessage());
		}		
		return response;
    }

}