package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.ReturnFavoritsDTO;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


@Api(tags = {"Favorits API"})
public interface FavoritsAPI {


    ResponseEntity<Object> addFavorit(ReturnFavoritsDTO fave);

    ResponseEntity<Page<ContracteDTO>> listarFavorits(Integer page, Integer cpp, Long user);

    ResponseEntity<Object> removeFavorit(ReturnFavoritsDTO fave);



}
