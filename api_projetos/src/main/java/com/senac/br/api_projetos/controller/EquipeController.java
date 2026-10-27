package com.senac.br.api_projetos.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping
    public ResponseEntity<EquipeResponseDTO> cadastrarEquipe(@RequestBody @Valid EquipeRequestDTO equipe) {
        return ResponseEntity.status(201).body(equipeService.salvar(equipe));
    }

    @DeleteMapping("/{id}")
    public String deletarEquipe(@PathVariable int id) {
        return equipeService.deletar(id);
    }

    @GetMapping
    public List<EquipeResponseDTO> listarEquipe() {
        return equipeService.listar();
    }

    @GetMapping("/{id}")
    public EquipeResponseDTO buscarEntidarPorId(@PathVariable int id) {
        return equipeService.buscarPorid(id);
    }

    @PutMapping("/{id}")
    public EquipeResponseDTO atualizarEquipe(@PathVariable int id, @RequestBody @Valid EquipeRequestDTO equipe) {
        return equipeService.atualizarPorId(id, equipe);

    }

    @PutMapping("/{id}/desenvolvedores/{idDesenvolvedor}")
    public EquipeResponseDTO adicionarDesenvolvedor(@PathVariable int id,
            @PathVariable int idDesenvolvedor) {
        return equipeService.adicionarDesenvolvedor(id, idDesenvolvedor);
    }

    @DeleteMapping("/{id}/desenvolvedores/{idDesenvolvedor}")
    public EquipeResponseDTO removerDesenvolvedor(@PathVariable int id,
            @PathVariable int idDesenvolvedor) {
        return equipeService.removerDesenvolvedor(id, idDesenvolvedor);
    }

    @PutMapping("/{id}/gerente/{idGerente}")
    public EquipeResponseDTO alterarGerente(@PathVariable int id, @PathVariable int idGerente) {
        return equipeService.alterarGerente(id, idGerente);
    }

    @PutMapping("/{id}/desenvolvedores/{idDesenvolvedor}/transferencia/{idEquipe}")
    public EquipeResponseDTO transferirDesenvolvedor(@PathVariable int id,
            @PathVariable int idDesenvolvedor, @PathVariable int idEquipe) {
        return equipeService.transferirDesenvolvedor(id, idEquipe, idDesenvolvedor);
    }
}
