package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FavoritsDTO;
import com.adjudicat.controller.dto.ReturnFavoritsDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.exception.RepositoryException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface FavoritsService {


    void addFavorit(ReturnFavoritsDTO fave) throws RuntimeException;

    void removeFavorit(ReturnFavoritsDTO fave) throws RuntimeException;

    Page<ContracteDTO> listFavorits(Integer page, Integer cpp, Long idUser);

    Boolean isFavorit(Long idUser, Long idContracte);

    void deleteFavorit(Long idUsuari, Long idContracte);

}
