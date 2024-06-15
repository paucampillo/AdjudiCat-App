package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.AmbitDTO;
import com.adjudicat.repository.entity.AmbitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AmbitMapper {

    AmbitDTO toDTO(AmbitEntity entity);

    AmbitEntity toEntity(AmbitDTO dto);

}
