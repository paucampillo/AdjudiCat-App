package com.adjudicat.domain.impl;

import com.adjudicat.controller.dto.ValoracioInfoDTO;
import com.adjudicat.domain.model.mapper.ValoracioMapper;
import com.adjudicat.domain.service.ValoracioUsuariService;
import com.adjudicat.repository.repository.ValoracioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValoracioUsuariServiceImpl implements ValoracioUsuariService {

    private final ValoracioRepository valoracioRepository;
    private final ValoracioMapper valoracioMapper;

    @Override
    public ValoracioInfoDTO getValoracioInfo(final Long idUsuari) {
        return valoracioRepository.findAverageAndTotalRatingsByUserId(idUsuari);
    }

    @Override
    public ValoracioInfoDTO getValoracioInfoEmisor(final Long idUsuari) {
        return valoracioRepository.findAverageAndTotalRatingsByUserIdEmisor(idUsuari);
    }
}
