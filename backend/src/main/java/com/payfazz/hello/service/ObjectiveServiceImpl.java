package com.payfazz.hello.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

import com.payfazz.hello.model.ApprovalStatusModel;
import com.payfazz.hello.model.ObjectiveModel;
import com.payfazz.hello.repository.ApprovalStatusDB;
import com.payfazz.hello.repository.ObjectiveDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObjectiveServiceImpl implements ObjectiveService {
    @Autowired
    private ObjectiveDB ObjectiveDb;

    @Autowired
    private ApprovalStatusDB ApprovalStatusDb;

    @Override
    public ObjectiveModel getObjectiveDetailById(long id) {
        return ObjectiveDb.findById(id);
    }

    @Override
    public List<ObjectiveModel> getObjectiveforApproval(long id) {
        ApprovalStatusModel status = ApprovalStatusDb.getOne(id);
        List<ObjectiveModel> objectiveForApproval = ObjectiveDb.findAllByApprovalStatus(status);
		return objectiveForApproval;
	}

    @Override
    public void addObjective(ObjectiveModel objective) {
        Date date = new Date();
        objective.setCreatedDate(new Timestamp(date.getTime()));
		objective.setLastModifiedDate(new Timestamp(date.getTime()));
        ObjectiveDb.save(objective);
    }

    @Override
    public ObjectiveModel editObjective(ObjectiveModel objective) {
		Date date = new Date();
		objective.setLastModifiedDate(new Timestamp(date.getTime()));
        return ObjectiveDb.save(objective);
    }

    @Override
    public void deleteObjective(long id) {
        ObjectiveDb.deleteById(id);
    }


    @Override
    public void updateObjectiveStatus(ObjectiveModel objective) {
        ObjectiveModel obj = ObjectiveDb.getOne(objective.getId());
        ApprovalStatusModel status = ApprovalStatusDb.getOne(objective.getApprovalStatus().getId());
        obj.setApprovalStatus(status);
        ObjectiveDb.save(obj);

    }

    @Override
    public List<ObjectiveModel> filterAllObjectiveExcludeRecommended(List<ObjectiveModel> listObj) {
        List<ObjectiveModel> listBaru = new ArrayList<ObjectiveModel>();
        
        for(ObjectiveModel objective : listObj){
            if(!objective.getApprovalStatus().getStatus().equals("recommended")){
                listBaru.add(objective);
            }
        }
        
        return listBaru;
    }
    
}
