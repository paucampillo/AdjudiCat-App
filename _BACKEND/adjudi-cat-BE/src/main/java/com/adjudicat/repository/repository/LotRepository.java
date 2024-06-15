package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.LotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LotRepository extends JpaRepository<LotEntity, Long> {
    @Query("Select l.numero from LotEntity l")
    List<String> findAllNumeroLots();

    LotEntity findByNumero(String numero);

    Optional<List<LotEntity>> findAllByDescripcioContainingIgnoreCaseOrderByDescripcio(String nombre);
}