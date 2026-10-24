package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senac.br.api_projetos.dto.equipe.EquipeRequestDTO;
import com.senac.br.api_projetos.dto.equipe.EquipeResponseDTO;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.model.GerenteProjeto;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;
import com.senac.br.api_projetos.repository.EquipeRepository;
import com.senac.br.api_projetos.repository.GerenteRepository;



@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final GerenteRepository gerenteRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;

    public EquipeService(EquipeRepository equipeRepository, GerenteRepository gerenteRepository,
            DesenvolvedorRepository desenvolvedorRepository) {
        this.equipeRepository = equipeRepository;
        this.gerenteRepository = gerenteRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
    }

    @Transactional 
    public EquipeResponseDTO salvar(EquipeRequestDTO request) {
        validarGerenteDisponivel(request.idGerente());

        GerenteProjeto gerenteNovo = gerenteRepository.findById(request.idGerente())
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado"));

         List<Desenvolvedor> desenvolvedores = desenvolvedorRepository
        .findAllById(request.idDesenvolvedores());   

         for (var id : request.idDesenvolvedores()) {
        validarDesenvolvedorDisponivel(id);
    }

        Equipe novaEquipe = new Equipe();
        novaEquipe.setGerente(gerenteNovo);
        novaEquipe.setDesenvolvedores(desenvolvedores);

        Equipe equipeSalva = equipeRepository.save(novaEquipe);

        // Associar cada desenvolvedor à equipe salva
        for (var desenvolvedor : desenvolvedores) {
        desenvolvedor.setEquipe(equipeSalva);
        }

// Salvar os desenvolvedores atualizados
        desenvolvedorRepository.saveAll(desenvolvedores);

        return toResponse(equipeSalva); 
    }



    private void validarGerenteDisponivel(Integer idGerente) {
        if (equipeRepository.existsByGerente_Id(idGerente)) {
            throw new IllegalArgumentException("O gerente com a id " + idGerente + " já está associado a uma equipe.");
        }
    }

    private void validarDesenvolvedorDisponivel(Integer idDesenvolvedor) {
        var desenvolvedor = desenvolvedorRepository.findById(idDesenvolvedor)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Desenvolvedor não encontrado"));

        if (desenvolvedor.getEquipe() != null) {
            throw new IllegalArgumentException(
                    "O desenvolvedor com a id " + idDesenvolvedor +
                            " já está associado a uma equipe.");
        }
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
                equipe.getGerente().getId(),
                equipe.getDesenvolvedores().stream()
                        .map(Desenvolvedor::getId)
                        .toList());
    }

    private Equipe buscarEntidadePorId(int id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipe com o id " + id + " não foi encontrado."));
    }

}
