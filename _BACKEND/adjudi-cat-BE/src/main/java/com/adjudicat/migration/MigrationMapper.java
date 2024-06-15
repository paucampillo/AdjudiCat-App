package com.adjudicat.migration;

import com.adjudicat.controller.dto.ContracteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MigrationMapper {

    @Mapping(source = "codi_expedient", target = "codiExpedient")
    @Mapping(source = "tipus_contracte", target = "tipusContracte")
    @Mapping(source = "subtipus_contracte", target = "subtipusContracte")
    @Mapping(source = "procediment", target = "procediment")
    @Mapping(source = "objecte_contracte", target = "objecteContracte")
    @Mapping(source = "pressupost_licitacio", target = "pressupostLicitacio", qualifiedByName = "stringToDouble")
    @Mapping(source = "valor_estimat_contracte", target = "valorEstimatContracte", qualifiedByName = "stringToDouble")
    @Mapping(source = "lloc_execucio", target = "llocExecucio")
    @Mapping(source = "duracio_contracte", target = "duracioContracte")
    @Mapping(source = "termini_presentacio_ofertes", target = "terminiPresentacioOfertes")
    @Mapping(source = "data_publicacio_anunci", target = "dataPublicacioAnunci")
    @Mapping(source = "ofertes_rebudes", target = "ofertesRebudes", qualifiedByName = "stringToInteger")
    @Mapping(source = "resultat", target = "resultat")
    @Mapping(source = "enllac_publicacio", target = "enllacPublicacio")
    @Mapping(source = "data_adjudicacio_contracte", target = "dataAdjudicacioContracte")
    ContracteDTO buildContracteDTOFromMigrationDTO(MigrationDTO migrationDTO);

    @Named("stringToDouble")
    default Double stringToDouble(String value) {
        return value != null ? Double.parseDouble(value) : null;
    }

    @Named("stringToInteger")
    default Integer stringToInteger(String value) {
        return value != null ? Integer.parseInt(value) : null;
    }
}