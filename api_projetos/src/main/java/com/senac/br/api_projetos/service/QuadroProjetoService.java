package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoRequestDTO;
import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoResponseDTO;
import com.senac.br.api_projetos.model.Atividade;
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

    public String deletar(int id) {
        if (quadroProjetoRepository.existsById(id)) {
            quadroProjetoRepository.deleteById(id);
            return "Quadro do projeto deletado com sucesso";
        } else {
            return "Quadro do projeto não encontrado, verifique novamente!!";
        }
    }

    public QuadroProjetoResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public QuadroProjetoResponseDTO atualizarPorId(int id, QuadroProjetoRequestDTO request) {
        QuadroProjeto quadroProjetoAntigo = buscarEntidadePorId(id);

        quadroProjetoAntigo.setProjeto(projetoRepository.findById(request.idProjeto())
               .orElseThrow(() -> new RuntimeException("Projeto não encontrado")));

        QuadroProjeto quadroProjetoSalvo = quadroProjetoRepository.save(quadroProjetoAntigo);
        return toResponse(quadroProjetoSalvo);

    }

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
                quadroProjeto.getProjeto().getId(),
                quadroProjeto.getAtividades().stream()
                        .map(Atividade::getId)
                        .toList());
    }

}
