package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Especialidade;

import java.util.HashMap;
import java.util.Map;

public class EspecialidadeRepository {

    private final Map<Integer, Especialidade> especialidades = new HashMap<>();
    private int proximoId = 1;

    public Especialidade salvar(String nome, String descricao) {
        Especialidade especialidade = new Especialidade(
                proximoId, nome, descricao
        );

        especialidades.put(proximoId, especialidade);
        proximoId++;

        return especialidade;
    }
}