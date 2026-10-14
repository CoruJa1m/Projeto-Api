package com.senac.br.api_projetos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.equipe.EquipeRequestDTO;
import com.senac.br.api_projetos.dto.equipe.EquipeResponseDTO;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;
import com.senac.br.api_projetos.repository.EquipeRepository;
import com.senac.br.api_projetos.repository.GerenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final GerenteRepository gerenteRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;

    public EquipeResponseDTO salvar(EquipeRequestDTO request) {
        Equipe equipe = new Equipe();

        equipe.setGerente(gerenteRepository.findById(request.idGerente())
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado")));

        equipe.setDesenvolvedores(desenvolvedorRepository.findAllById(request.idDesenvolvedores()));

        if (equipe.getDesenvolvedores().isEmpty()) {
            throw new IllegalArgumentException("Nenhum desenvolvedor cadastrado ou desenvolvedores não encontrados");
        }

        Equipe equipeSalva = equipeRepository.save(equipe);

        return toResponse(equipeSalva);
    }

    public String deletar(int id) {
        if (equipeRepository.existsById(id)) {
            equipeRepository.deleteById(id);
            return "equipe deletada com sucesso";
        } else {
            return "Não foi possivel deletar equipe, verificar novamente!";
        }
    }

    public List<EquipeResponseDTO> listar() {
        return equipeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EquipeResponseDTO buscarPorid(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    public EquipeResponseDTO atualizarPorId(int id, EquipeRequestDTO request) {
        Equipe equipe = buscarEntidadePorId(id);

        equipe.setGerente(gerenteRepository.findById(request.idGerente())
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado")));

        equipe.setDesenvolvedores(desenvolvedorRepository.findAllById(request.idDesenvolvedores()));

        Equipe equipeSalva = equipeRepository.save(equipe);

        return toResponse(equipeSalva);
    }

    private EquipeResponseDTO toResponse(Equipe equipe) {
        return new EquipeResponseDTO(
                equipe.getId(),
                equipe.getGerente(),
                equipe.getDesenvolvedores());
    }

    private Equipe buscarEntidadePorId(int id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
    }

}
