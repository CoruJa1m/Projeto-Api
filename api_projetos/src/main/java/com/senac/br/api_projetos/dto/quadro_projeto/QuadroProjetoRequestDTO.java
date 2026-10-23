package com.senac.br.api_projetos.dto.quadro_projeto;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Positive;

public record QuadroProjetoRequestDTO(

                @NotNull(message = "Projeto não pode ser vazio") @Positive(message = "O id do projeto deve ser positivo") Integer idProjeto) {
}