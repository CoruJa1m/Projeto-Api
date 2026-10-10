package com.senac.br.api_projetos.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.model.Projeto;
import com.senac.br.api_projetos.service.ProjetoService;

@RestController 
@RequestMapping("/Projeto")
public class ProjetoController {


    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService){
        this.projetoService = projetoService;
    }


    // metodo nao funcionavel, provavel nao ha dados(quadros, atividades)
    // revisar novamente domingo
    @PostMapping("/salvarProjeto")
    public String cadastrarProjeto(@RequestBody Projeto projeto) {
        return projetoService.salvar(projeto);
    }


    // nao testado pois esta tendo erro no  salvar 
     // revisar novamente domingo
    @PostMapping("/deletarProjeto/{id}")
    public String deletarProjeto(@PathVariable int id) {
        return projetoService.deletar(id);
    }
}
