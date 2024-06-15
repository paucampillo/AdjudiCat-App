package com.adjudicat.repository.repository;

import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.enums.RolsEnum;
import com.adjudicat.repository.entity.UsuariEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Optional;

public interface UsuariRepository extends JpaRepository<UsuariEntity, Long> {
    @Query("select u.identNif from UsuariEntity u")
    List<String> findAllIdAdjudicatari();
    Optional<UsuariEntity> findByEmail(String email);

    Optional<UsuariEntity> findFirstByEmailAndContrasenya(String email, String contrasenya);

    Optional<UsuariEntity> findFirstByEmail(String email);

    Page<UsuariEntity> findAllByRolCodiIsNot(String codi, Pageable pageable);
}