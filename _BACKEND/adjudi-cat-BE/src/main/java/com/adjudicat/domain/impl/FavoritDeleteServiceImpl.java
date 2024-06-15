package com.adjudicat.domain.impl;

import com.adjudicat.domain.service.FavoritDeleteService;
import com.adjudicat.repository.repository.FavoritsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoritDeleteServiceImpl implements FavoritDeleteService {

        private final FavoritsRepository favoritsRepository;

        @Override
        @Transactional
        public void deleteAllByIdContracte(Long idContracte) {
            favoritsRepository.deleteAll(idContracte);
        }
}
