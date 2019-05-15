package com.payfazz.hello.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.payfazz.hello.model.ReviewModel;
import com.payfazz.hello.model.ReviewQuestionModel;
import com.payfazz.hello.model.UserModel;
import com.payfazz.hello.repository.ReviewDB;
import com.payfazz.hello.repository.ReviewQuestionDB;
import com.payfazz.hello.repository.UserDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
  @Autowired
  private ReviewDB reviewDb;

  @Autowired
  private ReviewQuestionDB reviewQuestionDb;

  @Autowired
  private UserDB userDb;

  @Override
  public void addReview(ReviewModel review) {
    Date date = new Date();
    review.setDate(new Timestamp(date.getTime()));
    int avg = 0;
    List<ReviewQuestionModel> backupReviewQuestion = review.getListReviewQuestion();
    review.setListReviewQuestion(null);
    review.setReviewee(userDb.getOne(review.getReviewee().getId()));
    review.setReviewer(userDb.getOne(review.getReviewer().getId()));

    for (ReviewQuestionModel reviewQuestion : backupReviewQuestion) {
      reviewQuestion.setReview(review);
    }

    review.setListReviewQuestion(backupReviewQuestion);

    for (ReviewQuestionModel reviewQuestion : review.getListReviewQuestion()) {
      avg += reviewQuestion.getScore();
    }

    review.setAvgScore((float) avg / review.getListReviewQuestion().size());
    reviewDb.save(review);
  }

  @Override
  public ReviewModel getReviewModel(long id) {
    return reviewDb.getOne(id);
  }

  @Override
  public List<ReviewModel> getReviewByTypeAndReviewer(String reviewType, Long userId) {
    List<ReviewModel> termReview = reviewDb.findByTypeAndReviewer(reviewType, userDb.getOne(userId));

    int year = Calendar.getInstance().get(Calendar.YEAR);

    // filtering by year
    if(termReview.size() != 0) {
      for(ReviewModel temp: termReview) {
        if((temp.getDate().getYear() + 1900) != year) {
        termReview.remove(temp);
        }
      }
    }
    System.out.println("HAHA");
    System.out.println(termReview.size());
    return termReview;
  }
    
  @Override
  public List<ReviewModel> getPeerReviewer(UserModel reviewee) {
      return reviewDb.findReviewerByReviewee(reviewee);
  }

  @Override
  public List<ReviewModel> getPeerReviewee(UserModel reviewer) {
      return reviewDb.findRevieweeByReviewer(reviewer);
  }
  

  @Override
  public void deleteReview(ReviewModel review) {
      reviewDb.delete(review);
  }

  @Override
  public void updateReview(ReviewModel review) {
      reviewDb.save(review);
  }

  @Override
  public Optional<ReviewModel> getReviewById(long id) {
      return reviewDb.findById(id);
  }

  @Override
  public void addRevieweeAndReviewer(ReviewModel review) {
    ReviewModel temp = reviewDb.findByTypeAndReviewerAndReviewee("peer-review", review.getReviewer(), review.getReviewee());
    System.out.println(temp);
    if(review.getReviewer().getId() != review.getReviewee().getId() && temp == null){
      Date date = new Date();
      review.setDate(new Timestamp(date.getTime()));
      review.setReviewee(review.getReviewee());
      review.setReviewer(review.getReviewer());
      review.setType("peer-review");
      review.setStatus("incomplete");
      review.setAvgScore((float) 0);
      reviewDb.save(review);
    }
  }

}
