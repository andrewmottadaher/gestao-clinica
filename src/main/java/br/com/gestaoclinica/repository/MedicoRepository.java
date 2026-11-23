package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;

import java.util.HashMap;
import java.util.Map;

public class MedicoRepository {

    private final Map<Integer, Medico> medicos = new HashMap<>();
    private int proximoId = 1;

    public Medico salvar(
            String nome,
            String crm,
            String email,
            String telefone,
            Especialidade especialidade) {

        Medico medico = new Medico(
                proximoId,
                nome,
                crm,
                email,
                telefone,
                especialidade);

        medicos.put(proximoId, medico);
        proximoId++;

        return medico;
    }
}