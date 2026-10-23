package com.senac.br.api_projetos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senac.br.api_projetos.dto.desenvolvedor.DesenvolvedorRequestDTO;
import com.senac.br.api_projetos.dto.desenvolvedor.DesenvolvedorResponseDTO;
import com.senac.br.api_projetos.service.DesenvolvedorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/desenvolvedor")
public class DesenvolvedorController {

    private final DesenvolvedorService desenvolvedorService;

    public DesenvolvedorController(DesenvolvedorService desenvolvedorService) {
        this.desenvolvedorService = desenvolvedorService;
    }

    @PostMapping
    public DesenvolvedorResponseDTO cadastrarDesenvolvedor(@RequestBody @Valid DesenvolvedorRequestDTO desenvolvedor) {
        return desenvolvedorService.salvar(desenvolvedor);
    }

    @DeleteMapping("/{id}")
    public String deletarDesenvolvedor(@PathVariable int id) {
        return desenvolvedorService.deletar(id);
    }

    @GetMapping
    public List<DesenvolvedorResponseDTO> listarDesenvolvedor() {
        return desenvolvedorService.listar();
    }

    @GetMapping("/{id}")
    public DesenvolvedorResponseDTO buscarEntidadePorId(@PathVariable int id) {
        return desenvolvedorService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public DesenvolvedorResponseDTO atualizarDesenvolvedorPorId(@PathVariable int id,
            @RequestBody @Valid DesenvolvedorRequestDTO request) {
        return desenvolvedorService.atualizarPorId(id, request);
    }

}
