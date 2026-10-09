package com.senac.br.api_projetos.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.desenvolvedor.GerenteRequestDTO;
import com.senac.br.api_projetos.model.GerenteProjeto;
import com.senac.br.api_projetos.repository.GerenteRepository;

@Service
public class GerenteService {

    private final GerenteRepository gerenteRepository;

    public GerenteService(GerenteRepository gerenteRepository) {
        this.gerenteRepository = gerenteRepository;
    }

    public String salvar(GerenteRequestDTO gerente) {
        GerenteProjeto novoGerente = new GerenteProjeto();

        novoGerente.setSalario(gerente.salario());
        novoGerente.setEmail(gerente.email());
        novoGerente.setNome(gerente.nome());
        novoGerente.setCertificacoes(gerente.certificacoes());
        System.out.println(novoGerente.getSalario());
        gerenteRepository.save(novoGerente);
        return "Gerente de projeto salvo com sucesso";
    }

    public String deletar(int id) {
        if (gerenteRepository.existsById(id)) {
            gerenteRepository.deleteById(id);
            return "Gerente de projeto deletado com sucesso";
        } else {
            return "Gerente de projeto não encontrado, verifique novamente!";
        }
    }

    public String listar() {
        ArrayList<GerenteProjeto> gerentes = (ArrayList<GerenteProjeto>) gerenteRepository.findAll();

        if (!gerentes.isEmpty()) {
            StringBuilder sb = new StringBuilder("Lista de Gerentes de projeto:\n");
            for (GerenteProjeto gerente : gerentes) {
                sb.append("Nome: " + gerente.getNome());
                sb.append("\nEmail: " + gerente.getEmail());
                sb.append("\nSalario: " + gerente.getSalario());
                sb.append("\nCertificações: " + gerente.getCertificacoes() + "\n");
            }

            return sb.toString();
        } else {
            return "Sem dados cadastrados para mostrar, verifique novamente!";
        }
    }

    public String buscarPorid(int id) {
        GerenteProjeto gerenteExiste = gerenteRepository.findById(id).get();
        if (gerenteRepository.existsById(id)) {
            return gerenteExiste.toString();
        } else {
            return "Gerente de projeto não encontrado por id, verificar novamente!!";
        }

    }

    public String atualizarPorId(int id, GerenteRequestDTO gerente) {
        if (gerenteRepository.existsById(id)) {
            GerenteProjeto gerenteAnt = new GerenteProjeto();
            gerenteAnt.setSalario(gerente.salario());
            gerenteAnt.setEmail(gerente.email());
            gerenteAnt.setNome(gerente.nome());
            gerenteAnt.setCertificacoes(gerente.certificacoes());
            gerenteRepository.save(gerenteAnt);
            return "Gerente atualizado com sucesso";
        } else {
            return "houve erro ao tentar atualizar o gerente, verifique novamente";
        }
    }

}
