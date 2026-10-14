package com.senac.br.api_projetos.dto.quadro_projeto;

import java.util.List;

import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.model.Projeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record QuadroProjetoRequestDTO(

        @NotBlank(message = "Projeto não pode ser vazio") Integer idProjeto,
        @NotBlank(message = "Equipe não pode estar vazia") Integer idEquipe,
        @NotEmpty(message = "Atividade não pode estar vazia") List<Integer> idAtividades) {
}