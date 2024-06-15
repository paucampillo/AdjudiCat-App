package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.RolsDTO;
import com.adjudicat.repository.entity.RolsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolsMapper {

    RolsDTO toDTO(RolsEntity entity);

    RolsEntity toEntity(RolsDTO dto);

}
