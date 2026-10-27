package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.atividade.AtividadeRequestDTO;
import com.senac.br.api_projetos.dto.atividade.AtividadeResponseDTO;
import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.model.QuadroProjeto;
import com.senac.br.api_projetos.repository.AtividadeRepository;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;
import com.senac.br.api_projetos.repository.QuadroProjetoRepository;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;
    private final QuadroProjetoRepository quadroProjetoRepository;

    public AtividadeService(AtividadeRepository atividadeRepository, DesenvolvedorRepository desenvolvedorRepository,
            QuadroProjetoRepository quadroProjetoRepository) {
        this.atividadeRepository = atividadeRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
        this.quadroProjetoRepository = quadroProjetoRepository;
    }

    public String deletar(int id) {
        if (atividadeRepository.existsById(id)) {
            atividadeRepository.deleteById(id);
            return "Atividade deletada com sucesso";
        } else {
            return "Atividade não encontrada, verifique novamente!!";
        }
    }

    public AtividadeResponseDTO salvar(AtividadeRequestDTO request) {
      QuadroProjeto quadro = quadroProjetoRepository.findById(request.idQuadroProjeto())
                .orElseThrow(() -> new IllegalArgumentException("Quadro de projeto não encontrado, favor verificar"));

    List<Desenvolvedor> desenvolvedores = desenvolvedorRepository.findAllById(request.idDesenvolvedores());


    validarResponsaveisDaEquipe(quadro, desenvolvedores);

        Atividade novaAtividade = new Atividade();
        novaAtividade.setDescricao(request.descricao());
        novaAtividade.setStatus(request.status());
        novaAtividade.setQuadroProjeto(quadro);
        novaAtividade.setAtribuidos(desenvolvedores);

        Atividade atividadeSalva = atividadeRepository.save(novaAtividade);

        return toResponse(atividadeSalva);
    }

private void validarResponsaveisDaEquipe(
        QuadroProjeto quadro,
        List<Desenvolvedor> desenvolvedores) {

    Equipe equipe = quadro.getProjeto().getEquipe();

    for (Desenvolvedor desenvolvedor : desenvolvedores) {

        if (desenvolvedor.getEquipe() == null) {
            throw new IllegalArgumentException(
                    "Desenvolvedor não pertence a uma equipe");
        }

        if (!desenvolvedor.getEquipe().getId().equals(equipe.getId())) {
            throw new IllegalArgumentException(
                    "Desenvolvedor não pertence à equipe do projeto");
        }
    }
}

    public AtividadeResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    private AtividadeResponseDTO toResponse(Atividade atividade) {
        return new AtividadeResponseDTO(
                atividade.getId(),
                atividade.getDescricao(),
                atividade.getStatus(),
                atividade.getQuadroProjeto().getId(),
                atividade.getAtribuidos()
                        .stream()
                        .map(desenvolvedor -> desenvolvedor.getId())
                        .toList());
    }

    private Atividade buscarEntidadePorId(int id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada"));

    }

}
