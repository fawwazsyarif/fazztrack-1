package com.payfazz.hello.service;

import java.util.List;

import com.payfazz.hello.model.FeedbackModel;
import com.payfazz.hello.repository.FeedbackDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    public FeedbackDB feedbackDb;
    
    public void addFeedback(FeedbackModel feedback){
        feedbackDb.save(feedback);
    }

    public List<FeedbackModel> getAllFeedback(){
        List<FeedbackModel> feedbackList = feedbackDb.findAll();
        return feedbackList;
    }
}