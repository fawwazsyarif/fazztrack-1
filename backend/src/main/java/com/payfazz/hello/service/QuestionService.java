package com.payfazz.hello.service;

import java.util.List;

import com.payfazz.hello.model.QuestionModel;
import com.payfazz.hello.repository.QuestionDB;

import org.springframework.beans.factory.annotation.Autowired;

public interface QuestionService {
    List <QuestionModel> getAllQuestion();
}
