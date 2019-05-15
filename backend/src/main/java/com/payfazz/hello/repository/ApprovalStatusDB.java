package com.payfazz.hello.repository;

import com.payfazz.hello.model.ApprovalStatusModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalStatusDB extends JpaRepository<ApprovalStatusModel, Long>{
    
}