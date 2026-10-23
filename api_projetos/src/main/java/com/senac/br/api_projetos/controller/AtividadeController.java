package com.senac.br.api_projetos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.dto.atividade.AtividadeRequestDTO;
import com.senac.br.api_projetos.dto.atividade.AtividadeResponseDTO;
import com.senac.br.api_projetos.service.AtividadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atividade")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    @PostMapping
    public AtividadeResponseDTO cadastrarAtividade(@RequestBody @Valid AtividadeRequestDTO atividade) {
        return atividadeService.salvar(atividade);
    }

}
