package com.senac.br.api_projetos.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "equipe")
@NoArgsConstructor
@Getter
@Setter
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "gerente_id", nullable = false)
    @NotNull(message = "Equipe precisa de um gerente")
    private GerenteProjeto gerente;

    @OneToMany(mappedBy = "equipe")
    @NotEmpty(message = "Precisa ter pelo menos um desenvolvedor")
    private List<Desenvolvedor> desenvolvedores = new ArrayList<>();

}
