package com.senac.br.api_projetos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.dto.desenvolvedor.DesenvolvedorRequestDTO;
import com.senac.br.api_projetos.model.Desenvolvedor;
import com.senac.br.api_projetos.repository.DesenvolvedorRepository;

@Service
public class DesenvolvedorService {

    private final DesenvolvedorRepository desenvolvedorRepository;

    public DesenvolvedorService(DesenvolvedorRepository desenvolvedorRepository) {
        this.desenvolvedorRepository = desenvolvedorRepository;
    }

    public String salvar(DesenvolvedorRequestDTO desenvolvedor) {
        Desenvolvedor novoDesenvoveldor = new Desenvolvedor();
        novoDesenvoveldor.setNome(desenvolvedor.nome());
        novoDesenvoveldor.setEmail(desenvolvedor.email());
        novoDesenvoveldor.setSalario(desenvolvedor.salario());
        novoDesenvoveldor.setCargo(desenvolvedor.cargo());
        novoDesenvoveldor.setSenioridade(desenvolvedor.senioridade());

        desenvolvedorRepository.save(novoDesenvoveldor);
        return "Desenvolvedor salvo com sucesso";
    }

    public String deletar(int id) {
        if (desenvolvedorRepository.existsById(id)) {
            desenvolvedorRepository.deleteById(id);
            return "Desenvolvedor deletado com sucesso";
        } else {
            return "Desenvolvedor não encontrado";
        }
    }

    public String listar() {
        List<Desenvolvedor> desenvolvedores = desenvolvedorRepository.findAll();

        if (!desenvolvedores.isEmpty()) {
            StringBuilder sb = new StringBuilder("Lista de Desenvolvedores:\n");
            for (Desenvolvedor desenvolvedor : desenvolvedores) {
                sb.append("Nome: " + desenvolvedor.getNome());
                sb.append("\nEmail: " + desenvolvedor.getEmail());
                sb.append("\nSalario: " + desenvolvedor.getSalario());
                sb.append("\nCargo: " + desenvolvedor.getCargo());
                sb.append("\nSenioridade: " + desenvolvedor.getSenioridade().toString().toLowerCase() + "\n");
            }

            return sb.toString();
        } else {
            return "Sem dados cadastrados...";
        }
    }

    public String buscarPorId(int id) {
        Desenvolvedor desenvolvedorExistente = desenvolvedorRepository.findById(id).get();

        if (desenvolvedorRepository.existsById(id)) {
            return desenvolvedorExistente.toString();
        } else {
            return "Desenvolvedor não encontrado";
        }
    }

    public String atualizarPorId(int id, DesenvolvedorRequestDTO desenvolvedorAtualizado) {
        if (desenvolvedorRepository.existsById(id)) {
            Desenvolvedor devAntigo = new Desenvolvedor();
            devAntigo.setNome(desenvolvedorAtualizado.nome());
            devAntigo.setSalario(desenvolvedorAtualizado.salario());
            devAntigo.setEmail(desenvolvedorAtualizado.email());
            devAntigo.setCargo(desenvolvedorAtualizado.cargo());
            devAntigo.setSenioridade(desenvolvedorAtualizado.senioridade());

            desenvolvedorRepository.save(devAntigo);
            return "Atualização conculida com sucesso";
        } else {
            return "Erro ao atualizar desenvolvedor...";
        }
    }
}
