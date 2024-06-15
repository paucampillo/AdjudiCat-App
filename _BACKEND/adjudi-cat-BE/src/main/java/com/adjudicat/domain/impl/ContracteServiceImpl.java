package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FiltreDTO;
import com.adjudicat.domain.model.mapper.ContracteMapper;
import com.adjudicat.domain.service.*;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import com.adjudicat.repository.entity.ContracteEntity;
import com.adjudicat.repository.repository.ContracteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ContracteServiceImpl implements ContracteService {

    private final ContracteRepository contracteRepository;
    private final ContracteMapper contracteMapper;
    private final AmbitService ambitService;
    private final LotService lotService;
    private final FavoritsService favoritsService;
    private final OfertaDeleteService ofertaDeleteService;
    private final FavoritDeleteService favoritsDeleteService;
    private final MissatgeService missatgeService;

    @Override
    @Transactional
    public void saveContractes(List<ContracteDTO> contracteDTOs) throws AdjudicatBaseException {
        for (ContracteDTO dto : contracteDTOs) {
            try {
                if (dto.getAmbit() != null) dto.setAmbit(ambitService.getAmbit(dto.getAmbit().getCodi()));
                if (dto.getLot() != null) dto.setLot(lotService.getLot(dto.getLot().getNumero()));
                if (dto.getObjecteContracte() != null && dto.getObjecteContracte().length() > 512) dto.setObjecteContracte(dto.getObjecteContracte().substring(0, 511));
                if (dto.getSubtipusContracte() != null && dto.getSubtipusContracte().length() > 512) dto.setSubtipusContracte(dto.getSubtipusContracte().substring(0, 511));
                if (dto.getEnllacPublicacio() != null && dto.getEnllacPublicacio().length() > 255) dto.setEnllacPublicacio(null);
                contracteRepository.save(contracteMapper.toEntity(dto));
            } catch (DataAccessException e) {
                throw new RepositoryException(e, RepositoryConstants.ERROR_BBDD);
            }
        }
    }

    @Override
    public Page<ContracteDTO> findPaginated(FiltreDTO filtreDTO, Integer rpp, Integer page, String sort, Long idUsuari) {
        Pageable pageable = PageRequest.of(page-1, rpp, getOrdenacio(sort));
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Page<ContracteEntity> contracteEntityPage = contracteRepository.findByFilterPaginated(
                filtreDTO.getCodiExpedient(),
                filtreDTO.getTipusContracte(),
                filtreDTO.getAmbit(),
                filtreDTO.getProcediment(),
                filtreDTO.getLlocExecucio(),
                filtreDTO.getValorContracte(),
                filtreDTO.getObjecteContracte(),
                currentDate,
                pageable);
        Page<ContracteDTO> contracteDTOPage = contracteEntityPage.map(contracteMapper::toDTO);
        for (ContracteDTO contracteDTO : contracteDTOPage) {
            contracteDTO.setPreferit(favoritsService.isFavorit(idUsuari, contracteDTO.getIdContracte()));
        }
        return contracteDTOPage;
    }

    @Override
    public Page<ContracteDTO> findPaginatedHistoric(FiltreDTO filtreDTO, Integer rpp, Integer page, String sort, Long idUsuari) {
        Pageable pageable = PageRequest.of(page-1, rpp, getOrdenacio(sort));
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Page<ContracteEntity> contracteEntityPage = contracteRepository.findByFilterPaginatedHistoric(
                filtreDTO.getCodiExpedient(),
                filtreDTO.getTipusContracte(),
                filtreDTO.getAmbit(),
                filtreDTO.getProcediment(),
                filtreDTO.getLlocExecucio(),
                filtreDTO.getValorContracte(),
                filtreDTO.getObjecteContracte(),
                currentDate,
                pageable);
        Page<ContracteDTO> contracteDTOPage = contracteEntityPage.map(contracteMapper::toDTO);
        for (ContracteDTO contracteDTO : contracteDTOPage) {
            contracteDTO.setPreferit(favoritsService.isFavorit(idUsuari, contracteDTO.getIdContracte()));
        }
        return contracteDTOPage;
    }

    @Override
    public Page<ContracteDTO> findPaginatedHistoricCreador(Integer rpp, Integer page, Long idUsuariCreador) {
        Pageable pageable = PageRequest.of(page-1, rpp);
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Page<ContracteEntity> contracteEntityPage = contracteRepository.findByUsuariCreacioIdHistoric(idUsuariCreador, currentDate, pageable);
        Page<ContracteDTO> contracteDTOPage = contracteEntityPage.map(contracteMapper::toDTO);
        for (ContracteDTO contracteDTO : contracteDTOPage) {
            contracteDTO.setPreferit(favoritsService.isFavorit(idUsuariCreador, contracteDTO.getIdContracte()));
        }
        return contracteDTOPage;
    }

    @Override
    public Page<ContracteDTO> findPaginatedCreador(Integer rpp, Integer page, Long idUsuariCreador) {
        Pageable pageable = PageRequest.of(page-1, rpp);
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Page<ContracteEntity> contracteEntityPage = contracteRepository.findByUsuariCreacioId(idUsuariCreador, currentDate, pageable);
        Page<ContracteDTO> contracteDTOPage = contracteEntityPage.map(contracteMapper::toDTO);
        for (ContracteDTO contracteDTO : contracteDTOPage) {
            contracteDTO.setPreferit(favoritsService.isFavorit(idUsuariCreador, contracteDTO.getIdContracte()));
        }
        return contracteDTOPage;
    }


    @Override
    public Set<String> getAllCodiExpedient() {
        List<String> codiExpedients = contracteRepository.findAllCodiExpedients();
        return new HashSet<>(codiExpedients);
    }

    @Override
    @Transactional
    public void saveContracte(ContracteDTO contracteDTO) throws DataAccessException {
        contracteRepository.save(contracteMapper.toEntity(contracteDTO));
    }

    @Override
    public ContracteEntity findByCodi(String codiExpedient) {
        return contracteRepository.findByCodi(codiExpedient);
    }

    @Override
    public ContracteDTO get(Long idContracte, Long idUsuari) {
        ContracteDTO contracteDTO = contracteMapper.toDTO(contracteRepository.getReferenceById(idContracte));
        contracteDTO.setPreferit(favoritsService.isFavorit(idUsuari, contracteDTO.getIdContracte()));
        return contracteDTO;
    }

    @Override
    @Transactional
    public void deleteContracte(final Long idContracte) {
        ContracteEntity contracteEntity = contracteRepository.getReferenceById(idContracte);
        List<Long> licitadors = ofertaDeleteService.getLicitadorsContracte(idContracte);
        ofertaDeleteService.deleteOfertesByContracte(idContracte);
        favoritsDeleteService.deleteAllByIdContracte(idContracte);
        contracteRepository.delete(contracteEntity);
        for(Long idUsuari : licitadors) {
            missatgeService.enviarNotificacioSistema(idUsuari, 2, idContracte);
        }
    }

    private Sort getOrdenacio(String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort != null && !sort.isBlank()) {
            for (String field : sort.split(",")) {
                char direction = field.charAt(0);
                if (Character.toString(direction).equals("-")) {
                    String value = field.substring(1);
                    orders.add(new Sort.Order(Sort.Direction.DESC, getRelacioOrdenacio(value)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.ASC, getRelacioOrdenacio(field)));
                }
            }
        }
        return Sort.by(orders);
    }


    private String getRelacioOrdenacio(String value) {
        Map<String, String> relacioOrdenacio = new HashMap<>();
        relacioOrdenacio.put("id", "id");
        relacioOrdenacio.put("codiExpedient", "codiExpedient");
        relacioOrdenacio.put("tipusContracte", "tipusContracte");
        relacioOrdenacio.put("ambit", "ambit.nom");
        relacioOrdenacio.put("procediment", "procediment");
        relacioOrdenacio.put("llocExecucio", "llocExecucio");
        relacioOrdenacio.put("valorEstimatContracte", "valorEstimatContracte");

        return relacioOrdenacio.getOrDefault(value, "id");
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void comprovacioFinalitzacio(){
        LocalDateTime startOfDay = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().minusDays(1).atTime(23, 59, 59);
        Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        List<ContracteEntity> contracteEntities = contracteRepository.findContractsByTerminiBetween(startDate, endDate);
        notifyLicitadors(contracteEntities, 3);
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void comprovacioTerminis(){
        LocalDateTime startOfDay = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atTime(23, 59, 59);
        Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        List<ContracteEntity> contracteEntities = contracteRepository.findContractsByTerminiBetween(startDate, endDate);
        notifyLicitadors(contracteEntities, 4);
    }

    private void notifyLicitadors(List<ContracteEntity> contracteEntities, Integer tipus) {
        for (ContracteEntity contracteEntity : contracteEntities) {
            List<Long> licitadors = ofertaDeleteService.getLicitadorsContracte(contracteEntity.getIdContracte());
            for (Long idUsuari : licitadors) {
                missatgeService.enviarNotificacioSistema(idUsuari, tipus, contracteEntity.getIdContracte());
            }
        }
    }


}
