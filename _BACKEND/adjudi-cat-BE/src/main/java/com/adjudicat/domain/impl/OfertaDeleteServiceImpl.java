package com.adjudicat.domain.impl;

import com.adjudicat.domain.service.OfertaDeleteService;
import com.adjudicat.repository.repository.OfertaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfertaDeleteServiceImpl implements OfertaDeleteService {

    private final OfertaRepository ofertaRepository;

    @Override
    @Transactional
    public void deleteOfertesByContracte(Long idContracte) {
        ofertaRepository.deleteAllByContracteIdContracte(idContracte);
    }

    @Override
    public List<Long> getLicitadorsContracte(Long idContracte) {
        return ofertaRepository.findLicitadorsContracte(idContracte);
    }

}
