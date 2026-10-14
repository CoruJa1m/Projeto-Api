package com.senac.br.api_projetos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.dto.equipe.EquipeRequestDTO;
import com.senac.br.api_projetos.dto.equipe.EquipeResponseDTO;
import com.senac.br.api_projetos.service.EquipeService;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping
    public EquipeResponseDTO cadastrarEquipe(@RequestBody EquipeRequestDTO equipe) {
        return equipeService.salvar(equipe);
    }

    @DeleteMapping("/deletar/{id}")
    public String deletarEquipe(@PathVariable int id) {
        return equipeService.deletar(id);
    }

    @GetMapping
    public List<EquipeResponseDTO> listarEquipe() {
        return equipeService.listar();
    }

    @GetMapping("/{id}")
    public EquipeResponseDTO buscarEquipe(@PathVariable int id) {
        return equipeService.buscarPorid(id);
    }

    @PutMapping("/{id}")
    public EquipeResponseDTO atualizarEquipe(@PathVariable int id, @RequestBody EquipeRequestDTO equipe) {
        return equipeService.atualizarPorId(id, equipe);

    }

}
