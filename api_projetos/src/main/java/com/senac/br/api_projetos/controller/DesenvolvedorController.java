package com.senac.br.api_projetos.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.dto.desenvolvedor.DesenvolvedorRequestDTO;
import com.senac.br.api_projetos.service.DesenvolvedorService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/desenvolvedor")
public class DesenvolvedorController {

    private final DesenvolvedorService desenvolvedorService;

    public DesenvolvedorController(DesenvolvedorService desenvolvedorService) {
        this.desenvolvedorService = desenvolvedorService;

    }

    @PostMapping("/salvar")
    public String cadastrarDesenvolvedor(@RequestBody DesenvolvedorRequestDTO desenvolvedor) {
        return desenvolvedorService.salvar(desenvolvedor);
    }

    @DeleteMapping("/deletar/{id}")
    public String deletarDesenvolvedor(@PathVariable int id) {
        return desenvolvedorService.deletar(id);
    }

    @GetMapping("/listar")
    public String listarDesenvolvedor() {
        return desenvolvedorService.listar();
    }

    @GetMapping("/buscarPorId/{id}")
    public String getMethodName(@PathVariable int id) {
        return desenvolvedorService.buscarPorId(id);
    }

    @PutMapping("path/{id}")
    public String putMethodName(@PathVariable int id, @RequestBody DesenvolvedorRequestDTO request) {
        return desenvolvedorService.atualizarPorId(id, request);
    }

}
