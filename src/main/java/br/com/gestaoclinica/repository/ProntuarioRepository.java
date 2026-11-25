package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Prontuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProntuarioRepository {

    private final Map<Integer, Prontuario> prontuarios = new HashMap<>();
    private int proximoId = 1;

    public Prontuario salvar(
            Consulta consulta,
            String diagnostico,
            String prescricao,
            String observacoes) {

        Prontuario prontuario = new Prontuario(
                proximoId,
                consulta,
                diagnostico,
                prescricao,
                observacoes);

        prontuarios.put(proximoId, prontuario);
        proximoId++;

        return prontuario;
    }

    public Prontuario buscarPorId(int id) {
        return prontuarios.get(id);
    }

    public List<Prontuario> listarTodos() {
        return new ArrayList<>(prontuarios.values());
    }

    public Prontuario atualizar(
            int id,
            Consulta consulta,
            String diagnostico,
            String prescricao,
            String observacoes) {

        if (!prontuarios.containsKey(id)) {
            return null;
        }

        Prontuario prontuarioAtualizado = new Prontuario(
                id,
                consulta,
                diagnostico,
                prescricao,
                observacoes);

        prontuarios.put(id, prontuarioAtualizado);

        return prontuarioAtualizado;
    }

    public boolean excluir(int id) {
        return prontuarios.remove(id) != null;
    }

    public boolean existePorConsulta(int consultaId) {
        return prontuarios.values().stream()
                .anyMatch(prontuario -> prontuario.getConsulta().getId() == consultaId);
    }
}