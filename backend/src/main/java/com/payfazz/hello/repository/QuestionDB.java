package com.payfazz.hello.repository;

import java.util.List;

import com.payfazz.hello.model.QuestionModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDB extends JpaRepository<QuestionModel, Long>{
    List<QuestionModel> findAll();

}
