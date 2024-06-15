package com.adjudicat.domain.impl;

import com.adjudicat.domain.model.mapper.UsuariLogInFallidoMapper;
import com.adjudicat.domain.service.UsuariLogInFallidoService;
import com.adjudicat.repository.repository.UsuariLogInFallidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuariLogInFallidoServiceImpl implements UsuariLogInFallidoService {

    private final UsuariLogInFallidoRepository usuariLogInFallidoRepository;
    private final UsuariLogInFallidoMapper usuariLogInFallidoMapper;
}
