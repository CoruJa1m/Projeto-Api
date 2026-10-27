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

import com.senac.br.api_projetos.dto.atividade.AtividadeRequestDTO;
import com.senac.br.api_projetos.dto.atividade.AtividadeAtualizacaoDTO;
import com.senac.br.api_projetos.dto.atividade.AtividadeResponseDTO;
import com.senac.br.api_projetos.service.AtividadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atividade")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    @PostMapping
    public ResponseEntity<AtividadeResponseDTO> cadastrarAtividade(
            @Valid @RequestBody AtividadeRequestDTO request) {
        return ResponseEntity.status(201).body(atividadeService.salvar(request));
    }

    @DeleteMapping("/{id}")
    public String deletarAtividade(@PathVariable int id) {
        return atividadeService.deletar(id);
    }

    @GetMapping("/{id}")
    public AtividadeResponseDTO buscarAtividade(@PathVariable int id) {
        return atividadeService.buscarPorId(id);
    }

    @GetMapping
    public List<AtividadeResponseDTO> listarAtividades() {
        return atividadeService.listar();
    }

    @PutMapping("/{id}")
    public AtividadeResponseDTO atualizarAtividadePorId(@PathVariable int id,
            @RequestBody @Valid AtividadeAtualizacaoDTO request) {
        return atividadeService.atualizarPorId(id, request);
    }

    @PutMapping("/{id}/desenvolvedores/{idDesenvolvedor}")
    public AtividadeResponseDTO atribuirDesenvolvedor(@PathVariable int id,
            @PathVariable int idDesenvolvedor) {
        return atividadeService.atribuirDesenvolvedor(id, idDesenvolvedor);
    }

    @DeleteMapping("/{id}/desenvolvedores/{idDesenvolvedor}")
    public AtividadeResponseDTO removerDesenvolvedor(@PathVariable int id,
            @PathVariable int idDesenvolvedor) {
        return atividadeService.removerDesenvolvedor(id, idDesenvolvedor);
    }

    @PutMapping("/{id}/quadro/{idQuadro}")
    public AtividadeResponseDTO moverParaQuadro(@PathVariable int id,
            @PathVariable int idQuadro) {
        return atividadeService.moverParaQuadro(id, idQuadro);
    }

}
