package com.payfazz.hello.repository;

import java.util.List;

import com.payfazz.hello.model.ApprovalStatusModel;
import com.payfazz.hello.model.ObjectiveModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveDB extends JpaRepository<ObjectiveModel, Long>{
    ObjectiveModel findById(long id);
    List<ObjectiveModel> findAllByApprovalStatus(ApprovalStatusModel approvalStatus);
}
