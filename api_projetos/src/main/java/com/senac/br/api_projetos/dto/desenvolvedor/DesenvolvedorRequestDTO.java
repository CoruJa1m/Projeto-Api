package com.senac.br.api_projetos.dto.desenvolvedor;

import java.math.BigDecimal;

import com.senac.br.api_projetos.model.enums.Senioridade;

public record DesenvolvedorRequestDTO(
                String nome,
                BigDecimal salario,
                String email,
                String cargo,
                Senioridade senioridade) {
}
