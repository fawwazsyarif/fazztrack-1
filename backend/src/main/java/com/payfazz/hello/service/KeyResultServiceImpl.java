package com.payfazz.hello.service;

import com.payfazz.hello.model.KeyResultModel;
import com.payfazz.hello.repository.KeyResultDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KeyResultServiceImpl implements KeyResultService {
    
    @Autowired
    private KeyResultDB keyResultDb;
    
    @Override
    public void addKR(KeyResultModel keyResult) {
        keyResultDb.save(keyResult);
    }

    @Override
    public KeyResultModel findKeyResultById(long id){
        return keyResultDb.findById(id).get();
    }
}
