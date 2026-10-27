package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.gerente.GerenteRequestDTO;
import com.senac.br.api_projetos.dto.gerente.GerenteResponseDTO;

import com.senac.br.api_projetos.model.GerenteProjeto;
import com.senac.br.api_projetos.repository.GerenteRepository;
import com.senac.br.api_projetos.repository.EquipeRepository;

@Service
public class GerenteService {

    private final GerenteRepository gerenteRepository;

    private final EquipeRepository equipeRepository;

    public GerenteService(GerenteRepository gerenteRepository, EquipeRepository equipeRepository) {
        this.gerenteRepository = gerenteRepository;
        this.equipeRepository = equipeRepository;
    }

    @Transactional
    public GerenteResponseDTO salvar(GerenteRequestDTO request) {
        GerenteProjeto novoGerente = new GerenteProjeto();

        novoGerente.setSalario(request.salario());
        novoGerente.setEmail(request.email());
        novoGerente.setNome(request.nome());
        novoGerente.setCertificacoes(request.certificacoes());

        GerenteProjeto gerenteSalvo = gerenteRepository.save(novoGerente);

        return toResponse(gerenteSalvo);
    }

    @Transactional
    public String deletar(int id) {
        GerenteProjeto gerente = buscarEntidadePorId(id);
        if (equipeRepository.existsByGerente_Id(id)) {
            throw new IllegalArgumentException("Gerente possui equipe associada");
        }
        gerenteRepository.delete(gerente);
        return "Gerente de projeto deletado com sucesso";
    }

    @Transactional
    public List<GerenteResponseDTO> listar() {
        return gerenteRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public GerenteResponseDTO buscarPorid(int id) {
        return toResponse(buscarEntidadePorId(id));

    }

    @Transactional
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
                gerenteProjeto.getCertificacoes());
    }

    private GerenteProjeto buscarEntidadePorId(int id) {
        return gerenteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Gerente com o id " + id + " não foi encontrado."));
    }
}
