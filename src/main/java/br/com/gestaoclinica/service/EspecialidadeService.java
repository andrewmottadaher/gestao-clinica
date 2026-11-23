package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.repository.EspecialidadeRepository;

import java.util.List;

public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository) {
        this.repository = repository;
    }

    public Especialidade cadastrar(String nome, String descricao) {
        if (repository.existeNome(nome)) {
            throw new IllegalArgumentException(
                    "Ja existe uma especialidade cadastrada com esse nome.");
        }

        return repository.salvar(nome, descricao);
    }

    public Especialidade buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public List<Especialidade> listarTodos() {
        return repository.listarTodos();
    }
}