package com.senac.br.api_projetos.dto.atividade;

import java.util.List;

import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.QuadroProjeto;
import com.senac.br.api_projetos.model.enums.StatusAtividade;

public record AtividadeResponseDTO(
        Integer id,
        String descricao,
        StatusAtividade status,
        QuadroProjeto quadroProjeto,
        List<Desenvolvedor> desenvolvedores

) {

}
