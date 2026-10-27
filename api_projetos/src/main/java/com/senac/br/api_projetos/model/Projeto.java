package com.senac.br.api_projetos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private Boolean ativo = true;

    private LocalDateTime prazo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "equipe_id", nullable = false)
    @ToString.Exclude
    private Equipe equipe;

    @OneToMany(mappedBy = "projeto")
    @ToString.Exclude
    private List<QuadroProjeto> quadros = new ArrayList<>();

}
