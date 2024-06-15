package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.AmbitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmbitRepository extends JpaRepository<AmbitEntity, Long> {
    @Query("SELECT e.codi FROM AmbitEntity e")
    List<String> findAllCodiAmbits();

    AmbitEntity findByCodi(Long codi);
}