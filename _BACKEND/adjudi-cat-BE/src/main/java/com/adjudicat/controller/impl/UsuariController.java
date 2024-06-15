package com.adjudicat.controller.impl;

import com.adjudicat.controller.api.UsuariAPI;
import com.adjudicat.controller.dto.ReturnLoginDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.controller.dto.CanviContrasenyaDTO;
import com.adjudicat.domain.service.UsuariService;
import io.swagger.annotations.ApiParam;
import jakarta.servlet.ServletException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.adjudicat.exception.InvalidPasswordException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/adj/usuari")
public class UsuariController extends BaseController implements UsuariAPI {

    private final UsuariService usuariService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<ReturnLoginDTO> tryLoginLocal(@RequestParam(value = "email") final String email,
                                                        @RequestParam(value = "password") final String password) {
        ReturnLoginDTO ret = usuariService.tryLoginLocal(email, password);
        return ResponseEntity.ok(ret);
    }

    @Override
    @PostMapping("/loginGoogle")
    public ResponseEntity<ReturnLoginDTO> tryLoginGoogle(@RequestParam(value = "email") final String email) {

        try {
            ReturnLoginDTO ret = usuariService.processOAuthPostLogin(email);
            return ResponseEntity.ok(ret);
        } catch (ServletException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    @PostMapping("/register")
    public ResponseEntity<ReturnLoginDTO> registerUser(@RequestBody final UsuariDTO newUser, @RequestParam(value = "google") final Boolean google) {

        try {
            ReturnLoginDTO ret = usuariService.registraUser(newUser, google);
            if (!ret.isLoginCorrect()) {
                return ResponseEntity.badRequest().body(ret);
            }
            return ResponseEntity.ok(ret);
        } catch (ServletException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @GetMapping("/{idUsuari}")
    public ResponseEntity<UsuariDTO> get(@PathVariable final Long idUsuari) {
        try {
            return ResponseEntity.ok(usuariService.get(idUsuari));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping("/edit")
    public ResponseEntity<UsuariDTO> edit(@RequestBody final UsuariDTO usuari) {
        try {
            return ResponseEntity.ok(usuariService.edit(usuari));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PutMapping("/password")
    public ResponseEntity<UsuariDTO> canviContrasenya(@RequestBody final CanviContrasenyaDTO canviContrasenya) {
        try {
            return ResponseEntity.ok(usuariService.canviContrasenya(canviContrasenya));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidPasswordException i) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PutMapping("/bloquear")
    public ResponseEntity<Object> bloquearCuenta(
            @RequestParam (value = "idUser") final Long idUsuariBloquejat
    ){
        try
    {
        usuariService.bloqueaCuenta(idUsuariBloquejat);
        return new ResponseEntity<>(HttpStatus.OK);
    }
        catch(EntityNotFoundException e)
    {
        return ResponseEntity.notFound().build();
    }

        catch(InvalidPasswordException i)

    {
        return ResponseEntity.badRequest().build();
    }
}

    @Override
    @PutMapping("/desbloquear")
    public ResponseEntity<Object> desbloquearCuenta(
            @RequestParam (value = "idUser") final Long idUsuariBloquejat
    ){
        try
        {
            usuariService.desbloquearCuenta(idUsuariBloquejat);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }

        catch(InvalidPasswordException i)

        {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @GetMapping("/allUsers")
    public ResponseEntity<Page<UsuariDTO>> buscarUsers(
            @ApiParam(value = "Nombre de pàgina", example = "1") @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
            @ApiParam(value = "Valors per pàgina", example = "10") @RequestParam(defaultValue = "10", value = "rpp", required = false) Integer rpp){

        return ResponseEntity.ok(usuariService.devuelveAllUsers(page, rpp));
    }


}
