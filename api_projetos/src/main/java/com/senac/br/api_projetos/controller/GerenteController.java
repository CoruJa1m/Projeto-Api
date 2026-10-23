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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/gerente")
public class GerenteController {

    private final GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    @PostMapping
    public GerenteResponseDTO cadastrarGerente(@RequestBody @Valid GerenteRequestDTO gerente) {
        return gerenteService.salvar(gerente);
    }

    @DeleteMapping("/{id}")
    public String deletarGerente(@PathVariable int id) {
        return gerenteService.deletar(id);
    }

    @GetMapping
    public List<GerenteResponseDTO> listarGerente() {
        return gerenteService.listar();
    }

    @GetMapping("/{id}")
    public GerenteResponseDTO buscarEntidadePorId(@PathVariable int id) {
        return gerenteService.buscarPorid(id);
    }

    @PutMapping("/{id}")
    public GerenteResponseDTO atualizarGerente(@PathVariable int id, @RequestBody @Valid GerenteRequestDTO request) {
        return gerenteService.atualizarPorId(id, request);
    }
}
