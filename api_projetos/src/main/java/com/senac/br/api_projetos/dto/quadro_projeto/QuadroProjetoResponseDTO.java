package com.senac.br.api_projetos.dto.quadro_projeto;

import java.util.List;

public record QuadroProjetoResponseDTO(
                Integer id,
                Integer idProjeto,
                List<Integer> idAtividades) {

}
