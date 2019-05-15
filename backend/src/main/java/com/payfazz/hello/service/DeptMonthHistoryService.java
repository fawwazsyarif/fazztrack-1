package com.payfazz.hello.service;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.payfazz.hello.model.DeptMonthHistoryModel;
@Service
public interface DeptMonthHistoryService {

	List<DeptMonthHistoryModel> findByDate(Timestamp date1, Timestamp date2);

	DeptMonthHistoryModel addDeptHistory(DeptMonthHistoryModel deptHistory);

}
