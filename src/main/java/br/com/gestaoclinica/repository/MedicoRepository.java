package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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

    public Medico buscarPorId(int id) {
        return medicos.get(id);
    }

    public List<Medico> listarTodos() {
        return new ArrayList<>(medicos.values());
    }

    public boolean existeCrm(String crm) {
        if (crm == null) {
            return false;
        }

        return medicos.values().stream()
                .anyMatch(medico -> medico.getCrm().trim()
                        .equalsIgnoreCase(crm.trim()));
    }

    public boolean existeCrm(String crm, int idIgnorado) {
        return existeCrm(crm);
    }

    public Medico atualizar(
            int id,
            String nome,
            String crm,
            String email,
            String telefone,
            Especialidade especialidade) {

        Medico atual = medicos.get(id);

        if (atual == null) {
            return null;
        }

        Medico atualizado = new Medico(
                id,
                nome,
                crm,
                email,
                telefone,
                especialidade,
                atual.getAtivo());

        medicos.put(id, atualizado);

        return atualizado;
    }
}