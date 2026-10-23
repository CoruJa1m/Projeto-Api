package com.senac.br.api_projetos.dto.atividade;

import java.util.List;

import com.senac.br.api_projetos.model.enums.StatusAtividade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AtividadeRequestDTO(

                @NotNull(message = "O id da atividade não pode ser nulo") Integer idAtividade,

                @NotBlank(message = "Descrição não pode estar vazia") String descricao,

                @NotNull(message = "Status é obrigatório") StatusAtividade status,

                @NotNull(message = "Quadro do projeto é obrigatório") Integer idQuadroProjeto,

                @NotEmpty(message = "Informe ao menos um desenvolvedor") List<Integer> idDesenvolvedores

) {

}