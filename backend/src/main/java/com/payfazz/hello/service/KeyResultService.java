package com.payfazz.hello.service;

import com.payfazz.hello.model.KeyResultModel;

import org.springframework.stereotype.Service;
@Service
public interface KeyResultService {
    void addKR(KeyResultModel keyResult);
    KeyResultModel findKeyResultById(long id);
}
