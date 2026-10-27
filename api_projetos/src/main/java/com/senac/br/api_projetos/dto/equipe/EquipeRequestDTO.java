package com.senac.br.api_projetos.dto.equipe;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EquipeRequestDTO(
                @NotNull(message = "Equipe precisa de um gerente") @Positive Integer idGerente,
                @NotEmpty(message = "Precisa ter pelo menos um desenvolvedor") List<@NotNull @Positive Integer> idDesenvolvedores

) {

}
