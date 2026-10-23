package com.senac.br.api_projetos.dto.projeto;

import java.time.LocalDateTime;
import java.util.List;

import com.senac.br.api_projetos.model.QuadroProjeto;

public record ProjetoResponseDTO(
                Integer id,
                String nome,
                Boolean ativo,
                LocalDateTime prazo,
                Integer idEquipe,
                List<Integer> idQuadros) {

}
