package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.projeto.ProjetoRequestDTO;
import com.senac.br.api_projetos.dto.projeto.ProjetoResponseDTO;
import com.senac.br.api_projetos.model.Projeto;
import com.senac.br.api_projetos.model.QuadroProjeto;
import com.senac.br.api_projetos.repository.ProjetoRepository;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public ProjetoResponseDTO salvar(ProjetoRequestDTO request) {
        Projeto novoProjeto = new Projeto();

        novoProjeto.setNome(request.nome());
        novoProjeto.setPrazo(request.prazo());

        Projeto projetoSalvo = projetoRepository.save(novoProjeto);
        return toResponse(projetoSalvo);
    }

    public String deletar(int id) {
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
            return "Projeto deletado com sucesso";
        } else {
            return "Projeto não encontrado, verifique novamente!!";
        }
    }

    public ProjetoResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidarPorId(id));
    }

    public ProjetoResponseDTO atualizarPorId(int id, ProjetoRequestDTO request) {
        Projeto projetoAntigo = buscarEntidarPorId(id);

        projetoAntigo.setNome(request.nome());
        projetoAntigo.setPrazo(request.prazo());
        Projeto projetoSalvo = projetoRepository.save(projetoAntigo);
        return toResponse(projetoSalvo);

    }

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

    private Projeto buscarEntidarPorId(int id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Projeto com o id " + id + " não foi encontrado."));
    }
}
