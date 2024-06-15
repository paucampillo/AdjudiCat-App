package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.LicitarDTO;
import com.adjudicat.controller.dto.OfertaDTO;
import com.adjudicat.domain.model.mapper.ContracteMapper;
import com.adjudicat.domain.model.mapper.OfertaMapper;
import com.adjudicat.domain.service.*;
import com.adjudicat.repository.entity.ContracteEntity;
import com.adjudicat.repository.entity.OfertaEntity;
import com.adjudicat.repository.repository.OfertaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfertaServiceImpl implements OfertaService {

    private final OfertaRepository ofertaRepository;
    private final OfertaMapper ofertaMapper;
    private final UsuariService usuariService;
    private final ContracteService contracteService;
    private final ContracteMapper contracteMapper;
    private final FavoritsService favoritsService;
    private final MissatgeService missatgeService;

    @Override
    @Transactional
    public void saveOferta(final LicitarDTO licitarDTO) {

        OfertaEntity oferta = ofertaRepository.findAllByContracteIdContracteAndEmpresaIdUsuari(licitarDTO.getIdContracte(),licitarDTO.getIdUsuari()).orElse(null);
        if (oferta != null) {
            ofertaRepository.delete(oferta);
        }

        Double ofertaMasBaja = ofertaRepository.findLowestOfferForContract(licitarDTO.getIdContracte())
                .orElse(Double.MAX_VALUE);

        if (licitarDTO.getQuantitat() < ofertaMasBaja) {
            Long idUsuari = ofertaRepository.getIdUsuariLowestOfferByContract(licitarDTO.getIdContracte());
            if (idUsuari != null) {
                missatgeService.enviarNotificacioSistema(idUsuari, 1, licitarDTO.getIdContracte());
            }
            OfertaDTO ofertaDTO = new OfertaDTO();
            ofertaDTO.setEmpresa(usuariService.get(licitarDTO.getIdUsuari()));
            ofertaDTO.setContracte(contracteService.get(licitarDTO.getIdContracte(), licitarDTO.getIdUsuari()));
            ofertaDTO.setImportAdjudicacioAmbIva(licitarDTO.getQuantitat());
            ofertaDTO.setImportAdjudicacioSenseIva(licitarDTO.getQuantitat() / (1 + 0.21));
            ofertaDTO.setDataHoraOferta(LocalDateTime.now());
            ofertaDTO.setGanadora(false);
            ofertaRepository.save(ofertaMapper.toEntity(ofertaDTO));
            if (oferta == null) {
                ContracteDTO contracte = ofertaDTO.getContracte();
                contracte.setOfertesRebudes(contracte.getOfertesRebudes() + 1);
                contracteService.saveContracte(contracte);
            }
        }
    }

    @Override
    public List<OfertaDTO> getHistoricOfertes(final Long idContracte) {
        List<OfertaEntity> oferta = ofertaRepository.findAllByContracteIdContracteOrderByImportAdjudicacioAmbIva(idContracte).orElse(null);
        if (oferta == null) return Collections.emptyList();

        return ofertaMapper.toDTOList(oferta);
    }

    @Override
    public Page<ContracteDTO> findHistoricOfertesByUsuari(Integer page, Integer rpp, Long idUsuari) {
        Pageable pageable = PageRequest.of(page-1, rpp);
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Page<ContracteEntity> pageContracteEntity = ofertaRepository.findHistoricOfertesByUsuari(idUsuari, currentDate, pageable);
        Page<ContracteDTO> pageContracteDTO = pageContracteEntity.map(contracteMapper::toDTO);
        for (ContracteDTO contracteDTO : pageContracteDTO) {
            contracteDTO.setPreferit(favoritsService.isFavorit(idUsuari, contracteDTO.getIdContracte()));
        }
        return pageContracteDTO;
    }

    @Override
    public Page<ContracteDTO> findOfertesByUsuari(Integer page, Integer rpp, Long idUsuari) {
        Pageable pageable = PageRequest.of(page-1, rpp);
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Page<ContracteEntity> pageContracteEntity = ofertaRepository.findOfertesByUsuari(idUsuari, currentDate, pageable);
        Page<ContracteDTO> pageContracteDTO = pageContracteEntity.map(contracteMapper::toDTO);
        for (ContracteDTO contracteDTO : pageContracteDTO) {
            contracteDTO.setPreferit(favoritsService.isFavorit(idUsuari, contracteDTO.getIdContracte()));
        }
        return pageContracteDTO;
    }

    @Override
    public List<Long> getLicitadorsContracte(Long idContracte) {
        return ofertaRepository.findLicitadorsContracte(idContracte);
    }

    @Override
    @Transactional
    public void deleteOfertesByContracte(Long idContracte) {
        ofertaRepository.deleteAllByContracteIdContracte(idContracte);
    }
}
