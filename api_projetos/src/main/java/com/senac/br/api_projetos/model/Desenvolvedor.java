package com.senac.br.api_projetos.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senac.br.api_projetos.model.enums.Senioridade;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "desenvolvedor")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Desenvolvedor extends Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Cargo não pode estar vazio")
    private String cargo;

    @Enumerated(EnumType.STRING)
    private Senioridade senioridade;

    @ManyToOne
    @JsonIgnore
    private Equipe equipe;

    @ManyToMany
    @JoinTable(name = "atividade_desenvolvedor", joinColumns = @JoinColumn(name = "desenvolvedor_id"), inverseJoinColumns = @JoinColumn(name = "atividade_id"))
    private List<Atividade> atividades = new ArrayList<>();
}
