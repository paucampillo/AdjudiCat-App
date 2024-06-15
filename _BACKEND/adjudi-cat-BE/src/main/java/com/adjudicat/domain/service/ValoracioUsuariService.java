package com.adjudicat.domain.service;

import com.adjudicat.controller.dto.ValoracioInfoDTO;

public interface ValoracioUsuariService {

    ValoracioInfoDTO getValoracioInfo(Long idUsuari);

    ValoracioInfoDTO getValoracioInfoEmisor(Long idUsuari);

}
