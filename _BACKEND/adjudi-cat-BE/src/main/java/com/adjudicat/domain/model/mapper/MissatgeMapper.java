package com.adjudicat.domain.model.mapper;

import com.adjudicat.controller.dto.MissatgeCustomDTO;
import com.adjudicat.controller.dto.MissatgeDTO;
import com.adjudicat.repository.entity.MissatgeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UsuariMapper.class})
public interface MissatgeMapper {

    MissatgeDTO toDTO(MissatgeEntity entity);

    MissatgeEntity toEntity(MissatgeDTO dto);

    @Mapping(target = "idemissor", source = "emissor.idUsuari")
    @Mapping(target = "idreceptor", source = "receptor.idUsuari")
    MissatgeCustomDTO toCustomDTO(MissatgeDTO missatgeDTO);

    default Page<MissatgeCustomDTO> toPageCustomDTO(Page<MissatgeDTO> missatgeDTOPage) {
        return missatgeDTOPage.map(this::toCustomDTO);
    }
}
