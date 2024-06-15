package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.OrganDTO;
import com.adjudicat.repository.entity.OrganEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DepartamentMapper.class})
public interface OrganMapper {

    OrganDTO toDTO(OrganEntity entity);

    OrganEntity toEntity(OrganDTO dto);

}
