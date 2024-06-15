package com.adjudicat.domain.model.mapper;


import com.adjudicat.controller.dto.ChatbotDTO;
import com.adjudicat.repository.entity.ChatbotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatbotMapper {

    ChatbotDTO toDTO(ChatbotEntity entity);

    ChatbotEntity toEntity(ChatbotDTO dto);

}
