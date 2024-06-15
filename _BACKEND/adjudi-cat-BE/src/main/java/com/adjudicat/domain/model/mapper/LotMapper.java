package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.LotDTO;
import com.adjudicat.repository.entity.LotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LotMapper {

    LotDTO toDTO(LotEntity entity);

    LotEntity toEntity(LotDTO dto);

}
