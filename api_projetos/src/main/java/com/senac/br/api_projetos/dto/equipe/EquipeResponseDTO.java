package com.senac.br.api_projetos.dto.equipe;

import java.util.List;

public record EquipeResponseDTO(
        Integer id,
        Integer idGerente,
        List<Integer> idDesenvolvedores) {

}
