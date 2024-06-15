package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FiltreDTO;
import com.adjudicat.exception.RepositoryException;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Api(tags = {"Contracte API"})
public interface ContracteAPI {

    ResponseEntity<Page<ContracteDTO>> findPaginated(Integer page, Integer rpp, String sort, Long idUsuari, FiltreDTO filtreDTO);

    ResponseEntity<Page<ContracteDTO>> findPaginatedHistoric(Integer page, Integer rpp, String sort, Long idUsuari, FiltreDTO filtreDTO);

    ResponseEntity<Page<ContracteDTO>> findPaginatedHistoricCreador(Integer page, Integer rpp, Long idUsuariCreador);

    ResponseEntity<Page<ContracteDTO>> findPaginatedCreador(Integer page, Integer rpp, Long idUsuariCreador);

    ResponseEntity<Object> saveContracte(ContracteDTO contracteDTO);

    ResponseEntity<Object> deleteContracte(Long idContracte) throws RepositoryException;

    ResponseEntity<ContracteDTO> findByCodiExpedient(String codiExpedient) throws RepositoryException ;

    ResponseEntity<ContracteDTO> findByIdContracte(Long idContracte, Long idUsuari);


}
