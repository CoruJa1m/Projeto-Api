package com.senac.br.api_projetos.service;

import org.springframework.stereotype.Service;

import com.senac.br.api_projetos.model.Projeto;
import com.senac.br.api_projetos.repository.ProjetoRepository;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }


    public String salvar(Projeto projeto) {
        projetoRepository.save(projeto);
        return "Projeto salvo com sucesso";
    }


    public String deletar(int id){
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
            return "Projeto deletado com sucesso";
        }else{
            return "Projeto não encontrado, verifique novamente!!";
        }
    }

    





}
