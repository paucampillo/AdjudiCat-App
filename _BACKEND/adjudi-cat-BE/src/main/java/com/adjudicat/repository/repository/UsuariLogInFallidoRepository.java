package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.UsuariLogInFallidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariLogInFallidoRepository extends JpaRepository<UsuariLogInFallidoEntity, Long> {
    Optional<UsuariLogInFallidoEntity> findByUsuariIdUsuari(Long idUsuariBloquejat);

}