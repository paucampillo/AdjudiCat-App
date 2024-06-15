package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.repository.entity.ContracteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {AmbitMapper.class, LotMapper.class, UsuariMapper.class})
public interface ContracteMapper {

    ContracteDTO toDTO(ContracteEntity entity);

    List<ContracteDTO> toDTOList(List<ContracteEntity> entityList);

    ContracteEntity toEntity(ContracteDTO dto);

}
