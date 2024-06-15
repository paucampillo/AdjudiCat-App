package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.UsuariLogInFallidoDTO;
import com.adjudicat.repository.entity.UsuariLogInFallidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UsuariMapper.class})
public interface UsuariLogInFallidoMapper {

    UsuariLogInFallidoDTO toDTO(UsuariLogInFallidoEntity entity);

    UsuariLogInFallidoEntity toEntity(UsuariLogInFallidoDTO dto);

}
