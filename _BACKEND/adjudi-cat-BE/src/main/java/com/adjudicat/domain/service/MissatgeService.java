package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ConversacionDTO;
import com.adjudicat.controller.dto.MissatgeDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MissatgeService {
    void enviarMissatge(Long idEmisor, Long idReceptor, String mensaje);
    void deleteMissatge(Long idMissatge);
    Page<MissatgeDTO> findPaginatedHistoric(Integer rpp, Integer page, Long idEmisor, Long idReceptor);
    List<ConversacionDTO> findConverses(Long idUsuari);
    void deleteConverse(Long idEmissor, Long idReceptor) throws DataAccessException;
    void enviarNotificacioSistema(Long idUsuari, Integer tipus, Long idContracte);
}
