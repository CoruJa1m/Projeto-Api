package com.senac.br.api_projetos.dto.equipe;

import java.util.List;

public record EquipeRequestDTO(
        Integer idGerente,
        List<Integer> idDesenvolvedores

) {

}
