package com.senac.br.api_projetos.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoRequestDTO;
import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoResponseDTO;
import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.Projeto;
import com.senac.br.api_projetos.model.QuadroProjeto;
import com.senac.br.api_projetos.repository.ProjetoRepository;
import com.senac.br.api_projetos.repository.QuadroProjetoRepository;

@Service
public class QuadroProjetoService {

    private final QuadroProjetoRepository quadroProjetoRepository;
    private final ProjetoRepository projetoRepository;

    public QuadroProjetoService(QuadroProjetoRepository quadroProjetoRepository,
            ProjetoRepository projetoRepository) {
        this.quadroProjetoRepository = quadroProjetoRepository;
        this.projetoRepository = projetoRepository;
    }

    @Transactional
    public QuadroProjetoResponseDTO salvar(QuadroProjetoRequestDTO request) {

        Projeto projeto = projetoRepository.findById(request.idProjeto())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        QuadroProjeto novoQuadroProjeto = new QuadroProjeto();

        novoQuadroProjeto.setProjeto(projeto);

        QuadroProjeto quadroProjetoSalvo = quadroProjetoRepository.save(novoQuadroProjeto);
        return toResponse(quadroProjetoSalvo);

    }

    @Transactional
    public String deletar(int id) {
        QuadroProjeto quadro = buscarEntidadePorId(id);
        if (!quadro.getAtividades().isEmpty()) {
            throw new IllegalArgumentException("Exclua as atividades antes de excluir o quadro");
        }
        quadroProjetoRepository.delete(quadro);
        return "Quadro do projeto deletado com sucesso";
    }

    @Transactional
    public QuadroProjetoResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public QuadroProjetoResponseDTO atualizarPorId(int id, QuadroProjetoRequestDTO request) {
        QuadroProjeto quadroProjetoAntigo = buscarEntidadePorId(id);

        Projeto destino = projetoRepository.findById(request.idProjeto())
                .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));
        for (Atividade atividade : quadroProjetoAntigo.getAtividades()) {
            for (Desenvolvedor desenvolvedor : atividade.getAtribuidos()) {
                if (desenvolvedor.getEquipe() == null
                        || !desenvolvedor.getEquipe().getId().equals(destino.getEquipe().getId())) {
                    throw new IllegalArgumentException(
                            "Os responsáveis do quadro não pertencem à equipe do projeto de destino");
                }
            }
        }
        quadroProjetoAntigo.setProjeto(destino);

        QuadroProjeto quadroProjetoSalvo = quadroProjetoRepository.save(quadroProjetoAntigo);
        return toResponse(quadroProjetoSalvo);

    }

    @Transactional
    public List<QuadroProjetoResponseDTO> listar() {
        return quadroProjetoRepository.findAll().stream().map(this::toResponse).toList();
    }

    private QuadroProjeto buscarEntidadePorId(int id) {
        return quadroProjetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Quadro do projeto com o id " + id + " não foi encontrado."));
    }

    private QuadroProjetoResponseDTO toResponse(QuadroProjeto quadroProjeto) {
        return new QuadroProjetoResponseDTO(
                quadroProjeto.getId(),
                quadroProjeto.getProjeto(),
                new ArrayList<>(quadroProjeto.getAtividades()));
    }

}
