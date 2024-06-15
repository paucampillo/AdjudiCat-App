package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.AmbitDTO;
import com.adjudicat.controller.dto.ChatbotAddDTO;
import com.adjudicat.controller.dto.MissatgeCustomDTO;
import com.adjudicat.controller.dto.MissatgeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Chatbot API"})
public interface ChatbotAPI {

    ResponseEntity<MissatgeDTO> requestHelp(Long idEmissor, String mensajeRequest);

    ResponseEntity<String> requestHelpFarm2Table(String mensajeRequest);

    ResponseEntity<String> addKeywordToDB(ChatbotAddDTO reqBody);
}
