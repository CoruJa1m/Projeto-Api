package com.senac.br.api_projetos.dto.quadro_projeto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record QuadroProjetoRequestDTO(

        @NotNull(message = "Projeto não pode ser vazio") @Positive(message = "O id do projeto deve ser positivo") Integer idProjeto,
        @NotNull(message = "Equipe não pode estar vazia") @Positive(message = "O id da equipe deve ser positivo") Integer idEquipe,
        @NotEmpty(message = "Atividade não pode estar vazia") List<@NotNull @Positive(message = "O id da atividade deve ser positivo") Integer> idAtividades) {
}