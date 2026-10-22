package com.senac.br.api_projetos.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoRequestDTO;
import com.senac.br.api_projetos.dto.quadro_projeto.QuadroProjetoResponseDTO;
import com.senac.br.api_projetos.model.Atividade;
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

    public QuadroProjetoService(QuadroProjetoRepository quadroProjetoRepository,
            ProjetoRepository projetoRepository,
            EquipeRepository equipeRepository, AtividadeRepository atividadeRepository) {
        this.quadroProjetoRepository = quadroProjetoRepository;
        this.projetoRepository = projetoRepository;
        this.equipeRepository = equipeRepository;
        this.atividadeRepository = atividadeRepository;
    }

    @Transactional
public QuadroProjetoResponseDTO salvar(QuadroProjetoRequestDTO request) {
    QuadroProjeto novoQuadroProjeto = new QuadroProjeto();

    novoQuadroProjeto.setEquipe(
        equipeRepository.findById(request.idEquipe())
            .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"))
    );

    novoQuadroProjeto.setProjeto(
        projetoRepository.findById(request.idProjeto())
            .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"))
    );

    novoQuadroProjeto.setAtividades(
        atividadeRepository.findAllById(request.idAtividades())
    );

    QuadroProjeto quadroProjetoSalvo = quadroProjetoRepository.save(novoQuadroProjeto);

    quadroProjetoSalvo.getAtividades().forEach(
        atividade -> atividade.setQuadroProjeto(quadroProjetoSalvo)
    );

    atividadeRepository.saveAll(quadroProjetoSalvo.getAtividades());

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
        return toResponse(buscarEntidarPorId(id));
    }

    public QuadroProjetoResponseDTO atualizarPorId(int id, QuadroProjetoRequestDTO request) {
        QuadroProjeto quadroProjetoAntigo = buscarEntidarPorId(id);

        quadroProjetoAntigo.setEquipe(equipeRepository.findById(request.idEquipe())
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada")));

        quadroProjetoAntigo.setProjeto(projetoRepository.findById(request.idProjeto())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado")));

        quadroProjetoAntigo.setAtividades(atividadeRepository.findAllById(request.idAtividades()));

        QuadroProjeto quadroProjetoSalvo = quadroProjetoRepository.save(quadroProjetoAntigo);
        return toResponse(quadroProjetoSalvo);

    }

    public List<QuadroProjetoResponseDTO> listar() {
        return quadroProjetoRepository.findAll().stream().map(this::toResponse).toList();
    }


    public QuadroProjeto buscarEntidarPorId(int id) {
        return quadroProjetoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadro do projeto com o id " + id + " não foi encontrado."));
    }




private QuadroProjetoResponseDTO toResponse(QuadroProjeto quadroProjeto) {
    return new QuadroProjetoResponseDTO(
            quadroProjeto.getProjeto(),
            quadroProjeto.getEquipe(),
            quadroProjeto.getAtividades()
    );
}


}
