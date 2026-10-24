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

import com.senac.br.api_projetos.dto.projeto.ProjetoAtualizacaoDTO;
import com.senac.br.api_projetos.dto.projeto.ProjetoRequestDTO;
import com.senac.br.api_projetos.dto.projeto.ProjetoResponseDTO;
import com.senac.br.api_projetos.service.ProjetoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }

    @DeleteMapping("/{id}")
    public String deletarProjeto(@PathVariable int id) {
        return projetoService.deletar(id);
    }

    @PostMapping
    public ProjetoResponseDTO cadastrarProjeto(@RequestBody @Valid ProjetoRequestDTO projeto) {
        return projetoService.salvar(projeto);
    }

    @GetMapping("/{id}")
    public ProjetoResponseDTO buscarProjeto(@PathVariable int id) {
        return projetoService.buscarPorId(id);
    }

    @GetMapping
    public List<ProjetoResponseDTO> listarProjetos() {
        return projetoService.listar();
    }

    @PutMapping("/{id}")
    public ProjetoResponseDTO buscarEntidadePorId(@PathVariable int id,
            @RequestBody @Valid ProjetoRequestDTO request) {
        return projetoService.atualizarPorId(id, null);
    }


    @PutMapping("projeto/{id}")
    public ProjetoResponseDTO atualizarProjetoPorId(@PathVariable int id, @RequestBody @Valid ProjetoAtualizacaoDTO request) {
        return projetoService.atualizarPorId(id, request);
        }
}
