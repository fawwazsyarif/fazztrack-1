package com.payfazz.hello.service;

import com.payfazz.hello.model.CommentModel;
import com.payfazz.hello.repository.CommentDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
	private CommentDB commentDb; 

    @Override
    public CommentModel addComment(CommentModel comment) {
        return commentDb.save(comment);
    }

    @Override
    public void removeById(long id) {
        commentDb.deleteById(id);
    }

}
