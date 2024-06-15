package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.IdiomesDTO;
import com.adjudicat.domain.model.mapper.IdiomesMapper;
import com.adjudicat.domain.service.IdiomesService;
import com.adjudicat.enums.IdiomaEnum;
import com.adjudicat.repository.repository.IdiomesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdiomesServiceImpl implements IdiomesService {

    private final IdiomesRepository idiomesRepository;
    private final IdiomesMapper idiomesMapper;

    @Override
    public List<IdiomesDTO> listar() {
        return idiomesRepository.findAll().stream()
                .map(idiomesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IdiomesDTO getIdioma(IdiomaEnum code) {
        return idiomesMapper.toDTO(idiomesRepository.findByCodi(code.toString()));
    }
}
