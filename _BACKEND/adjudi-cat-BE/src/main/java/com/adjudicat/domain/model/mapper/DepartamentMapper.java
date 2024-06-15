package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.DepartamentDTO;
import com.adjudicat.repository.entity.DepartamentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartamentMapper {

    DepartamentDTO toDTO(DepartamentEntity entity);

    DepartamentEntity toEntity(DepartamentDTO dto);

}
