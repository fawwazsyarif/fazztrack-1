package com.payfazz.hello.repository;

import com.payfazz.hello.model.ReviewQuestionModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewQuestionDB extends JpaRepository<ReviewQuestionModel, Long>{

}
