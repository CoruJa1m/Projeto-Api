package com.senac.br.api_projetos.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.desenvolvedor.DesenvolvedorRequestDTO;
import com.senac.br.api_projetos.dto.desenvolvedor.DesenvolvedorResponseDTO;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;

@Service
public class DesenvolvedorService {

    private final DesenvolvedorRepository desenvolvedorRepository;

    public DesenvolvedorService(DesenvolvedorRepository desenvolvedorRepository) {
        this.desenvolvedorRepository = desenvolvedorRepository;
    }

    @Transactional
    public DesenvolvedorResponseDTO salvar(DesenvolvedorRequestDTO request) {
        Desenvolvedor novoDesenvoveldor = new Desenvolvedor();
        novoDesenvoveldor.setNome(request.nome());
        novoDesenvoveldor.setEmail(request.email());
        novoDesenvoveldor.setSalario(request.salario());
        novoDesenvoveldor.setCargo(request.cargo());
        novoDesenvoveldor.setSenioridade(request.senioridade());

        Desenvolvedor devSalvo = desenvolvedorRepository.save(novoDesenvoveldor);

        return toResponse(devSalvo);
    }

    @Transactional
    public String deletar(int id) {
        Desenvolvedor desenvolvedor = buscarEntidadePorId(id);
        if (desenvolvedor.getEquipe() != null || !desenvolvedor.getAtividades().isEmpty()) {
            throw new IllegalArgumentException("Desvincule o desenvolvedor da equipe e das atividades antes de excluí-lo");
        }
        desenvolvedorRepository.delete(desenvolvedor);
        return "Desenvolvedor deletado com sucesso";
    }

    @Transactional
    public List<DesenvolvedorResponseDTO> listar() {
        return desenvolvedorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public DesenvolvedorResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public DesenvolvedorResponseDTO atualizarPorId(int id, DesenvolvedorRequestDTO request) {
        Desenvolvedor devAntigo = buscarEntidadePorId(id);

        devAntigo.setNome(request.nome());
        devAntigo.setSalario(request.salario());
        devAntigo.setEmail(request.email());
        devAntigo.setCargo(request.cargo());
        devAntigo.setSenioridade(request.senioridade());

        Desenvolvedor devSalvo = desenvolvedorRepository.save(devAntigo);

        return toResponse(devSalvo);
    }

    @Transactional
    public List<Desenvolvedor> buscarTodosPorIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Informe pelo menos um desenvolvedor");
        }
        List<Integer> idsVerificados = new ArrayList<>();
        for (Integer id : ids) {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Os IDs dos desenvolvedores devem ser positivos");
            }
            if (idsVerificados.contains(id)) {
                throw new IllegalArgumentException("Não informe desenvolvedores repetidos");
            }
            idsVerificados.add(id);
        }
        List<Desenvolvedor> desenvolvedores = desenvolvedorRepository.findAllById(ids);
        if (desenvolvedores.size() != ids.size()) {
            throw new IllegalArgumentException("Um ou mais desenvolvedores não foram encontrados");
        }
        return desenvolvedores;
    }

    @Transactional
    public List<DesenvolvedorResponseDTO> buscarTodosPorId(List<Integer> ids) {
        return buscarTodosPorIds(ids).stream().map(this::toResponse).toList();
    }

    private DesenvolvedorResponseDTO toResponse(Desenvolvedor desenvolvedor) {
        return new DesenvolvedorResponseDTO(
                desenvolvedor.getId(),
                desenvolvedor.getNome(),
                desenvolvedor.getSalario(),
                desenvolvedor.getEmail(),
                desenvolvedor.getCargo(),
                desenvolvedor.getSenioridade());
    }

    private Desenvolvedor buscarEntidadePorId(int id) {
        return desenvolvedorRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Desenvolvedor com o id " + id + " não foi encontrado."));
    }
}
