package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public String deletar(int id) {
        if (desenvolvedorRepository.existsById(id)) {
            desenvolvedorRepository.deleteById(id);
            return "Desenvolvedor deletado com sucesso";
        } else {
            return "Desenvolvedor não encontrado";
        }
    }

    public List<DesenvolvedorResponseDTO> listar() {
        return desenvolvedorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public DesenvolvedorResponseDTO buscarPorId(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

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

    private DesenvolvedorResponseDTO toResponse(Desenvolvedor desenvolvedor) {
        return new DesenvolvedorResponseDTO(
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
