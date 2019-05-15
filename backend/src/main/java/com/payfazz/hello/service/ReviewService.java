package com.payfazz.hello.service;

import java.util.List;
import java.util.Optional;

import com.payfazz.hello.model.ReviewModel;
import com.payfazz.hello.model.UserModel;

import org.springframework.stereotype.Service;

public interface ReviewService {
    public void addReview(ReviewModel review);
    public ReviewModel getReviewModel(long id);
    public List<ReviewModel> getReviewByTypeAndReviewer(String reviewType, Long userId);
    void deleteReview(ReviewModel review);
    void updateReview(ReviewModel review);
    List<ReviewModel> getPeerReviewer(UserModel reviewee);
    List<ReviewModel> getPeerReviewee(UserModel reviewer);
    Optional<ReviewModel> getReviewById(long id);
    void addRevieweeAndReviewer(ReviewModel review);
}
