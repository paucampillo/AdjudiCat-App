package com.adjudicat.domain.service;

import java.util.List;

public interface OfertaDeleteService {
    void deleteOfertesByContracte(Long idContracte);
    List<Long> getLicitadorsContracte(Long idContracte);

}
