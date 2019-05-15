package com.payfazz.hello.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payfazz.hello.model.CommentModel;
@Repository
public interface CommentDB extends JpaRepository<CommentModel, Long>{

}
