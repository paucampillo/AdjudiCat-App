package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.ChatbotAPI;
import com.adjudicat.controller.api.FavoritsAPI;
import com.adjudicat.controller.dto.*;
import com.adjudicat.domain.service.ChatbotService;
import com.adjudicat.domain.service.FavoritsService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/chatbot")
public class ChatbotController extends BaseController implements ChatbotAPI {

    private final ChatbotService chatbotService;

    @Override
    @PostMapping(value = "/consulta")
    public ResponseEntity<MissatgeDTO> requestHelp(
            @ApiParam(value = "ID del usuario que realiza la consulta", example = "1") @RequestParam(value = "idEmissor", required = true) Long idEmissor,
            @ApiParam(value = "Mensaje de la consulta. Límite de 1000 carácteres.", example = "¿Cómo puedo licitar en una oferta activa?") @RequestParam(value = "mensaje", required = true) String mensaje
    ){
        return ResponseEntity.ok(chatbotService.operaMensaje(idEmissor, mensaje));
    }

    @Override
    @PostMapping(value = "/consultaExterna")
    public ResponseEntity<String> requestHelpFarm2Table(
            @ApiParam(value = "Keyword que se quiere buscar.", example = "licitar") @RequestParam(value = "mensaje", required = true) String mensajeRequest
            ){
        String ret = chatbotService.operaMensajeFarm2Table(mensajeRequest);
                if(ret == "error"){
                    return ResponseEntity.badRequest().body("Error: no se ha encontrado ninguna consulta con esa palabra clave.");
                }
        return ResponseEntity.ok(ret);
    }

    @Override
    @PostMapping(value = "/nuevaPalabra")
    public ResponseEntity<String> addKeywordToDB(
            @RequestBody final ChatbotAddDTO reqBody
    ){

        return ResponseEntity.ok(chatbotService.addNewArticle(reqBody));
    }

}
