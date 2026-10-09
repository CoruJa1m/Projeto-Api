package com.senac.br.api_projetos.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Funcionario {
    @NotBlank(message = "Nome não pode estar vazio")
    protected String nome;

    @NotNull(message = "salário não pode estar vazio")
    @PositiveOrZero(message = "precisa ser maior que zero")
    protected BigDecimal salario;

    @NotBlank(message = "e-mail não pode estar vazio")
    @Email(message = "e-mail inválido")
    protected String email;
}
