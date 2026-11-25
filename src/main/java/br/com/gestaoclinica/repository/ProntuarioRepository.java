package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Prontuario;

import java.util.HashMap;
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
}