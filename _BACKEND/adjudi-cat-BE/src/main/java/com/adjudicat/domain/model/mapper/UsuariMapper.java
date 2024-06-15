package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.repository.entity.UsuariEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {RolsMapper.class, IdiomesMapper.class, OrganMapper.class})
public interface UsuariMapper {

    UsuariDTO toDTO(UsuariEntity entity);

    UsuariEntity toEntity(UsuariDTO dto);

}
