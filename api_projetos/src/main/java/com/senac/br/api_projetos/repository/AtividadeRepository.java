package com.senac.br.api_projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senac.br.api_projetos.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {

}
