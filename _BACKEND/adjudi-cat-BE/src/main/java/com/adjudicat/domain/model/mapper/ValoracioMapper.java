package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.ValoracioCustomDTO;
import com.adjudicat.controller.dto.ValoracioDTO;
import com.adjudicat.repository.entity.ValoracioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {UsuariMapper.class})
public interface ValoracioMapper {

    ValoracioDTO toDTO(ValoracioEntity entity);

    ValoracioEntity toEntity(ValoracioDTO dto);

    ValoracioEntity toEntityCustom(ValoracioCustomDTO valoracioDTO);
}
