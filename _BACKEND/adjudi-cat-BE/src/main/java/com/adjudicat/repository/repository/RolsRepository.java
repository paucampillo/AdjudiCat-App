package com.adjudicat.repository.repository;

import com.adjudicat.controller.dto.RolsDTO;
import com.adjudicat.repository.entity.RolsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolsRepository extends JpaRepository<RolsEntity, Long> {

    List<RolsEntity> findAllByVisibleIsTrue();
    RolsEntity findByCodi(String code);
}