package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ChatbotAddDTO;
import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.MissatgeDTO;
import com.adjudicat.controller.dto.ReturnFavoritsDTO;
import org.springframework.data.domain.Page;

public interface ChatbotService {

    MissatgeDTO operaMensaje(Long idEmissor, String mensaje);

    String operaMensajeFarm2Table(String mensaje);

    String addNewArticle(ChatbotAddDTO reqBody);
}
