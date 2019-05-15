package com.payfazz.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payfazz.hello.model.DeptMonthHistoryModel;
@Repository
public interface DeptMonthHistoryDB extends JpaRepository<DeptMonthHistoryModel, Long>{

}
