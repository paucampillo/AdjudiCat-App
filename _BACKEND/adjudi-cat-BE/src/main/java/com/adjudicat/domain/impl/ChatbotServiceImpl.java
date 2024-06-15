package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.ChatbotAddDTO;
import com.adjudicat.controller.dto.ChatbotDTO;
import com.adjudicat.controller.dto.MissatgeDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.domain.model.mapper.ChatbotMapper;
import com.adjudicat.domain.model.mapper.RelacionArticulosMapper;
import com.adjudicat.domain.model.mapper.UsuariMapper;
import com.adjudicat.domain.service.ChatbotService;
import com.adjudicat.domain.service.MissatgeService;
import com.adjudicat.repository.entity.ChatbotEntity;
import com.adjudicat.repository.entity.RelacionArticulosEntity;
import com.adjudicat.repository.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final Long ID_ADJUDIBOT = 19L;
    private final RelacionArticulosRepository raRepo;
    private final ChatbotRepository chatbotRepo;
    private final UsuariRepository userRepo;
    private final UsuariMapper userMapper;
    private final MissatgeService msgService;
    private final ChatbotMapper cbMapper;
    private final RelacionArticulosMapper raMapper;


    @Override
    public MissatgeDTO operaMensaje(Long idEmissor, String mensaje) {
        msgService.enviarMissatge(idEmissor, ID_ADJUDIBOT, mensaje);
        mensaje = mensaje.replaceAll("[^a-zA-Z0-9çñ_ ]", "");
        String[] splitString = mensaje.split(" ");
        UsuariDTO AjudibotDTO = userMapper.toDTO(userRepo.findById(ID_ADJUDIBOT).orElse(null));
        UsuariDTO emissorDTO = userMapper.toDTO(userRepo.findById(idEmissor).orElse(null));

        if (splitString.length >= 25) {
            String error = "Tu consulta es demasiado larga; prueba a usar menos de 25 palabras.";
            msgService.enviarMissatge(ID_ADJUDIBOT, idEmissor, error);
            MissatgeDTO ret = new MissatgeDTO();
            ret.setMissatge(error);

            ret.setEmissor(AjudibotDTO);
            ret.setReceptor(emissorDTO);
            ret.setDataHoraEnvio(LocalDateTime.now());
            return ret;


        }

        HashMap<Long, Integer> frecuencias = new HashMap<>();
        for (int i = 0; i < splitString.length; ++i) {
            String palabra = splitString[i];
            RelacionArticulosEntity auxRA = raRepo.findById(palabra).orElse(null);


            if (auxRA != null) {
                Long idPalabra = auxRA.getArticle().getIdArticulo();
                if (!frecuencias.containsKey(idPalabra)) {
                    frecuencias.put(idPalabra, 1);
                } else {
                    frecuencias.put(idPalabra, frecuencias.get(idPalabra) + 1);
                }
            }
        }
        if (frecuencias.isEmpty()) {
            String error = "No he encontrado ningún artículo relativo a la consulta. Por favor, inténtalo con otras palabras.";
            msgService.enviarMissatge(ID_ADJUDIBOT, idEmissor, error);
            MissatgeDTO ret = new MissatgeDTO();
            ret.setMissatge(error);
            ret.setEmissor(AjudibotDTO);
            ret.setReceptor(emissorDTO);
            ret.setDataHoraEnvio(LocalDateTime.now());
            return ret;
        }

        Long curMaxLong = 0L;
        Integer curMaxInt = 0;
        for (HashMap.Entry<Long, Integer> entry : frecuencias.entrySet()) {
            if (entry.getValue() > curMaxInt) {
                curMaxLong = entry.getKey();
                curMaxInt = entry.getValue();
            }
        }
        ChatbotEntity maxFreq = chatbotRepo.findById(curMaxLong).orElse(null);
        String mensajeAEnviar = "";
        if (maxFreq != null) mensajeAEnviar = maxFreq.getTexto();
        msgService.enviarMissatge(ID_ADJUDIBOT, idEmissor, mensajeAEnviar);


        MissatgeDTO ret = new MissatgeDTO();
        ret.setMissatge(mensajeAEnviar);
        ret.setEmissor(AjudibotDTO);
        ret.setReceptor(emissorDTO);
        ret.setDataHoraEnvio(LocalDateTime.now());
        return ret;
    }

    @Override
    public String operaMensajeFarm2Table(String mensaje){
        String ret;
        mensaje = mensaje.replaceAll("[^a-zA-Z0-9çñ_ ]", "");
        String[] splitString = mensaje.split(" ");
        if (splitString.length >= 25) {
            ret = "Error: +25 Palabras";
            return ret;
        }
        HashMap<Long, Integer> frecuencias = new HashMap<>();
        for (int i = 0; i < splitString.length; ++i) {
            String palabra = splitString[i];
            RelacionArticulosEntity auxRA = raRepo.findById(palabra).orElse(null);
            if (auxRA != null) {
                Long idPalabra = auxRA.getArticle().getIdArticulo();
                if (!frecuencias.containsKey(idPalabra)) {
                    frecuencias.put(idPalabra, 1);
                } else {
                    frecuencias.put(idPalabra, frecuencias.get(idPalabra) + 1);
                }
            }
        }
        if(frecuencias.isEmpty()) {
            ret = "Error: Not Found";
            return ret;
        }
        Long curMaxLong = 0L;
        Integer curMaxInt = 0;
        for (HashMap.Entry<Long, Integer> entry : frecuencias.entrySet()) {
            if (entry.getValue() > curMaxInt) {
                curMaxLong = entry.getKey();
                curMaxInt = entry.getValue();
            }
        }
        ChatbotEntity maxFreq = chatbotRepo.findById(curMaxLong).orElse(null);
        if(maxFreq != null){
            return maxFreq.getTexto();
        }
            return "Error: Unknown error.";

    }

    @Override
    public String addNewArticle(ChatbotAddDTO reqBody) {
        String result = "";
        String mensaje = reqBody.getFrase();
        if(mensaje.length() >= 1024){
            result = "Error: el mensaje debe tener menos de 1024 carácteres.";
            return result;
        }

        List<String> kws = reqBody.getKeywords();
        for(String kw : kws){
            RelacionArticulosEntity check = raRepo.findById(kw).orElse(null);
            if(check != null){
                result = "Error: la siguiente keyword ya está en uso -> " + kw;
                return result;
            }
        }

        ChatbotDTO auxCbDto = new ChatbotDTO();
        auxCbDto.setTexto(mensaje);
        auxCbDto.setIdArticulo(null);
        ChatbotEntity auxCbE = cbMapper.toEntity(auxCbDto);
        chatbotRepo.save(auxCbE);
        auxCbE = chatbotRepo.findByTexto(mensaje).orElse(null);
        Long id = auxCbE.getIdArticulo();

        for(String kw : kws){
            RelacionArticulosEntity raEntAux = new RelacionArticulosEntity();
            raEntAux.setPalabra(kw);
            raEntAux.setArticle(auxCbE);
            raRepo.save(raEntAux);
        }
        result = "success";
        return result;

    }
}
