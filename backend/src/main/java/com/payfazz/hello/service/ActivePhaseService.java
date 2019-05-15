package com.payfazz.hello.service;

import com.payfazz.hello.model.ActivePhaseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivePhaseService {
    void addPhase(ActivePhaseModel phase);
    ActivePhaseModel findActivePhaseById(long id);
    ActivePhaseModel findActivePhaseByStatus(String status);
    List<ActivePhaseModel> findAll();

}
