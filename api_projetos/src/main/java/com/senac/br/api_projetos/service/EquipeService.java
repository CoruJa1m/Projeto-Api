package com.senac.br.api_projetos.service;

import java.util.ArrayList;
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
import com.senac.br.api_projetos.repository.ProjetoRepository;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final GerenteRepository gerenteRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;
    private final ProjetoRepository projetoRepository;
    private final DesenvolvedorService desenvolvedorService;

    public EquipeService(EquipeRepository equipeRepository, GerenteRepository gerenteRepository,
            DesenvolvedorRepository desenvolvedorRepository, ProjetoRepository projetoRepository,
            DesenvolvedorService desenvolvedorService) {
        this.equipeRepository = equipeRepository;
        this.gerenteRepository = gerenteRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
        this.projetoRepository = projetoRepository;
        this.desenvolvedorService = desenvolvedorService;
    }

    @Transactional
    public EquipeResponseDTO salvar(EquipeRequestDTO request) {
        GerenteProjeto gerente = buscarGerente(request.idGerente());
        validarGerenteDisponivel(gerente, null);
        List<Desenvolvedor> desenvolvedores = desenvolvedorService.buscarTodosPorIds(request.idDesenvolvedores());
        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            validarDesenvolvedorDisponivel(desenvolvedor, null);
        }

        Equipe equipe = new Equipe();
        equipe.setGerente(gerente);
        equipeRepository.save(equipe);
        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            equipe.adicionarDesenvolvedor(desenvolvedor);
        }
        desenvolvedorRepository.saveAll(desenvolvedores);
        return toResponse(equipe);
    }

    @Transactional
    public EquipeResponseDTO atualizarPorId(int id, EquipeRequestDTO request) {
        Equipe equipe = buscarEntidadePorId(id);
        GerenteProjeto gerente = buscarGerente(request.idGerente());
        validarGerenteDisponivel(gerente, equipe);
        List<Desenvolvedor> desenvolvedores = desenvolvedorService.buscarTodosPorIds(request.idDesenvolvedores());

        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            validarDesenvolvedorDisponivel(desenvolvedor, equipe);
        }

        List<Desenvolvedor> desenvolvedoresAntigos = new ArrayList<>(equipe.getDesenvolvedores());
        for (Desenvolvedor desenvolvedor : desenvolvedoresAntigos) {
            if (!desenvolvedores.contains(desenvolvedor) && !desenvolvedor.getAtividades().isEmpty()) {
                throw new IllegalArgumentException(
                        "Remova as atribuições do desenvolvedor antes de retirá-lo da equipe");
            }
        }

        equipe.setGerente(gerente);
        for (Desenvolvedor desenvolvedor : desenvolvedoresAntigos) {
            if (!desenvolvedores.contains(desenvolvedor)) {
                equipe.removerDesenvolvedor(desenvolvedor);
            }
        }
        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            equipe.adicionarDesenvolvedor(desenvolvedor);
        }

        return toResponse(equipeRepository.save(equipe));
    }

    @Transactional
    public EquipeResponseDTO adicionarDesenvolvedor(int id, int idDesenvolvedor) {
        Equipe equipe = buscarEntidadePorId(id);
        Desenvolvedor desenvolvedor = buscarDesenvolvedor(idDesenvolvedor);

        validarDesenvolvedorDisponivel(desenvolvedor, equipe);
        equipe.adicionarDesenvolvedor(desenvolvedor);

        desenvolvedorRepository.save(desenvolvedor);
        return toResponse(equipe);
    }

    @Transactional
    public EquipeResponseDTO removerDesenvolvedor(int id, int idDesenvolvedor) {
        Equipe equipe = buscarEntidadePorId(id);
        Desenvolvedor desenvolvedor = buscarDesenvolvedor(idDesenvolvedor);

        validarSaidaDesenvolvedor(equipe, desenvolvedor);
        equipe.removerDesenvolvedor(desenvolvedor);

        desenvolvedorRepository.save(desenvolvedor);
        return toResponse(equipe);
    }

    @Transactional
    public EquipeResponseDTO transferirDesenvolvedor(int id, int idEquipe, int idDesenvolvedor) {
        Equipe equipe = buscarEntidadePorId(id);
        Equipe novaEquipe = buscarEntidadePorId(idEquipe);
        Desenvolvedor desenvolvedor = buscarDesenvolvedor(idDesenvolvedor);

        if (desenvolvedor.getEquipe() == null || !desenvolvedor.getEquipe().getId().equals(equipe.getId())) {
            throw new IllegalArgumentException("Desenvolvedor não pertence à equipe de origem");
        }

        if (equipe.getId().equals(novaEquipe.getId())) {
            return toResponse(novaEquipe);
        }

        validarSaidaDesenvolvedor(equipe, desenvolvedor);
        equipe.removerDesenvolvedor(desenvolvedor);
        novaEquipe.adicionarDesenvolvedor(desenvolvedor);
        desenvolvedorRepository.save(desenvolvedor);
        return toResponse(novaEquipe);
    }

    @Transactional
    public EquipeResponseDTO alterarGerente(int id, int idGerente) {
        Equipe equipe = buscarEntidadePorId(id);
        GerenteProjeto gerente = buscarGerente(idGerente);
        validarGerenteDisponivel(gerente, equipe);
        equipe.setGerente(gerente);
        return toResponse(equipeRepository.save(equipe));
    }

    @Transactional
    public String deletar(int id) {
        Equipe equipe = buscarEntidadePorId(id);
        if (projetoRepository.existsByEquipe_Id(id)) {
            throw new IllegalArgumentException("Equipe possui projetos associados");
        }

        List<Desenvolvedor> desenvolvedores = new ArrayList<>(equipe.getDesenvolvedores());
        for (Desenvolvedor desenvolvedor : desenvolvedores) {
            if (!desenvolvedor.getAtividades().isEmpty()) {
                throw new IllegalArgumentException("Equipe possui desenvolvedores com atividades atribuídas");
            }
            equipe.removerDesenvolvedor(desenvolvedor);
        }

        desenvolvedorRepository.saveAll(desenvolvedores);
        equipeRepository.delete(equipe);
        return "Equipe deletada com sucesso";
    }

    @Transactional
    public List<EquipeResponseDTO> listar() {
        return equipeRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public EquipeResponseDTO buscarPorid(int id) {
        return toResponse(buscarEntidadePorId(id));
    }

    private void validarGerenteDisponivel(GerenteProjeto gerente, Equipe equipeAtual) {
        if (equipeAtual != null) {
            if (equipeAtual.getGerente().getId().equals(gerente.getId())) {
                return;
            }
        }
        if (equipeRepository.existsByGerente_Id(gerente.getId())) {
            throw new IllegalArgumentException("Gerente já está associado a uma equipe");
        }
    }

    private void validarDesenvolvedorDisponivel(Desenvolvedor desenvolvedor, Equipe equipeAtual) {
        if (desenvolvedor.getEquipe() != null) {
            if (equipeAtual == null) {
                throw new IllegalArgumentException("Desenvolvedor já está associado a outra equipe");
            }
            if (!desenvolvedor.getEquipe().getId().equals(equipeAtual.getId())) {
                throw new IllegalArgumentException("Desenvolvedor já está associado a outra equipe");
            }
        }
    }

    private void validarSaidaDesenvolvedor(Equipe equipe, Desenvolvedor desenvolvedor) {
        if (desenvolvedor.getEquipe() == null || !desenvolvedor.getEquipe().getId().equals(equipe.getId())) {
            throw new IllegalArgumentException("Desenvolvedor não pertence à equipe");
        }
        if (equipe.getDesenvolvedores().size() <= 1) {
            throw new IllegalArgumentException("Equipe precisa manter pelo menos um desenvolvedor");
        }
        if (!desenvolvedor.getAtividades().isEmpty()) {
            throw new IllegalArgumentException("Remova as atribuições do desenvolvedor antes de retirá-lo da equipe");
        }
    }

    private GerenteProjeto buscarGerente(int id) {
        return gerenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gerente não encontrado"));
    }

    private Desenvolvedor buscarDesenvolvedor(int id) {
        return desenvolvedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Desenvolvedor não encontrado"));
    }

    private Equipe buscarEntidadePorId(int id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipe não encontrada"));
    }

    private EquipeResponseDTO toResponse(Equipe equipe) {
        return new EquipeResponseDTO(equipe.getId(), equipe.getGerente().getId(),
                equipe.getDesenvolvedores().stream().map(Desenvolvedor::getId).toList());
    }
}
