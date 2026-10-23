package com.senac.br.api_projetos.dto.projeto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjetoRequestDTO(
                @NotBlank(message = "Nome não pode estar vazio") String nome,
                @NotNull(message = "Projeto precisa de um prazo") LocalDateTime prazo) {

}
