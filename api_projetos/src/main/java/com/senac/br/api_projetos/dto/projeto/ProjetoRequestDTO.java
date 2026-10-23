package com.senac.br.api_projetos.dto.projeto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProjetoRequestDTO(
        @NotNull(message = "Equipe é  obrigatorio, verificar ") @Positive(message = "O id da equipe deve ser positivo") Integer idEquipe,
        @NotBlank(message = "Nome não pode estar vazio") String nome,
        @NotNull(message = "Projeto precisa de um prazo") LocalDateTime prazo) {

}
