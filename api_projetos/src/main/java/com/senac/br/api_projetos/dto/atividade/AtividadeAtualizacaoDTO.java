package com.senac.br.api_projetos.dto.atividade;

import com.senac.br.api_projetos.model.enums.StatusAtividade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtividadeAtualizacaoDTO(
    @NotBlank(message = "Descrição não pode estar vazia") String descricao,
    @NotNull (message = "Status é obrigatório") StatusAtividade status
) {

}
