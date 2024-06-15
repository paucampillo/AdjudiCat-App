package com.adjudicat.domain.impl;

import com.adjudicat.config.security.JwtAuthtenticationConfig;
import com.adjudicat.controller.dto.*;
import com.adjudicat.domain.model.mapper.UsuariLogInFallidoMapper;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.domain.model.mapper.UsuariMapper;
import com.adjudicat.domain.service.OrganService;
import com.adjudicat.domain.service.UsuariService;
import com.adjudicat.enums.RolsEnum;
import com.adjudicat.domain.service.ValoracioUsuariService;
import com.adjudicat.repository.entity.UsuariEntity;
import com.adjudicat.exception.InvalidPasswordException;
import com.adjudicat.exception.AdjudicatBaseException;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.exception.constants.RepositoryConstants;
import com.adjudicat.repository.entity.UsuariLogInFallidoEntity;
import com.adjudicat.repository.repository.UsuariLogInFallidoRepository;
import com.adjudicat.repository.repository.UsuariRepository;
import jakarta.servlet.ServletException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuariServiceImpl implements UsuariService {

    private final UsuariRepository usuariRepository;
    private final UsuariMapper usuariMapper;
    private final OrganService organService;
    private final ValoracioUsuariService valoracioUsuariService;
    private final UsuariLogInFallidoRepository failRepo;
    private final UsuariLogInFallidoMapper failMapper;
    private final JwtAuthtenticationConfig jwtAuthtenticationConfig = new JwtAuthtenticationConfig();

    @Override
    public UsuariDTO get(final Long idUsuari) throws EntityNotFoundException {
        UsuariDTO user = usuariMapper.toDTO(usuariRepository.getReferenceById(idUsuari));
        if (Objects.equals(user.getRol().getCodi(), RolsEnum.OP.name())) {
            ValoracioInfoDTO valoracioInfo = valoracioUsuariService.getValoracioInfo(idUsuari);
            user.setValoracio(valoracioInfo.getValoracio());
            user.setNumValoracions(valoracioInfo.getNumValoracions());
        } else if (Objects.equals(user.getRol().getCodi(), RolsEnum.EP.name())) {
            ValoracioInfoDTO valoracioInfo = valoracioUsuariService.getValoracioInfoEmisor(idUsuari);
            user.setValoracio(valoracioInfo.getValoracio());
            user.setNumValoracions(valoracioInfo.getNumValoracions());
        }
        return user;
    }

    @Override
    public UsuariDTO edit(final UsuariDTO usuari) throws EntityNotFoundException {
        if (usuari.getIdUsuari() == null || !usuariRepository.existsById(usuari.getIdUsuari())) {
            throw new EntityNotFoundException();
        }
        return usuariMapper.toDTO(usuariRepository.save(usuariMapper.toEntity(usuari)));
    }

    @Override
    public UsuariDTO canviContrasenya(final CanviContrasenyaDTO canviContrasenya) throws EntityNotFoundException, InvalidPasswordException {
        if (canviContrasenya.getIdUsuari() == null || !usuariRepository.existsById(canviContrasenya.getIdUsuari())) {
            throw new EntityNotFoundException();
        }
        UsuariEntity usuari = usuariRepository.getReferenceById(canviContrasenya.getIdUsuari());
        if (!Objects.equals(usuari.getContrasenya(), canviContrasenya.getLastPassword())) {
            throw new InvalidPasswordException();
        }
        usuari.setContrasenya(canviContrasenya.getNewPassword());
        return usuariMapper.toDTO(usuariRepository.save(usuari));
    }

    @Override
    @Transactional
    public void saveUsuaris(List<UsuariDTO> UsuariDTOs) throws AdjudicatBaseException {
        for (UsuariDTO dto : UsuariDTOs) {
            try {
                if (dto.getOrgan() != null) dto.setOrgan(organService.getOrgan(dto.getOrgan().getCodi()));
                usuariRepository.save(usuariMapper.toEntity(dto));
            } catch (DataAccessException e) {
                throw new RepositoryException(e, RepositoryConstants.ERROR_BBDD);
            }
        }
    }

    @Override
    public Set<String> getAllIdAdjudicatari() {
        List<String> idAdjudicataris = usuariRepository.findAllIdAdjudicatari();
        return new HashSet<>(idAdjudicataris);
    }

    public ReturnLoginDTO tryLoginLocal(String email, String password){
        UsuariEntity user = usuariRepository.findFirstByEmail(email).orElse(null);
        if(user == null){
            return new ReturnLoginDTO(null,null,null,false, "Error: no existeix un usuari amb aquest email.");
        }

        UsuariEntity user2 = usuariRepository.findFirstByEmailAndContrasenya(email, password).orElse(null);
        if(user2 == null){
            Integer intentos = gestionaLoginFallido(user);
            if(intentos == 0)
                return new ReturnLoginDTO(null,null,null,false, "Error: Contrasenya incorrecta. Compte bloquejat per 24 hores.");
            return new ReturnLoginDTO(null,null,null,false, "Error: Contrasenya incorrecta. Queden " + intentos + " intents.");
        }
        if(user.getBloquejat() != null && user.getBloquejat().equals(true)) {
            UsuariLogInFallidoEntity tmp = failRepo.findByUsuariIdUsuari(user.getIdUsuari()).orElse(null);
            if (tmp != null) {
                if (tmp.getDataFinalizacionBan().isBefore(LocalDateTime.now())) {
                    desbloquearCuenta(user.getIdUsuari());
                }
                else return new ReturnLoginDTO(null,null,null,false, "Error: compte bloquejat.");
            }

        }
        String nom = user.getNom();
        Long idUser = user.getIdUsuari();
        String token = jwtAuthtenticationConfig.getJWTToken(nom, idUser);
        String codiRol = user.getRol().getCodi();
        return new ReturnLoginDTO(token, idUser, codiRol, true, null);
    }

    private Integer gestionaLoginFallido(final UsuariEntity usuari) {
        UsuariLogInFallidoDTO logInFallidoDTO = failMapper.toDTO(failRepo.findByUsuariIdUsuari(usuari.getIdUsuari()).orElse(null));
        if(logInFallidoDTO == null){
            logInFallidoDTO = new UsuariLogInFallidoDTO();
            logInFallidoDTO.setUsuari(usuariMapper.toDTO(usuari));
            logInFallidoDTO.setNumeroIntentos(1);
            logInFallidoDTO.setDataFinalizacionBan(LocalDateTime.now().minusDays(1));
            failRepo.save(failMapper.toEntity(logInFallidoDTO));
        }
        else if(logInFallidoDTO.getNumeroIntentos() == 3) {
            UsuariLogInFallidoEntity auxEntity = failMapper.toEntity(logInFallidoDTO);
            auxEntity.setDataFinalizacionBan(LocalDateTime.now().plusDays(1));
            failRepo.save(auxEntity);
            bloqueaCuenta(logInFallidoDTO.getUsuari().getIdUsuari());
            return 0;
        }
        else{
            logInFallidoDTO.setNumeroIntentos(logInFallidoDTO.getNumeroIntentos() + 1);
            failRepo.save(failMapper.toEntity(logInFallidoDTO));
        }
        return 3-logInFallidoDTO.getNumeroIntentos();
    }

    @Override
    public ReturnLoginDTO processOAuthPostLogin(final String email) throws ServletException {
        UsuariEntity existUser = usuariRepository.findByEmail(email).orElse(null);
        if (existUser == null){
            throw new ServletException();
        }
        if(existUser.getBloquejat() != null && existUser.getBloquejat().equals(true)) {
            UsuariLogInFallidoEntity tmp = failRepo.findByUsuariIdUsuari(existUser.getIdUsuari()).orElse(null);
            if (tmp != null) {
                if (tmp.getDataFinalizacionBan().isBefore(LocalDateTime.now())) {
                    desbloquearCuenta(existUser.getIdUsuari());
                }
                else return new ReturnLoginDTO(null,null,null,false, "Error: compte bloquejat.");
            }

        }
        ReturnLoginDTO ret = new ReturnLoginDTO();
        UsuariDTO auxUser = usuariMapper.toDTO(existUser);
        ret.setIdUsuari(auxUser.getIdUsuari());
        ret.setLoginCorrect(true);
        ret.setToken(jwtAuthtenticationConfig.getJWTToken(auxUser.getNom(), auxUser.getIdUsuari()));
        ret.setCodiRol(auxUser.getRol().getCodi());
        return ret;
    }
    public ReturnLoginDTO registraUser(final UsuariDTO newUser, final Boolean google) throws ServletException {
        UsuariEntity user = usuariRepository.findByEmail(newUser.getEmail()).orElse(null);
        if(user != null){
            return new ReturnLoginDTO(null, null, null, false, "Error: ja existeix un usuari amb aquest email.");
        }//Falta tirar la exception
        UsuariEntity newU = usuariMapper.toEntity(newUser);
        usuariRepository.save(newU);
        if (google){
            return processOAuthPostLogin(newUser.getEmail());
        }
        return tryLoginLocal(newUser.getEmail(), newUser.getContrasenya());
    }

    @Override
    public void bloqueaCuenta(Long idUsuariBloquejat) throws EntityNotFoundException, InvalidPasswordException{
        UsuariEntity blockEntity = usuariRepository.findById(idUsuariBloquejat).orElse(null);
        if (blockEntity != null) {
            blockEntity.setBloquejat(true);
            usuariRepository.save(blockEntity);
            UsuariLogInFallidoEntity aux = failRepo.findByUsuariIdUsuari(idUsuariBloquejat).orElse(null);
            if(aux == null){
                aux = new UsuariLogInFallidoEntity();
                aux.setDataFinalizacionBan(LocalDateTime.now().plusDays(1));
                aux.setNumeroIntentos(3);
                aux.setUsuari(blockEntity);
                failRepo.save(aux);
            }
            else{
                aux.setDataFinalizacionBan(LocalDateTime.now().plusDays(1));
                aux.setNumeroIntentos(3);
                failRepo.save(aux);
            }

        }
    }

    @Override
    public void desbloquearCuenta(Long idUsuariBloquejat) throws EntityNotFoundException, InvalidPasswordException{
        UsuariEntity blockEntity = usuariRepository.findById(idUsuariBloquejat).orElse(null);
        if (blockEntity != null) {
            blockEntity.setBloquejat(false);
            usuariRepository.save(blockEntity);
            UsuariLogInFallidoEntity logInFallidoEntity = failRepo.findByUsuariIdUsuari(idUsuariBloquejat).orElse(null);
            if (logInFallidoEntity != null) {
                failRepo.delete(logInFallidoEntity);
            }
        }

    }

    @Override
    public Page<UsuariDTO> devuelveAllUsers(Integer page, Integer rpp){
        Pageable pageable = PageRequest.of(page-1, rpp, Sort.by("idUsuari"));
        Page<UsuariEntity> tmp = usuariRepository.findAllByRolCodiIsNot(RolsEnum.ADM.toString(), pageable);
        Page<UsuariDTO> ret = tmp.map(usuariMapper::toDTO);
        return ret;
    }


}
