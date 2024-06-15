package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.ConversacionDTO;
import com.adjudicat.controller.dto.MissatgeCustomDTO;
import com.adjudicat.exception.RepositoryException;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = {"Missatge API"})
public interface MissatgeAPI {

    ResponseEntity<Object> enviarMissatge(Long idEmisor, Long idReceptor, String mensaje);

    ResponseEntity<Object> deleteMissatge(Long idMissatge);

    ResponseEntity<Page<MissatgeCustomDTO>> findPaginated(Integer page, Integer rpp, Long idEmissor, Long idReceptor);

    ResponseEntity<List<ConversacionDTO>> findConverses(Long idUsuari);

    ResponseEntity<Object> deleteConverse(Long idEmissor, Long idReceptor);

}