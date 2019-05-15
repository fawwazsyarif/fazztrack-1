package com.payfazz.hello.service;

import java.util.List;

import com.payfazz.hello.model.FeedbackModel;

import org.springframework.stereotype.Service;

@Service
public interface FeedbackService{
    void addFeedback(FeedbackModel feedback);
    List<FeedbackModel> getAllFeedback();
}