package com.senac.br.api_projetos.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.dto.desenvolvedor.GerenteRequestDTO;
import com.senac.br.api_projetos.service.GerenteService;

@RestController
@RequestMapping("/Gerente")
public class GerenteController {

    private final GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    @PostMapping("/salvarGerente")
    public String cadastrarGerente(@RequestBody GerenteRequestDTO gerente) {
        return gerenteService.salvar(gerente);
    }

    @DeleteMapping("/deletarGerente/{id}")
    public String deletarGerente(@PathVariable int id) {
        return gerenteService.deletar(id);
    }

    @GetMapping("/listarGerente/{id}")
    public String listarGerente(@PathVariable int id) {
        return gerenteService.listar();
    }

    @GetMapping("/buscarGerente/{id}")
    public String buscarGerente(@PathVariable int id) {
        return gerenteService.buscarPorid(id);
    }

    @PutMapping("/atualizarGerente/{id}")
    public String atualizarGerente(@PathVariable int id, @RequestBody GerenteRequestDTO gerente) {
        return gerenteService.atualizarPorId(id, gerente);
    }
}
