package com.payfazz.hello.service;

import com.payfazz.hello.model.CommentModel;

import org.springframework.stereotype.Service;
@Service
public interface CommentService {
    CommentModel addComment(CommentModel comment);
    void removeById(long id);
}
