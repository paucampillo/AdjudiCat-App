package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.ContracteEntity;
import com.adjudicat.repository.entity.FavoritsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FavoritsRepository extends JpaRepository<FavoritsEntity, Long> {

    Optional<FavoritsEntity> findByUsuariIdUsuariAndContracteIdContracte(Long IdUsuari, Long IdContracte);

    @Query("SELECT f.contracte FROM FavoritsEntity f WHERE" +
            "(f.usuari.idUsuari = :idUser)")
    Page<ContracteEntity> findAllByUsuariIdUsuari(@Param("idUser")Long idUser, Pageable pageable);

    @Modifying
    @Query("DELETE FROM FavoritsEntity f WHERE f.contracte.idContracte = :idContracte")
    void deleteAll(Long idContracte);
}