package com.payfazz.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payfazz.hello.model.KeyResultModel;
@Repository
public interface KeyResultDB extends JpaRepository<KeyResultModel, Long>{

}
