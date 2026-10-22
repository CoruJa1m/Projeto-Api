package com.senac.br.api_projetos.dto.atividade;

import java.util.List;

import com.senac.br.api_projetos.model.enums.StatusAtividade;

public record AtividadeResponseDTO(
    Integer idAtividade,
    String descricao,
    StatusAtividade status,
    Integer idQuadroProjeto,
    List<Integer> idDesenvolvedores


    
) {

}
