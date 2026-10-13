package com.senac.br.api_projetos.service;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.equipe.EquipeRequestDTO;
import com.senac.br.api_projetos.model.Equipe;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;
import com.senac.br.api_projetos.repository.EquipeRepository;
import com.senac.br.api_projetos.repository.GerenteRepository;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final GerenteRepository gerenteRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;

    public EquipeService(EquipeRepository equipeRepository,
            GerenteRepository gerenteRepository,
            DesenvolvedorRepository desenvolvedorRepository) {
        this.equipeRepository = equipeRepository;
        this.gerenteRepository = gerenteRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
    }

    public String salvar(EquipeRequestDTO equipe) {
        Equipe novaEquipe = new Equipe();

        novaEquipe.setGerente(gerenteRepository.findById(equipe.idGerente())
                .orElseThrow(() -> new RuntimeException("Gerente não encontrado")));

        novaEquipe.setDesenvolvedores(desenvolvedorRepository.findAllById(equipe.idDesenvolvedores()));
        equipeRepository.save(novaEquipe);
        System.out.println(novaEquipe);
        return "Equipe salva com sucesso";
    }

    public String deletar(int id) {
        if (equipeRepository.existsById(id)) {
            equipeRepository.deleteById(id);
            return "equipe deletada com sucesso";
        } else {
            return "Não foi possivel deletar equipe, verificar novamente!";
        }
    }

    public String listar() {
        var equipes = equipeRepository.findAll();
        if (equipes == null || equipes.isEmpty()) {
            return "Sem dados cadastrados para mostrar, verifique novamente!";
        } else {
            return equipes.toString();
        }
    }

    public String buscarPorid(int id) {
        Equipe equipeExiste = equipeRepository.findById(id).get();
        if (equipeRepository.existsById(id)) {
            return equipeExiste.toString();
        } else {
            return "equipe nao encontrada, verificar novamente!!!";
        }
    }

    public String atualizarPorId(int id, EquipeRequestDTO equipe) {
        if (equipeRepository.existsById(id)) {
            Equipe equipeAtu = equipeRepository.findById(id).get();
            equipeAtu.setDesenvolvedores(desenvolvedorRepository.findAllById(equipe.idDesenvolvedores()));
            equipeAtu.setGerente(gerenteRepository.findById(equipe.idGerente())
                    .orElseThrow(() -> new RuntimeException("Gerente não encontrado")));
            equipeRepository.save(equipeAtu);
            return "Equipe atualizada com sucesso";
        } else {
            return "Não foi possivel atualizar equipe, verificar novamente!";
        }
    }

}
