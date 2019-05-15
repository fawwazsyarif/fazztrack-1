package com.payfazz.hello.service;

import org.springframework.stereotype.Service;

import com.payfazz.hello.model.CompMonthSumModel;
@Service
public interface CompMonthSumService {

	CompMonthSumModel addCompSummary(CompMonthSumModel submitObjComp);

}
