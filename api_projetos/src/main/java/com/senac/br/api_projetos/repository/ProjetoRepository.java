package com.senac.br.api_projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.senac.br.api_projetos.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

}
