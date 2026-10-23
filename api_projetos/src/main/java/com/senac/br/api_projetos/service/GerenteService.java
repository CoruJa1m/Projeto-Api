package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.gerente.GerenteRequestDTO;
import com.senac.br.api_projetos.dto.gerente.GerenteResponseDTO;

import com.senac.br.api_projetos.model.GerenteProjeto;
import com.senac.br.api_projetos.repository.GerenteRepository;

@Service
public class GerenteService {

    private final GerenteRepository gerenteRepository;

    public GerenteService(GerenteRepository gerenteRepository) {
        this.gerenteRepository = gerenteRepository;
    }

    public GerenteResponseDTO salvar(GerenteRequestDTO request) {
        GerenteProjeto novoGerente = new GerenteProjeto();

        novoGerente.setSalario(request.salario());
        novoGerente.setEmail(request.email());
        novoGerente.setNome(request.nome());
        novoGerente.setCertificacoes(request.certificacoes());

        GerenteProjeto gerenteSalvo = gerenteRepository.save(novoGerente);

        return toResponse(gerenteSalvo);
    }

    public String deletar(int id) {
        if (gerenteRepository.existsById(id)) {
            gerenteRepository.deleteById(id);
            return "Gerente de projeto deletado com sucesso";
        } else {
            return "Gerente de projeto não encontrado, verifique novamente!";
        }
    }

    public List<GerenteResponseDTO> listar() {
        return gerenteRepository.findAll().stream().map(this::toResponse).toList();
    }

    public GerenteResponseDTO buscarPorid(int id) {
        return toResponse(buscarEntidadePorId(id));

    }

    public GerenteResponseDTO atualizarPorId(int id, GerenteRequestDTO request) {
        GerenteProjeto gerenteAntigo = buscarEntidadePorId(id);

        gerenteAntigo.setNome(request.nome());
        gerenteAntigo.setSalario(request.salario());
        gerenteAntigo.setEmail(request.email());
        gerenteAntigo.setCertificacoes(request.certificacoes());

        GerenteProjeto gerenteSalvo = gerenteRepository.save(gerenteAntigo);

        return toResponse(gerenteSalvo);
    }

    private GerenteResponseDTO toResponse(GerenteProjeto gerenteProjeto) {
        return new GerenteResponseDTO(
            gerenteProjeto.getId(),
            gerenteProjeto.getNome(),
            gerenteProjeto.getSalario(),
            gerenteProjeto.getEmail(),
            gerenteProjeto.getCertificacoes()
        );
    }

    private GerenteProjeto buscarEntidadePorId(int id) {
        return gerenteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Desenvolvedor com o id " + id + " não foi encontrado."));
    }
}
