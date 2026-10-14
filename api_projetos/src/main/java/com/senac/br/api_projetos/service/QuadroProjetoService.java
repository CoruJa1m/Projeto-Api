package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.equipe.EquipeResponseDTO;
import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoRequestDTO;
import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoResponseDTO;
import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.model.QuadroProjeto;
import com.senac.br.api_projetos.repository.AtividadeRepository;
import com.senac.br.api_projetos.repository.EquipeRepository;
import com.senac.br.api_projetos.repository.ProjetoRepository;
import com.senac.br.api_projetos.repository.QuadroProjetoRepository;

@Service
public class QuadroProjetoService {

    private final QuadroProjetoRepository quadroProjetoRepository;
    private final ProjetoRepository projetoRepository;
    private final EquipeRepository equipeRepository;
    private final AtividadeRepository atividadeRepository;

    public QuadroProjetoService(QuadroProjetoRepository quadroProjetoRepository, ProjetoRepository projetoRepository,
            EquipeRepository equipeRepository, AtividadeRepository atividadeRepository) {
        this.quadroProjetoRepository = quadroProjetoRepository;
        this.projetoRepository = projetoRepository;
        this.equipeRepository = equipeRepository;
        this.atividadeRepository = atividadeRepository;
    }

    public QuadroProjetoResponseDTO salvar(QuadroProjetoRequestDTO request) {

        QuadroProjeto novoQuadroProjeto = new QuadroProjeto();
        novoQuadroProjeto.setEquipe(equipeRepository.findById(request.idEquipe())
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada")));

        novoQuadroProjeto.setProjeto(projetoRepository.findById(request.idProjeto())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado")));

        novoQuadroProjeto.setAtividades(atividadeRepository.findAllById(request.idAtividades()));

    }

}
