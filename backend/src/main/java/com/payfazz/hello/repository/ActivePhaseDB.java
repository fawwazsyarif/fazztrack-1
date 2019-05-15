package com.payfazz.hello.repository;

import com.payfazz.hello.model.ActivePhaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivePhaseDB extends JpaRepository<ActivePhaseModel, Long> {
    ActivePhaseModel findById(long id);
    ActivePhaseModel findByStatus(String status);

}
