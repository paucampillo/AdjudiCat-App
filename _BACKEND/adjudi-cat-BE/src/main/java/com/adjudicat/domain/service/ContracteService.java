package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FiltreDTO;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.repository.entity.ContracteEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ContracteService {
    void saveContractes(List<ContracteDTO> contracteDTOs) throws AdjudicatBaseException;

    Page<ContracteDTO> findPaginated(FiltreDTO filtreDTO, Integer rpp, Integer page, String sort, Long idUsuari);

    Page<ContracteDTO> findPaginatedHistoric(FiltreDTO filtreDTO, Integer rpp, Integer page, String sort, Long idUsuari);

    Page<ContracteDTO> findPaginatedHistoricCreador(Integer rpp, Integer page, Long idUsuariCreador);

    Page<ContracteDTO> findPaginatedCreador(Integer rpp, Integer page, Long idUsuariCreador);

    Set<String> getAllCodiExpedient();

    void saveContracte(ContracteDTO contracteDTO) throws DataAccessException;

    void deleteContracte(Long idContracte);

    ContracteEntity findByCodi(String codiExpedient);

    ContracteDTO get(Long id, Long idUsuari);

}
