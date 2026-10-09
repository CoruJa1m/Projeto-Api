package com.senac.br.api_projetos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.dto.desenvolvedor.EquipeRequestDTO;
import com.senac.br.api_projetos.service.EquipeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/Equipe")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping("/SalvarEquipe")
    public String cadastrarEquipe(@RequestBody EquipeRequestDTO equipe) {
        return equipeService.salvar(equipe);
    }

}
