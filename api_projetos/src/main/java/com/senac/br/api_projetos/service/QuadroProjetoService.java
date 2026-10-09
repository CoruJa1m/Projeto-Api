package com.senac.br.api_projetos.service;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.repository.QuadroProjetoRepository;

@Service
public class QuadroProjetoService {

    private final QuadroProjetoRepository quadroProjetoRepository;

    public QuadroProjetoService(QuadroProjetoRepository quadroProjetoRepository) {
        this.quadroProjetoRepository = quadroProjetoRepository;
    }
}
