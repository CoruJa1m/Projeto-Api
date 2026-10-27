package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.projeto.ProjetoAtualizacaoDTO;
import com.senac.br.api_projetos.dto.projeto.ProjetoRequestDTO;
import com.senac.br.api_projetos.dto.projeto.ProjetoResponseDTO;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.Projeto;
import com.senac.br.api_projetos.model.QuadroProjeto;
import com.senac.br.api_projetos.repository.EquipeRepository;
import com.senac.br.api_projetos.repository.ProjetoRepository;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final EquipeRepository equipeRepository;

    public ProjetoService(ProjetoRepository projetoRepository, EquipeRepository equipeRepository) {
        this.projetoRepository = projetoRepository;
        this.equipeRepository = equipeRepository;
    }

    @Transactional
    public ProjetoResponseDTO salvar(ProjetoRequestDTO request) {

        Equipe novaEquipe = equipeRepository.findById(request.idEquipe())
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

        if (novaEquipe.getDesenvolvedores().isEmpty()) {
            throw new IllegalArgumentException("Equipe precisa ter pelo menos um desenvolvedor");
        }
        Projeto novoProjeto = new Projeto();

        novoProjeto.setNome(request.nome());
        novoProjeto.setPrazo(request.prazo());
        novoProjeto.setEquipe(novaEquipe);
        Projeto projetoSalvo = projetoRepository.save(novoProjeto);
        return toResponse(projetoSalvo);
    }

    @Transactional
    public String deletar(int id) {
        Projeto projeto = buscarEntidadePorId(id);
        if (!projeto.getQuadros().isEmpty()) {
            throw new IllegalArgumentException("Exclua os quadros antes de excluir o projeto");
        }
        projetoRepository.delete(projeto);
        return "Projeto deletado com sucesso";
    }

    @Transactional
    public ProjetoResponseDTO alterarEquipe(int id, int idEquipe) {
        Projeto projeto = buscarEntidadePorId(id);
        Equipe equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));
        if (equipe.getDesenvolvedores().isEmpty()) {
            throw new IllegalArgumentException("Equipe precisa ter pelo menos um desenvolvedor");
        }
        for (QuadroProjeto quadro : projeto.getQuadros()) {
            for (Atividade atividade : quadro.getAtividades()) {
                for (Desenvolvedor desenvolvedor : atividade.getAtribuidos()) {
                    if (desenvolvedor.getEquipe() == null || !desenvolvedor.getEquipe().getId().equals(idEquipe)) {
                        throw new IllegalArgumentException("A nova equipe não contém todos os responsáveis pelas atividades");
                    }
                }
            }
        }
        projeto.setEquipe(equipe);
        return toResponse(projetoRepository.save(projeto));
    }

    @Transactional
    public ProjetoResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public ProjetoResponseDTO atualizarPorId(int id, ProjetoAtualizacaoDTO request) {
        Projeto projetoAntigo = buscarEntidadePorId(id);

        projetoAntigo.setNome(request.nome());
        projetoAntigo.setPrazo(request.prazo());
        Projeto projetoSalvo = projetoRepository.save(projetoAntigo);
        return toResponse(projetoSalvo);

    }

    @Transactional
    public List<ProjetoResponseDTO> listar() {
        return projetoRepository.findAll().stream().map(this::toResponse).toList();
    }

    private ProjetoResponseDTO toResponse(Projeto projeto) {
        return new ProjetoResponseDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getAtivo(),
                projeto.getPrazo(),
                projeto.getEquipe().getId(),
                projeto.getQuadros().stream()
                        .map(QuadroProjeto::getId)
                        .toList());
    }

    private Projeto buscarEntidadePorId(int id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto com o id " + id + " não foi encontrado."));
    }
}
