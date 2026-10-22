package com.senac.br.api_projetos.service;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.atividade.AtividadeResponseDTO;
import com.senac.br.api_projetos.model.Atividade;
import com.senac.br.api_projetos.repository.AtividadeRepository;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;
import com.senac.br.api_projetos.repository.QuadroProjetoRepository;

@Service
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;
    private final QuadroProjetoRepository quadroProjetoRepository;

    public AtividadeService(AtividadeRepository atividadeRepository, DesenvolvedorRepository desenvolvedorRepository, QuadroProjetoRepository quadroProjetoRepository) {
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


    public AtividadeResponseDTO salvar(Atividade atividade) {
        Atividade atividadeNova = new Atividade();
        atividadeNova.setDescricao(atividade.getDescricao());
        atividadeNova.setStatus(atividade.getStatus());
        Atividade atividadeSalva = atividadeRepository.save(atividadeNova);
        return toResponse(atividadeSalva);
    }


    public AtividadeResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

   
private  AtividadeResponseDTO toResponse(Atividade atividade) {
    return new AtividadeResponseDTO(
        atividade.getId(),
        atividade.getDescricao(),
        atividade.getStatus(),
        atividade.getQuadroProjeto().getId(),
        atividade.getAtribuidos()
            .stream()
            .map(desenvolvedor -> desenvolvedor.getId())
            .toList()
    );
}


    public Atividade buscarEntidadePorId(int id){
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada"));
    
}


}
