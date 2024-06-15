package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.ContracteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ContracteRepository extends JpaRepository<ContracteEntity, Long> {
    @Query("SELECT c.codiExpedient FROM ContracteEntity c")
    List<String> findAllCodiExpedients();

    @Query("SELECT c FROM ContracteEntity c WHERE " +
            "(:codiExpedient IS NULL OR c.codiExpedient LIKE %:codiExpedient%) AND " +
            "(:tipusContracte IS NULL OR c.tipusContracte LIKE %:tipusContracte%) AND " +
            "(:ambit IS NULL OR c.ambit.nom LIKE %:ambit%) AND " +
            "(c.llocExecucio LIKE %:llocExecucio% OR :llocExecucio IS NULL) AND " +
            "(:valorContracte IS NULL OR c.valorEstimatContracte < :valorContracte) AND " +
            "(:fechaActual IS NULL OR c.terminiPresentacioOfertes > :fechaActual) AND " +
            "(c.objecteContracte LIKE %:objecteContracte% OR :objecteContracte IS NULL) AND " +
            "(c.procediment LIKE %:procediment% OR :procediment IS NULL)")
    Page<ContracteEntity> findByFilterPaginated(@Param("codiExpedient") String codiExpedient,
                                                @Param("tipusContracte") String tipusContracte,
                                                @Param("ambit") String ambit,
                                                @Param("procediment") String procediment,
                                                @Param("llocExecucio") String llocExecucio,
                                                @Param("valorContracte") Double valorContracte,
                                                @Param("objecteContracte") String objecteContracte,
                                                @Param("fechaActual") Date fechaActual,
                                                Pageable pageable);

    @Query("SELECT c FROM ContracteEntity c WHERE " +
            "(:codiExpedient IS NULL OR c.codiExpedient LIKE %:codiExpedient%) AND " +
            "(:tipusContracte IS NULL OR c.tipusContracte LIKE %:tipusContracte%) AND " +
            "(:ambit IS NULL OR c.ambit.nom LIKE %:ambit%) AND " +
            "(c.llocExecucio LIKE %:llocExecucio% OR :llocExecucio IS NULL) AND " +
            "(:valorContracte IS NULL OR c.valorEstimatContracte < :valorContracte) AND " +
            "(:fechaActual IS NULL OR c.terminiPresentacioOfertes < :fechaActual) AND " +
            "(c.objecteContracte LIKE %:objecteContracte% OR :objecteContracte IS NULL) AND " +
            "(c.procediment LIKE %:procediment% OR :procediment IS NULL)")
    Page<ContracteEntity> findByFilterPaginatedHistoric(@Param("codiExpedient") String codiExpedient,
                                                        @Param("tipusContracte") String tipusContracte,
                                                        @Param("ambit") String ambit,
                                                        @Param("procediment") String procediment,
                                                        @Param("llocExecucio") String llocExecucio,
                                                        @Param("valorContracte") Double valorContracte,
                                                        @Param("objecteContracte") String objecteContracte,
                                                        @Param("fechaActual") Date fechaActual,
                                                        Pageable pageable);

    @Query("SELECT c FROM ContracteEntity c WHERE c.codiExpedient LIKE %:codiExpedient%")
    ContracteEntity findByCodi(@Param("codiExpedient") String codiExpedient);

    @Query("SELECT c FROM ContracteEntity c WHERE " +
            "(c.usuariCreacio.idUsuari = :idUsuariCreador) AND " +
            "(c.terminiPresentacioOfertes < :fechaActual)")
    Page<ContracteEntity> findByUsuariCreacioIdHistoric(@Param("idUsuariCreador") Long idUsuariCreador,
                                                        @Param("fechaActual") Date fechaActual,
                                                        Pageable pageable);

    @Query("SELECT c FROM ContracteEntity c WHERE " +
            "(c.usuariCreacio.idUsuari = :idUsuariCreador) AND " +
            "(c.terminiPresentacioOfertes > :fechaActual)")
    Page<ContracteEntity> findByUsuariCreacioId(@Param("idUsuariCreador") Long idUsuariCreador,
                                                @Param("fechaActual") Date fechaActual,
                                                Pageable pageable);


    @Query("SELECT c FROM ContracteEntity c WHERE c.terminiPresentacioOfertes BETWEEN :start AND :end")
    List<ContracteEntity> findContractsByTerminiBetween(Date start, Date end);

}