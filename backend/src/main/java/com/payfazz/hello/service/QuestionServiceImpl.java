package com.payfazz.hello.service;

import java.util.List;

import javax.transaction.Transactional;

import com.payfazz.hello.model.QuestionModel;
import com.payfazz.hello.repository.QuestionDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDB questionDb;

	@Override
	public List<QuestionModel> getAllQuestion() {
		return questionDb.findAll() ;
	}

}
