package com.senac.br.api_projetos.dto.equipe;

import java.util.List;

import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.GerenteProjeto;

public record EquipeResponseDTO(
                Integer id,
                GerenteProjeto gerente,
                List<Desenvolvedor> desenvolvedores) {

}
