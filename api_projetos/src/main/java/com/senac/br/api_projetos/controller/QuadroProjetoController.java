package com.senac.br.api_projetos.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoRequestDTO;
import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoResponseDTO;
import com.senac.br.api_projetos.service.QuadroProjetoService;

@RestController 
@RequestMapping("/QuadroProjeto")
public class QuadroProjetoController {

    private final QuadroProjetoService quadroProjetoService;

    public QuadroProjetoController(QuadroProjetoService quadroProjetoService){
        this.quadroProjetoService = quadroProjetoService;
    }

    @GetMapping()
    public List<QuadroProjetoResponseDTO> listarProjetos(){
        return quadroProjetoService.listar();

    }

    @GetMapping ("/{id}")
    public QuadroProjetoResponseDTO buscarQuadroProjeto(@PathVariable int id){
        return quadroProjetoService.buscarPorId(id);
    }

    @DeleteMapping("/deletarQuadroProjeto/{id}")
    public String deletarQuadroProjeto(@PathVariable int id){
        return quadroProjetoService.deletar(id);
    }

    @PostMapping("/salvarQuadroProjeto")
    public QuadroProjetoResponseDTO cadastrarQuadroProjeto(@Valid @RequestBody QuadroProjetoRequestDTO quadroProjeto){
        return quadroProjetoService.salvar(quadroProjeto);
    }


    @PutMapping("/atualizar/{id}")
    public QuadroProjetoResponseDTO atualizarQuadroProjetoPorId(@PathVariable int id, @RequestBody QuadroProjetoRequestDTO request){
        return quadroProjetoService.atualizarPorId(id, request);
    }


}
