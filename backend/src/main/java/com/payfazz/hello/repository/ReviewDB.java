package com.payfazz.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.payfazz.hello.model.ReviewModel;
import com.payfazz.hello.model.UserModel;

@Repository
public interface ReviewDB extends JpaRepository<ReviewModel, Long> {
    
    List<ReviewModel> findByTypeAndReviewer(String reviewType, UserModel user);

    List<ReviewModel> findAllByType(String reviewType);

    List<ReviewModel> findAllByReviewer(UserModel user);

    // List<ReviewModel> findAllByType(String type);

    List<ReviewModel> findAll();

    List<ReviewModel> findReviewerByReviewee(UserModel reviewee);
    
    List<ReviewModel> findRevieweeByReviewer(UserModel reviewer);

    ReviewModel findByTypeAndReviewerAndReviewee(String reviewType, UserModel reviewer, UserModel reviewee);
}
