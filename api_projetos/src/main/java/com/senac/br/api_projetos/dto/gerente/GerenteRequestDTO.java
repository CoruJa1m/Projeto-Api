package com.senac.br.api_projetos.dto.gerente;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record GerenteRequestDTO(
                @NotBlank(message = "Nome não pode estar vazio") String nome,
                @NotNull(message = "salário não pode estar vazio") @PositiveOrZero(message = "precisa ser maior que zero") BigDecimal salario,
                @NotBlank(message = "e-mail não pode estar vazio") @Email(message = "e-mail inválido") String email,
                @NotEmpty(message = "Lista não pode estar vazia") List<String> certificacoes) {

}
