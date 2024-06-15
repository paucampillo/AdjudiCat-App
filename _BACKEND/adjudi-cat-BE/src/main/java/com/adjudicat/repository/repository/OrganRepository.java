package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.OrganEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganRepository extends JpaRepository<OrganEntity, Long> {
    @Query("SELECT o.codi FROM OrganEntity o")
    List<Long> findAllCodiOrgan();
    OrganEntity findByCodi(Long codi);

    Optional<List<OrganEntity>> findAllByNomContainingIgnoreCaseOrderByNom(String nombre);
}