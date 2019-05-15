package com.payfazz.hello.service;

import com.payfazz.hello.model.ReviewQuestionModel;
import com.payfazz.hello.repository.ReviewQuestionDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewQuestionServiceImpl implements ReviewQuestionService {
    @Autowired
    private ReviewQuestionDB reviewQuestionDb;

    @Override
    public void addReviewQuestion(ReviewQuestionModel answeredQuestion) {
        reviewQuestionDb.save(answeredQuestion);
	}


}
