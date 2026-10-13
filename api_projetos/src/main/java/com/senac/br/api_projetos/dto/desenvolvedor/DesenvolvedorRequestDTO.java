package com.senac.br.api_projetos.dto.desenvolvedor;

import java.math.BigDecimal;

import com.senac.br.api_projetos.model.enums.Senioridade;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record DesenvolvedorRequestDTO(
        @NotBlank(message = "Nome não pode estar vazio") String nome,
        @NotNull(message = "salário não pode estar vazio") @PositiveOrZero(message = "precisa ser maior que zero") BigDecimal salario,
        @NotBlank(message = "e-mail não pode estar vazio") @Email(message = "e-mail inválido") String email,
        @NotBlank(message = "Cargo não pode estar vazio") String cargo,
        Senioridade senioridade) {
}
