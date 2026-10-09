package com.senac.br.api_projetos.model;

import java.util.ArrayList;
import java.util.List;

import com.senac.br.api_projetos.model.enums.StatusAtividade;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "atividade")
@NoArgsConstructor
@Getter
@Setter
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusAtividade status;

    @NotBlank(message = "Atividade precisa de uma descrição")
    private String descricao;

    @ManyToMany(mappedBy = "atividades")
    @NotEmpty(message = "Essa atividade precisa de pelo menos um desenvolvedor atribuído")
    private List<Desenvolvedor> atribuidos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "quadro_projeto_id", nullable = false)
    private QuadroProjeto quadroProjeto;

}
