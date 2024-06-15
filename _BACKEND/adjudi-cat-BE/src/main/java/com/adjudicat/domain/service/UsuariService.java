package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ReturnLoginDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import jakarta.servlet.ServletException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.adjudicat.controller.dto.CanviContrasenyaDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.exception.AdjudicatBaseException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.exception.InvalidPasswordException;
import jakarta.persistence.EntityNotFoundException;

public interface UsuariService {
    ReturnLoginDTO tryLoginLocal(String username, String password);

    ReturnLoginDTO processOAuthPostLogin(String email) throws ServletException;

    ReturnLoginDTO registraUser(UsuariDTO newUser, Boolean google) throws ServletException;

    UsuariDTO get(Long idUsuari) throws EntityNotFoundException;

    UsuariDTO edit(UsuariDTO usuari) throws EntityNotFoundException;

    UsuariDTO canviContrasenya(CanviContrasenyaDTO canviContrasenya) throws EntityNotFoundException, InvalidPasswordException;

    void saveUsuaris(List<UsuariDTO> UsuariDTOs) throws AdjudicatBaseException;

    Set<String> getAllIdAdjudicatari();

    void bloqueaCuenta(Long idUsuariBloquejat) throws EntityNotFoundException, InvalidPasswordException;

    void desbloquearCuenta(Long idUsuariBloquejat)throws EntityNotFoundException, InvalidPasswordException;

    Page<UsuariDTO> devuelveAllUsers(Integer page, Integer rpp);


}
