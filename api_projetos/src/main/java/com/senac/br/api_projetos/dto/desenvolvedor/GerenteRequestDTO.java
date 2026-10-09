package com.senac.br.api_projetos.dto.desenvolvedor;

import java.math.BigDecimal;
import java.util.List;

public record GerenteRequestDTO(
                String nome,
                BigDecimal salario,
                String email,
                List<String> certificacoes) {

}
