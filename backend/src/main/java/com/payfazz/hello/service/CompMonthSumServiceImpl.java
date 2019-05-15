package com.payfazz.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payfazz.hello.model.CompMonthSumModel;
import com.payfazz.hello.repository.CompMonthSumDB;
@Service
@Transactional
public class CompMonthSumServiceImpl implements CompMonthSumService {

	@Autowired
	private CompMonthSumDB compMonthSumDb;
	
	@Override
	public CompMonthSumModel addCompSummary(CompMonthSumModel submitObjComp) {
		// TODO Auto-generated method stub
		System.out.println("masuk sini");
		System.out.println(submitObjComp.getId());
		System.out.println(submitObjComp.getDate());
		System.out.println(submitObjComp.getCompObjective());
		return compMonthSumDb.save(submitObjComp);
	}

}
