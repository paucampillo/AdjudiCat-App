package com.adjudicat.domain.impl;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.adjudicat.controller.dto.ContracteDTO;
import com.adjudicat.controller.dto.FavoritsDTO;
import com.adjudicat.controller.dto.ReturnFavoritsDTO;
import com.adjudicat.controller.dto.UsuariDTO;
import com.adjudicat.domain.model.mapper.ContracteMapper;
import com.adjudicat.domain.model.mapper.FavoritsMapper;
import com.adjudicat.domain.service.FavoritsService;
import com.adjudicat.exception.RepositoryException;
import com.adjudicat.repository.entity.ContracteEntity;
import com.adjudicat.repository.entity.FavoritsEntity;
import com.adjudicat.repository.entity.UsuariEntity;
import com.adjudicat.repository.repository.ContracteRepository;
import com.adjudicat.repository.repository.FavoritsRepository;
import com.adjudicat.repository.repository.UsuariRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritsServiceImpl implements FavoritsService {

    private final FavoritsRepository favoritsRepository;
    private final UsuariRepository userRepo;
    private final ContracteRepository contRepo;
    private final ContracteMapper contMapper;


    @Override
    public void addFavorit(ReturnFavoritsDTO fave) throws NoSuchElementException {
        UsuariEntity auxUser = userRepo.findById(fave.getIdUsuari()).orElse(null);

        ContracteEntity auxCont = contRepo.findById(fave.getIdContracte()).orElse(null);
        if(auxUser != null && auxCont != null) {

            FavoritsEntity faveEnt = new FavoritsEntity();

            faveEnt.setUsuari(auxUser);
            faveEnt.setContracte(auxCont);

            favoritsRepository.save(faveEnt);
        }
        else {
        throw new NoSuchElementException("No se ha podido encontrar el usuario/contrato");
        }

    }

    @Override
    public void removeFavorit(ReturnFavoritsDTO fave) throws DataAccessException {
        FavoritsEntity faveAux = favoritsRepository.findByUsuariIdUsuariAndContracteIdContracte(fave.getIdUsuari(), fave.getIdContracte()).orElse(null);
        if(faveAux != null) {

            favoritsRepository.delete(faveAux);
        }
        else {
            throw new NoSuchElementException("No se ha podido encontrar el favorito");
        }
    }

    @Override
    public Page<ContracteDTO> listFavorits(Integer page, Integer cpp, Long idUser) {
        Pageable pg = PageRequest.of(page-1, cpp);

        Page<ContracteEntity> results = favoritsRepository.findAllByUsuariIdUsuari(idUser, pg);
        Page<ContracteDTO> resultsDTO = results.map(contMapper::toDTO);
        for (ContracteDTO c : resultsDTO) {
            c.setPreferit(true);
        }
        return resultsDTO;

    }

    @Override
    public Boolean isFavorit(Long idUser, Long idContracte) {
        return favoritsRepository.findByUsuariIdUsuariAndContracteIdContracte(idUser, idContracte).isPresent();
    }

    @Override
    @Transactional
    public void deleteFavorit(Long idUsuari, Long idContracte) {
        FavoritsEntity fave = favoritsRepository.findByUsuariIdUsuariAndContracteIdContracte(idUsuari, idContracte).orElse(null);
        if(fave != null) {
            favoritsRepository.delete(fave);
        }
        else {
            throw new NoSuchElementException("No se ha podido encontrar el favorito");
        }
    }


}
