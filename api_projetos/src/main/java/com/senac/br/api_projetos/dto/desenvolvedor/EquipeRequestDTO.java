package com.senac.br.api_projetos.dto.desenvolvedor;

import java.util.List;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.GerenteProjeto;

public record EquipeRequestDTO(
                
                Integer idGerente,
                List<Integer> idDesenvolvedores


) {

}
