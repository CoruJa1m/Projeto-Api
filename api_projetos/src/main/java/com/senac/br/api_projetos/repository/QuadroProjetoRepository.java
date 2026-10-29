package com.senac.br.api_projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.senac.br.api_projetos.model.QuadroProjeto;
import java.util.List;

public interface QuadroProjetoRepository extends JpaRepository<QuadroProjeto, Integer> {

    List<QuadroProjeto> findByProjetoId(int id);
}
