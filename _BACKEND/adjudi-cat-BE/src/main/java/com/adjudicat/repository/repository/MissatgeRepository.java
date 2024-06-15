package com.adjudicat.repository.repository;

import com.adjudicat.controller.dto.ConversacionDTO;
import com.adjudicat.repository.entity.MissatgeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissatgeRepository extends JpaRepository<MissatgeEntity, Long> {

    @Query("SELECT m FROM MissatgeEntity m WHERE " +
            "(m.emissor.idUsuari = :idEmisor AND m.receptor.idUsuari = :idReceptor) OR " +
            "(m.emissor.idUsuari = :idReceptor AND m.receptor.idUsuari = :idEmisor) " +
            "ORDER BY m.idMissatge DESC")
    Page<MissatgeEntity> findPaginatedHistoric(@Param("idEmisor") Long idEmisor,
                                               @Param("idReceptor") Long idReceptor,
                                               Pageable pageable);

    @Query("SELECT DISTINCT new com.adjudicat.controller.dto.ConversacionDTO(m.receptor.idUsuari, m.receptor.nom) FROM MissatgeEntity m WHERE " +
            "m.emissor.idUsuari = :idUsuari UNION " +
            "SELECT DISTINCT new com.adjudicat.controller.dto.ConversacionDTO(m.emissor.idUsuari, m.emissor.nom) FROM MissatgeEntity m WHERE " +
            "m.receptor.idUsuari = :idUsuari")
    List<ConversacionDTO> findConverses(@Param("idUsuari") Long idUsuari);

    @Modifying
    @Query("DELETE FROM MissatgeEntity WHERE " +
            "(emissor.idUsuari = :idEmissor AND receptor.idUsuari = :idReceptor) OR " +
            "(emissor.idUsuari = :idReceptor AND receptor.idUsuari = :idEmissor)")
    void deleteConverse(@Param("idEmissor") Long idEmissor, @Param("idReceptor") Long idReceptor);
}