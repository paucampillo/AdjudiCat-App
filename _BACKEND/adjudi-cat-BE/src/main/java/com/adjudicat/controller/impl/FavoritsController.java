package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.FavoritsAPI;
import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FavoritsDTO;
import com.adjudicat.controller.dto.ReturnFavoritsDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.domain.service.FavoritsService;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/favorits")
public class FavoritsController extends BaseController implements FavoritsAPI {

    private final FavoritsService favoritsService;


    @Override
    @PostMapping("/add")
    public ResponseEntity<Object> addFavorit(
            @ApiParam(value = "DTO favorits") @RequestBody ReturnFavoritsDTO fave){

        try {
            favoritsService.addFavorit(fave);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    @PostMapping("/delete")
    public ResponseEntity<Object> removeFavorit(
            @ApiParam(value = "DTO favorits") @RequestBody ReturnFavoritsDTO fave) {

        try {
            favoritsService.removeFavorit(fave);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    @PostMapping("/list")
    public ResponseEntity<Page<ContracteDTO>> listarFavorits(
            @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "cpp", required = false) Integer cpp,
            @ApiParam(value = "DTO de l'usuari que esborra un favorit") @RequestParam(value = "id_user") Long idUser
    ) {
        return ResponseEntity.ok(favoritsService.listFavorits(page, cpp, idUser));
    }

}
