package com.adjudicat.repository.repository;

import com.adjudicat.controller.dto.ValoracioInfoDTO;
import com.adjudicat.repository.entity.ValoracioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface ValoracioRepository extends JpaRepository<ValoracioEntity, Long> {

    @Query ("SELECT v FROM ValoracioEntity v WHERE v.receptor.idUsuari = :idUsuari")
    Page<ValoracioEntity> findByIdUsuari(@PathVariable("idUsuari") Long idUsuari, Pageable pageable);

    @Query ("SELECT v FROM ValoracioEntity v WHERE v.autor.idUsuari = :idUsuari")
    Page<ValoracioEntity> findByIdUsuariEmisor(@PathVariable("idUsuari") Long idUsuari, Pageable pageable);

    @Query("SELECT new com.adjudicat.controller.dto.ValoracioInfoDTO(COALESCE(AVG(v.puntuacio), 0), COUNT(v)) " +
            "FROM ValoracioEntity v WHERE v.receptor.idUsuari = :idUsuari")
    ValoracioInfoDTO findAverageAndTotalRatingsByUserId(@Param("idUsuari") Long idUsuari);

    @Query("SELECT new com.adjudicat.controller.dto.ValoracioInfoDTO(COALESCE(AVG(v.puntuacio), 0), COUNT(v)) " +
            "FROM ValoracioEntity v WHERE v.autor.idUsuari = :idUsuari")
    ValoracioInfoDTO findAverageAndTotalRatingsByUserIdEmisor(@Param("idUsuari") Long idUsuari);
}