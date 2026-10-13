package com.senac.br.api_projetos.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.dto.equipe.EquipeRequestDTO;
import com.senac.br.api_projetos.service.EquipeService;

@RestController
@RequestMapping("/Equipe")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping("/SalvarEquipe")
    public String cadastrarEquipe(@org.springframework.web.bind.annotation.RequestBody EquipeRequestDTO equipe) {
        System.out.println(equipe);
        return equipeService.salvar(equipe);
    }

    @DeleteMapping("/DeletarEquipe/{id}")
    public String deletarEquipe(@PathVariable int id) {
        return equipeService.deletar(id);
    }

    // esse metodo não está funcionando, ele retorna uma lista vazia mesmo com dados
    // cadastrados
    @GetMapping("/ListarEquipe/{id}")
    public String listarEquipe(@PathVariable int id) {
        return equipeService.listar();
    }

    // esse metodo esta retornando o objeto e nao o to string de forma correta
    // verificar
    @GetMapping("/BuscarEquipe/{id}")
    public String buscarEquipe(@PathVariable int id) {
        return equipeService.buscarPorid(id);
    }

    @PutMapping("AtualizarEquipe/{id}")
    public String atualizarEquipe(@PathVariable int id, @RequestBody EquipeRequestDTO equipe) {
        return equipeService.atualizarPorId(id, equipe);

    }

}
