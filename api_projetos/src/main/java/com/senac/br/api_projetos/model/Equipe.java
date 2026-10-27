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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "equipe")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "gerente_id", nullable = false)
    @ToString.Exclude
    private GerenteProjeto gerente;

    @OneToMany(mappedBy = "equipe")
    @ToString.Exclude
    private List<Desenvolvedor> desenvolvedores = new ArrayList<>();

    public void adicionarDesenvolvedor(Desenvolvedor desenvolvedor) {
        if (!desenvolvedores.contains(desenvolvedor)) {
            desenvolvedores.add(desenvolvedor);
        }
        desenvolvedor.setEquipe(this);
    }

    public void removerDesenvolvedor(Desenvolvedor desenvolvedor) {
        if (desenvolvedores.remove(desenvolvedor)) {
            desenvolvedor.setEquipe(null);
        }
    }
}
