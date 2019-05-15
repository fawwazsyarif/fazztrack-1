package com.payfazz.hello.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payfazz.hello.model.CompMonthSumModel;
@Repository
public interface CompMonthSumDB extends JpaRepository<CompMonthSumModel, Long>{

}
