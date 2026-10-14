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

import com.senac.br.api_projetos.dto.gerente.GerenteRequestDTO;
import com.senac.br.api_projetos.dto.gerente.GerenteResponseDTO;
import com.senac.br.api_projetos.service.GerenteService;

@RestController
@RequestMapping("/Gerente")
public class GerenteController {

    private final GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    @PostMapping("/salvarGerente")
    public GerenteResponseDTO cadastrarGerente(@RequestBody GerenteRequestDTO gerente) {
        return gerenteService.salvar(gerente);
    }

    @DeleteMapping("/deletarGerente/{id}")
    public String deletarGerente(@PathVariable int id) {
        return gerenteService.deletar(id);
    }

    @GetMapping("/listarGerente/{id}")
    public List<GerenteResponseDTO> listarGerente() {
        return gerenteService.listar();
    }

    @GetMapping("/buscarGerente/{id}")
    public GerenteResponseDTO buscarGerentePorId(@PathVariable int id) {
        return gerenteService.buscarPorid(id);
    }

    @PutMapping("/atualizarGerente/{id}")
    public GerenteResponseDTO atualizarGerente(@PathVariable int id, @RequestBody GerenteRequestDTO request) {
        return gerenteService.atualizarPorId(id, request);
    }
}
