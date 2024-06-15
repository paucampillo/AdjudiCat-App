package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.IdiomesDTO;
import com.adjudicat.repository.entity.IdiomesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IdiomesMapper {

    IdiomesDTO toDTO(IdiomesEntity entity);

    IdiomesEntity toEntity(IdiomesDTO dto);

}
