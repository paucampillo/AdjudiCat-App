package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.ContracteEntity;
import com.adjudicat.repository.entity.OfertaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OfertaRepository extends JpaRepository<OfertaEntity, Long> {

    @Query("SELECT MIN(o.importAdjudicacioAmbIva) FROM OfertaEntity o WHERE o.contracte.idContracte = :idContracte")
    Optional<Double> findLowestOfferForContract(@Param("idContracte") Long idContracte);

    Optional<List<OfertaEntity>> findAllByContracteIdContracteOrderByImportAdjudicacioAmbIva (Long idContracte);

    Optional<OfertaEntity> findAllByContracteIdContracteAndEmpresaIdUsuari(Long idContracte, Long idUsuari);

    @Query("SELECT o.contracte FROM OfertaEntity o WHERE " +
            "(o.empresa.idUsuari = :idUsuari) AND " +
            "(o.contracte.terminiPresentacioOfertes < :fechaActual)")
    Page<ContracteEntity> findHistoricOfertesByUsuari(@Param("idUsuari") Long idUsuari,
                                                   @Param("fechaActual") Date fechaActual,
                                                   Pageable pageable);

    @Query("SELECT o.contracte FROM OfertaEntity o WHERE " +
            "(o.empresa.idUsuari = :idUsuari) AND " +
            "(o.contracte.terminiPresentacioOfertes > :fechaActual)")
    Page<ContracteEntity> findOfertesByUsuari(@Param("idUsuari") Long idUsuari,
                                              @Param("fechaActual") Date fechaActual,
                                              Pageable pageable);

    @Query("SELECT DISTINCT o.empresa.idUsuari FROM OfertaEntity o WHERE o.contracte.idContracte = :idContracte")
    List<Long> findLicitadorsContracte(Long idContracte);

    @Modifying
    @Transactional
    void deleteAllByContracteIdContracte(Long idContracte);

    @Query("SELECT o.empresa.idUsuari FROM OfertaEntity o WHERE o.contracte.idContracte = :idContracte ORDER BY o.importAdjudicacioAmbIva ASC")
    Long getIdUsuariLowestOfferByContract(Long idContracte);


}