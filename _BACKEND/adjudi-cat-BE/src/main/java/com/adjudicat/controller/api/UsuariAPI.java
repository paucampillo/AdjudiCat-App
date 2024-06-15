package com.adjudicat.controller.api;

import com.adjudicat.controller.dto.*;

import com.adjudicat.controller.dto.CanviContrasenyaDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.ServletException;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@Api(tags = {"Usuari API"})
public interface UsuariAPI {

    @ApiOperation(value = "Login Local")
    ResponseEntity<ReturnLoginDTO> tryLoginLocal(String email, String hashedPassword);

    @ApiOperation(value = "Login OAuth google")
    ResponseEntity<ReturnLoginDTO> tryLoginGoogle(String email);

    @ApiOperation(value = "Register")
    ResponseEntity<ReturnLoginDTO> registerUser(UsuariDTO newUser, Boolean google);

    @ApiOperation(value = "Get usuari")
    ResponseEntity<UsuariDTO> get(Long idUsuari);

    @ApiOperation(value = "Edit usuari")
    ResponseEntity<UsuariDTO> edit(UsuariDTO usuari);

    @ApiOperation(value = "Canvi de contrasenya")
    ResponseEntity<UsuariDTO> canviContrasenya(CanviContrasenyaDTO canviContrasenya);

    @ApiOperation(value = "Bloquear cuenta")
    ResponseEntity<Object> bloquearCuenta(Long idUsuari);
    @ApiOperation(value = "Desbloquear cuenta")
    ResponseEntity<Object> desbloquearCuenta(Long idUsuari);

    @ApiOperation(value = "Todos los no admins")
    ResponseEntity<Page<UsuariDTO>> buscarUsers(Integer page, Integer rpp);

}
