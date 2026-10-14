package com.senac.br.api_projetos.dto.equipe;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EquipeRequestDTO(
                @NotNull(message = "Equipe precisa de um gerente") Integer idGerente,
                @NotEmpty(message = "Precisa ter pelo menos um desenvolvedor") List<Integer> idDesenvolvedores

) {

}
