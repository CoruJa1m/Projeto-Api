package com.senac.br.api_projetos.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quadro_projeto")
@NoArgsConstructor
@Getter
@Setter
public class QuadroProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    @NotNull(message = "Este quadro precisa de um projeto associado")
    private Projeto projeto;

    @OneToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    @NotNull(message = "Este quadro precisa de uma equipe associada")
    private Equipe equipe;

    @OneToMany(mappedBy = "quadroProjeto")
    private List<Atividade> atividades = new ArrayList<>();

}
