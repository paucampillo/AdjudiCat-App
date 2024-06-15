package com.adjudicat.domain.model.mapper;


import com.adjudicat.controller.dto.FavoritsDTO;
import com.adjudicat.repository.entity.FavoritsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {UsuariMapper.class, ContracteMapper.class})
public interface FavoritsMapper {

    FavoritsDTO toDTO(FavoritsEntity entity);

    FavoritsEntity toEntity(FavoritsDTO dto);

}
