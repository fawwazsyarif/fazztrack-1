package com.payfazz.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payfazz.hello.model.RoleModel;

import org.springframework.stereotype.Repository;
@Repository
public interface RoleDB extends JpaRepository<RoleModel, Long>{

}
