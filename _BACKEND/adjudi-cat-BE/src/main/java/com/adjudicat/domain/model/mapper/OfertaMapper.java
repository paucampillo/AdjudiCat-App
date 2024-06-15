package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.OfertaDTO;
import com.adjudicat.repository.entity.OfertaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {UsuariMapper.class, ContracteMapper.class})
public interface OfertaMapper {

    OfertaDTO toDTO(OfertaEntity entity);

    List<OfertaDTO> toDTOList(List<OfertaEntity> entity);

    OfertaEntity toEntity(OfertaDTO dto);

}
