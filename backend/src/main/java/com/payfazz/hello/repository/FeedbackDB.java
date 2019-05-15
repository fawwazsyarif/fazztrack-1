package com.payfazz.hello.repository;

import java.util.List;

import com.payfazz.hello.model.FeedbackModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackDB extends JpaRepository<FeedbackModel, Long> {
    List<FeedbackModel> findAll();

}
