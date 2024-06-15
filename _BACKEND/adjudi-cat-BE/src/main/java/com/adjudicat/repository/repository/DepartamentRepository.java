package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.DepartamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartamentRepository extends JpaRepository<DepartamentEntity, Long> {
    @Query("SELECT d.codi FROM DepartamentEntity d")
    List<Long> findAllCodiDepartamentEns();

    DepartamentEntity findByCodi(Long codi);
}
