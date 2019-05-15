package com.payfazz.hello.service;

import java.util.List;

import com.payfazz.hello.model.ObjectiveModel;

public interface ObjectiveService {
    ObjectiveModel getObjectiveDetailById(long id);
	//ObjectiveModel addObjective(ObjectiveModel obj);
    List<ObjectiveModel> getObjectiveforApproval(long idStatus);

    void addObjective(ObjectiveModel objective);

    ObjectiveModel editObjective(ObjectiveModel objective);

    void deleteObjective(long id);
    
    public void updateObjectiveStatus(ObjectiveModel objective);

    List<ObjectiveModel> filterAllObjectiveExcludeRecommended(List<ObjectiveModel> listObj);
}
