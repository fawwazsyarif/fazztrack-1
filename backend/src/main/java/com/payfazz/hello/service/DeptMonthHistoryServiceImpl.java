package com.payfazz.hello.service;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payfazz.hello.model.DeptMonthHistoryModel;
import com.payfazz.hello.repository.DeptMonthHistoryDB;
@Service
@Transactional
public class DeptMonthHistoryServiceImpl implements DeptMonthHistoryService{

	@Autowired
	private DeptMonthHistoryDB deptMonthHistoryDb;

	@Override
	public DeptMonthHistoryModel addDeptHistory(DeptMonthHistoryModel deptHistory) {
		return deptMonthHistoryDb.save(deptHistory);
	}

	@Override
	public List<DeptMonthHistoryModel> findByDate(Timestamp date1, Timestamp date2) {
		
		return null;
	}

}
