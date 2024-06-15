package com.adjudicat.domain.impl;

import com.adjudicat.constants.AdjudicatConstants;
import com.adjudicat.controller.dto.ConversacionDTO;
import com.adjudicat.controller.dto.MissatgeDTO;
import com.adjudicat.domain.model.mapper.MissatgeMapper;
import com.adjudicat.domain.service.MissatgeService;
import com.adjudicat.domain.service.UsuariService;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import com.adjudicat.repository.entity.MissatgeEntity;
import com.adjudicat.repository.repository.MissatgeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissatgeServiceImpl implements MissatgeService {

    private final MissatgeRepository missatgeRepository;
    private final MissatgeMapper missatgeMapper;
    private final UsuariService usuariService;

    @Override
    public void enviarMissatge(Long idEmisor, Long idReceptor, String mensaje) {
        MissatgeDTO missatgeDTO = new MissatgeDTO();
        missatgeDTO.setEmissor(usuariService.get(idEmisor));
        missatgeDTO.setReceptor(usuariService.get(idReceptor));
        missatgeDTO.setMissatge(mensaje);
        missatgeDTO.setDataHoraEnvio(LocalDateTime.now());
        missatgeRepository.save(missatgeMapper.toEntity(missatgeDTO));
    }

    @Override
    public void deleteMissatge(Long idMissatge) {
        MissatgeEntity missatgeEntity = missatgeRepository.getReferenceById(idMissatge);
        missatgeRepository.delete(missatgeEntity);
    }

    @Override
    public Page<MissatgeDTO> findPaginatedHistoric(Integer rpp, Integer page, Long idEmisor, Long idReceptor) {
        Pageable pageable = PageRequest.of(page-1, rpp);
        Page<MissatgeEntity> missatgeEntityPage = missatgeRepository.findPaginatedHistoric(idEmisor, idReceptor, pageable);
        return missatgeEntityPage.map(missatgeMapper::toDTO);
    }

    @Override
    public List<ConversacionDTO> findConverses(Long idUsuari) {
        return missatgeRepository.findConverses(idUsuari);
    }

    @Override
    @Transactional
    public void deleteConverse(Long idEmissor, Long idReceptor) throws DataAccessException {
        missatgeRepository.deleteConverse(idEmissor, idReceptor);
    }

    @Override
    public void enviarNotificacioSistema(Long idUsuari, Integer tipus, Long idContracte) {
        switch (tipus) {
            case AdjudicatConstants.NOTIFICATION_TYPE_OFFER_SURPASSED:
                enviarMissatge(AdjudicatConstants.SYSTEM_USER_ID, idUsuari, "S'ha superat la teva oferta al contracte amb id: " + idContracte);
                break;
            case AdjudicatConstants.NOTIFICATION_TYPE_CONTRACT_DELETED:
                enviarMissatge(AdjudicatConstants.SYSTEM_USER_ID, idUsuari, "S'ha eliminat el contracte amb id: " + idContracte);
                break;
            case AdjudicatConstants.NOTIFICATION_TYPE_THIRD_CASE:
                enviarMissatge(AdjudicatConstants.SYSTEM_USER_ID, idUsuari, "Ha finalitzat el termini de presentació d'ofertes al contracte amb id: " + idContracte);
                break;
            case AdjudicatConstants.NOTIFICATION_TYPE_FOURTH_CASE:
                enviarMissatge(AdjudicatConstants.SYSTEM_USER_ID, idUsuari, "Queda poc temps per finalitzar el termini de presentació d'ofertes al contracte amb id: " + idContracte);
                break;
        }
    }
}
