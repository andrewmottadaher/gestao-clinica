package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.repository.EspecialidadeRepository;

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
}