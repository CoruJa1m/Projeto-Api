package com.senac.br.api_projetos.model;

import java.math.BigDecimal;

import jakarta.persistence.MappedSuperclass;
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

    protected String nome;
    protected BigDecimal salario;
    protected String email;
}
