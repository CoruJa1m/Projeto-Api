package com.senac.br.api_projetos.service;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.desenvolvedor.EquipeRequestDTO;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.repository.EquipeRepository;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    public String salvar(EquipeRequestDTO equipe) {
        Equipe novaEquipe = new Equipe();

        novaEquipe.setGerente(equipe.gerente());
        return "Equipe salva com sucesso";

    }

}
