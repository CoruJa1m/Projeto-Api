package com.senac.br.api_projetos.dto.quadro_projeto;

import java.util.List;

import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.model.Projeto;

public record QuadroProjetoResponseDTO(
        Projeto projeto,
        Equipe equipe,
        List<Atividade> atividades) {

}
