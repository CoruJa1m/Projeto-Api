package com.senac.br.api_projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senac.br.api_projetos.model.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe, Integer> {
    // Verifica se existe uma equipe associada a um gerente específico
    boolean existsByGerente_Id(Integer id);

}
