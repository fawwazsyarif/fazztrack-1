package com.payfazz.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payfazz.hello.model.DepartmentModel;

import org.springframework.stereotype.Repository;
@Repository
public interface DepartmentDB extends JpaRepository<DepartmentModel, Long>{
    DepartmentModel findByNameIgnoreCase(String name);

}
