package com.adjudicat.domain.model.mapper;


import com.adjudicat.controller.dto.FavoritsDTO;
import com.adjudicat.controller.dto.RelacionArticulosDTO;
import com.adjudicat.repository.entity.FavoritsEntity;
import com.adjudicat.repository.entity.RelacionArticulosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {ChatbotMapper.class})
public interface RelacionArticulosMapper {

    RelacionArticulosDTO toDTO(RelacionArticulosEntity entity);

    RelacionArticulosEntity toEntity(RelacionArticulosDTO dto);

}
