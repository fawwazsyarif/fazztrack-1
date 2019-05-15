package com.payfazz.hello.service;

import com.payfazz.hello.model.ActivePhaseModel;
import com.payfazz.hello.repository.ActivePhaseDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ActivePhaseServiceImpl implements ActivePhaseService{

    @Autowired
    private ActivePhaseDB activePhaseDB;

    @Override
    public void addPhase(ActivePhaseModel phase) {
        activePhaseDB.save(phase);
    }

    @Override
    public ActivePhaseModel findActivePhaseById(long id) {
        return activePhaseDB.findById(id);
    }

    @Override
    public List<ActivePhaseModel> findAll(){
        return activePhaseDB.findAll();
    }

    @Override
    public ActivePhaseModel findActivePhaseByStatus(String status) {
        return activePhaseDB.findByStatus(status);
    }
}