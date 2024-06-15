package com.adjudicat.repository.repository;

import com.adjudicat.enums.IdiomaEnum;
import com.adjudicat.repository.entity.IdiomesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdiomesRepository extends JpaRepository<IdiomesEntity, Long> {
    IdiomesEntity findByCodi(String codi);
}