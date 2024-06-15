package com.adjudicat.migration;

import com.adjudicat.controller.dto.*;
import com.adjudicat.domain.service.*;
import com.adjudicat.exception.AdjudicatBaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MigrationServiceImpl implements MigrationService{
    private static final String URL = "https://analisi.transparenciacatalunya.cat/resource/a23c-d6vp.json";

    private final ContracteService contracteService;
    private final AmbitService ambitService;
    private final DepartamentService departamentService;
    private final OrganService organService;
    private final LotService lotService;
    private final UsuariService usuariService;

    @Override
    @Scheduled(cron = "0 0 7 * * 1")
    public void fetchFromApi() throws AdjudicatBaseException {
        RestTemplate restTemplate = new RestTemplate();
        MigrationDTO[] dataArray = restTemplate.getForObject(URL, MigrationDTO[].class);
        List<MigrationDTO> migrationDTOList = Arrays.asList(dataArray);

        List<ContracteDTO> contracteDTOs = new ArrayList<>();
        Set<String> contracteKeys = contracteService.getAllCodiExpedient();
        List<AmbitDTO> ambitDTOs = new ArrayList<>();
        Set<String> ambitKeys = ambitService.getAllCodiAmbit();
        List<DepartamentDTO> departamentDTOs = new ArrayList<>();
        Set<Long> departamentKeys = departamentService.getAllCodiDepartamentEns();
        List<OrganDTO> organDTOs = new ArrayList<>();
        Set<Long> organKeys = organService.getAllCodiOrgan();
        List<LotDTO> lotDTOs = new ArrayList<>();
        Set<String> lotKeys = lotService.getAllNumeroLot();

        UsuariDTO usuariGene = usuariService.get(14L);

        for (MigrationDTO migrationDTO : migrationDTOList) {
            if (migrationDTO.getNumero_lot() != null && lotKeys.add(migrationDTO.getNumero_lot())) {
                LotDTO lotDTO = buildLotDTOFromMigrationDTO(migrationDTO);
                lotDTOs.add(lotDTO);
            }
            if (migrationDTO.getCodi_ambit() != null && ambitKeys.add(migrationDTO.getCodi_ambit())) {
                AmbitDTO ambitDTO = buildAmbitDTOFromMigrationDTO(migrationDTO);
                ambitDTOs.add(ambitDTO);
            }
            if (migrationDTO.getCodi_expedient() != null && contracteKeys.add(migrationDTO.getCodi_expedient())) {
                LotDTO lotDTO = null;
                if (migrationDTO.getNumero_lot() != null) {
                    lotDTO = buildLotDTOFromMigrationDTO(migrationDTO);
                }
                AmbitDTO ambitDTO = null;
                if (migrationDTO.getCodi_ambit() != null) {
                    ambitDTO = buildAmbitDTOFromMigrationDTO(migrationDTO);
                }
                ContracteDTO contracteDTO = buildContracteDTOFromMigrationDTO(migrationDTO, lotDTO, ambitDTO, usuariGene);
                contracteDTOs.add(contracteDTO);
            }
            if (migrationDTO.getCodi_departament_ens() != null && departamentKeys.add(Long.valueOf(migrationDTO.getCodi_departament_ens()))) {
                DepartamentDTO departamentDTO = buildDepartamentDTOFromMigrationDTO(migrationDTO);
                departamentDTOs.add(departamentDTO);
            }
            if (migrationDTO.getCodi_organ() != null && organKeys.add(Long.valueOf(migrationDTO.getCodi_organ()))) {
                DepartamentDTO departamentDTO = null;
                if (migrationDTO.getCodi_departament_ens() != null) {
                    departamentDTO = buildDepartamentDTOFromMigrationDTO(migrationDTO);
                }
                OrganDTO organDTO = buildOrganDTOFromMigrationDTO(migrationDTO, departamentDTO);
                organDTOs.add(organDTO);
            }
        }
        ambitService.saveAmbits(ambitDTOs);
        lotService.saveLots(lotDTOs);
        departamentService.saveDepartaments(departamentDTOs);
        organService.saveOrgans(organDTOs);
        contracteService.saveContractes(contracteDTOs);
    }

    private OrganDTO buildOrganDTOFromMigrationDTO(MigrationDTO migrationDTO, DepartamentDTO departamentDTO) {
        OrganDTO organDTO = new OrganDTO();
        organDTO.setCodi(migrationDTO.getCodi_organ() != null ? Long.valueOf(migrationDTO.getCodi_organ()) : null);
        organDTO.setNom(migrationDTO.getNom_organ());
        organDTO.setDepartament(departamentDTO);
        return organDTO;
    }

    private LotDTO buildLotDTOFromMigrationDTO(MigrationDTO migrationDTO) {
        LotDTO lotDTO = new LotDTO();
        lotDTO.setNumero(migrationDTO.getNumero_lot());
        lotDTO.setDescripcio(migrationDTO.getDescripcio_lot());
        return lotDTO;
    }

    private ContracteDTO buildContracteDTOFromMigrationDTO(MigrationDTO migrationDTO, LotDTO lotDTO, AmbitDTO ambitDTO, UsuariDTO usuariGene) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dataPublicacioAnunci = null;
        Date terminiPresentacioOfertes = null;
        try {
            dataPublicacioAnunci = sdf.parse("2023-01-01");
            terminiPresentacioOfertes = sdf.parse("2025-01-01");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContracteDTO contracteDTO = new ContracteDTO();
        contracteDTO.setCodiExpedient(migrationDTO.getCodi_expedient());
        contracteDTO.setTipusContracte(migrationDTO.getTipus_contracte());
        contracteDTO.setSubtipusContracte(migrationDTO.getSubtipus_contracte());
        contracteDTO.setProcediment(migrationDTO.getProcediment());
        contracteDTO.setObjecteContracte(migrationDTO.getObjecte_contracte());
        contracteDTO.setPressupostLicitacio(migrationDTO.getPressupost_licitacio() != null ? Double.valueOf(migrationDTO.getPressupost_licitacio()) : null);
        contracteDTO.setValorEstimatContracte(migrationDTO.getValor_estimat_contracte() != null ? Double.valueOf(migrationDTO.getValor_estimat_contracte()) : null);
        contracteDTO.setLlocExecucio(migrationDTO.getLloc_execucio());
        contracteDTO.setDuracioContracte(migrationDTO.getDuracio_contracte());
        contracteDTO.setDataPublicacioAnunci(migrationDTO.getData_publicacio_anunci() != null ? migrationDTO.getData_publicacio_anunci() : dataPublicacioAnunci);
        contracteDTO.setTerminiPresentacioOfertes(migrationDTO.getTermini_presentacio_ofertes() != null ? migrationDTO.getTermini_presentacio_ofertes() : terminiPresentacioOfertes);
        contracteDTO.setOfertesRebudes(0);
        contracteDTO.setResultat(migrationDTO.getResultat());
        contracteDTO.setEnllacPublicacio(migrationDTO.getEnllac_publicacio());
        contracteDTO.setDataAdjudicacioContracte(migrationDTO.getData_adjudicacio_contracte());
        contracteDTO.setLot(lotDTO);
        contracteDTO.setAmbit(ambitDTO);
        contracteDTO.setUsuariCreacio(usuariGene);
        return contracteDTO;
    }

    private DepartamentDTO buildDepartamentDTOFromMigrationDTO(MigrationDTO migrationDTO) {
        DepartamentDTO departamentDTO = new DepartamentDTO();
        departamentDTO.setCodi(migrationDTO.getCodi_departament_ens() != null ? Long.valueOf(migrationDTO.getCodi_departament_ens()) : null);
        departamentDTO.setNom(migrationDTO.getNom_ambit());
        return departamentDTO;
    }

    private AmbitDTO buildAmbitDTOFromMigrationDTO(MigrationDTO migrationDTO) {
        AmbitDTO ambitDTO = new AmbitDTO();
        ambitDTO.setCodi(migrationDTO.getCodi_ambit() != null ? Long.valueOf(migrationDTO.getCodi_ambit()) : null);
        ambitDTO.setNom(migrationDTO.getNom_ambit());
        return ambitDTO;
    }

}