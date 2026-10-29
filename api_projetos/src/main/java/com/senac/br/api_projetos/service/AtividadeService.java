package com.senac.br.api_projetos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.atividade.AtividadeAtualizacaoDTO;
import com.senac.br.api_projetos.dto.atividade.AtividadeRequestDTO;
import com.senac.br.api_projetos.dto.atividade.AtividadeResponseDTO;
import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.model.QuadroProjeto;
import com.senac.br.api_projetos.model.enums.StatusAtividade;
import com.senac.br.api_projetos.repository.AtividadeRepository;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;
import com.senac.br.api_projetos.repository.QuadroProjetoRepository;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;
    private final QuadroProjetoRepository quadroProjetoRepository;
    private final DesenvolvedorService desenvolvedorService;

    public AtividadeService(AtividadeRepository atividadeRepository, DesenvolvedorRepository desenvolvedorRepository,
            QuadroProjetoRepository quadroProjetoRepository, DesenvolvedorService desenvolvedorService) {
        this.atividadeRepository = atividadeRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
        this.quadroProjetoRepository = quadroProjetoRepository;
        this.desenvolvedorService = desenvolvedorService;
    }

    @Transactional
    public AtividadeResponseDTO salvar(AtividadeRequestDTO request) {
        QuadroProjeto quadro = buscarQuadro(request.idQuadroProjeto());
        List<Desenvolvedor> desenvolvedores = desenvolvedorService.buscarTodosPorIds(request.idDesenvolvedores());
        validarResponsaveisDaEquipe(quadro, desenvolvedores);

        Atividade atividade = new Atividade();
        atividade.setDescricao(request.descricao());
        atividade.setStatus(request.status());
        atividade.setQuadroProjeto(quadro);
        atividadeRepository.save(atividade);
        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            atividade.adicionarDesenvolvedor(desenvolvedor);
        }
        desenvolvedorRepository.saveAll(desenvolvedores);
        return toResponse(atividade);
    }

    @Transactional
    public AtividadeResponseDTO atualizarPorId(int id, AtividadeAtualizacaoDTO request) {
        Atividade atividade = buscarEntidadePorId(id);
        atividade.setDescricao(request.descricao());
        atividade.setStatus(request.status());
        return toResponse(atividadeRepository.save(atividade));
    }

    @Transactional
    public AtividadeResponseDTO atribuirDesenvolvedor(int id, int idDesenvolvedor) {
        Atividade atividade = buscarEntidadePorId(id);
        Desenvolvedor desenvolvedor = buscarDesenvolvedor(idDesenvolvedor);
        List<Desenvolvedor> desenvolvedores = new ArrayList<>();
        desenvolvedores.add(desenvolvedor);
        validarResponsaveisDaEquipe(atividade.getQuadroProjeto(), desenvolvedores);
        atividade.adicionarDesenvolvedor(desenvolvedor);
        desenvolvedorRepository.save(desenvolvedor);
        return toResponse(atividade);
    }

    @Transactional
    public AtividadeResponseDTO removerDesenvolvedor(int id, int idDesenvolvedor) {
        Atividade atividade = buscarEntidadePorId(id);
        Desenvolvedor desenvolvedor = buscarDesenvolvedor(idDesenvolvedor);
        if (!atividade.getAtribuidos().contains(desenvolvedor)) {
            throw new IllegalArgumentException("Desenvolvedor não está atribuído à atividade");
        }
        if (atividade.getAtribuidos().size() <= 1) {
            throw new IllegalArgumentException("Atividade precisa manter pelo menos um responsável");
        }
        atividade.removerDesenvolvedor(desenvolvedor);
        desenvolvedorRepository.save(desenvolvedor);
        return toResponse(atividade);
    }

    @Transactional
    public AtividadeResponseDTO moverParaQuadro(int id, int idQuadro) {
        Atividade atividade = buscarEntidadePorId(id);
        QuadroProjeto quadro = buscarQuadro(idQuadro);
        validarResponsaveisDaEquipe(quadro, atividade.getAtribuidos());
        atividade.setQuadroProjeto(quadro);
        return toResponse(atividadeRepository.save(atividade));
    }

    @Transactional
    public String deletar(int id) {
        Atividade atividade = buscarEntidadePorId(id);
        List<Desenvolvedor> responsaveis = new ArrayList<>(atividade.getAtribuidos());
        for (Desenvolvedor desenvolvedor : responsaveis) {
            atividade.removerDesenvolvedor(desenvolvedor);
        }
        desenvolvedorRepository.saveAll(responsaveis);
        atividadeRepository.delete(atividade);
        return "Atividade deletada com sucesso";
    }

    @Transactional(readOnly = true)
    public AtividadeResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listar() {
        return atividadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarPorProjeto(int id) {
        return atividadeRepository.findAll()
                .stream()
                .filter(atividade -> atividade.getQuadroProjeto()
                        .getProjeto()
                        .getId()
                        .equals(id))
                .map(this::toResponse)
                .toList();

    }

    @Transactional
    public AtividadeResponseDTO finalizar(int id) {
        Atividade atividade = buscarEntidadePorId(id);

        atividade.setStatus(StatusAtividade.CONCLUIDO);

        Atividade atividadeSalva = atividadeRepository.save(atividade);
        return toResponse(atividadeSalva);
    }

    private void validarResponsaveisDaEquipe(QuadroProjeto quadro, List<Desenvolvedor> desenvolvedores) {
        if (desenvolvedores.isEmpty()) {
            throw new IllegalArgumentException("Informe pelo menos um desenvolvedor existente");
        }
        Equipe equipe = quadro.getProjeto().getEquipe();
        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            if (desenvolvedor.getEquipe() == null || !desenvolvedor.getEquipe().getId().equals(equipe.getId())) {
                throw new IllegalArgumentException("Desenvolvedor não pertence à equipe do projeto");
            }
        }
    }

    private Desenvolvedor buscarDesenvolvedor(int id) {
        return desenvolvedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Desenvolvedor não encontrado"));
    }

    private QuadroProjeto buscarQuadro(int id) {
        return quadroProjetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadro de projeto não encontrado"));
    }

    private Atividade buscarEntidadePorId(int id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada"));
    }

    private AtividadeResponseDTO toResponse(Atividade atividade) {
        return new AtividadeResponseDTO(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getStatus(),
                atividade.getQuadroProjeto(),
                new ArrayList<>(atividade.getAtribuidos()));
    }
}
