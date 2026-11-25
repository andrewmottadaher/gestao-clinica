package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.StatusConsulta;
import br.com.gestaoclinica.model.ModalidadeConsulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultaRepository {

    private final Map<Integer, Consulta> consultas = new HashMap<>();
    private int proximoId = 1;

    public Consulta salvar(
            Paciente paciente,
            Medico medico,
            LocalDateTime dataHoraAgendamento,
            BigDecimal valor,
            StatusConsulta status,
            ModalidadeConsulta modalidade) {

        Consulta consulta = new Consulta(
                proximoId,
                paciente,
                medico,
                dataHoraAgendamento,
                valor,
                status,
                modalidade);

        consultas.put(proximoId, consulta);
        proximoId++;

        return consulta;
    }

    public Consulta buscarPorId(int id) {
        return consultas.get(id);
    }

    public List<Consulta> listarTodos() {
        return new ArrayList<>(consultas.values());
    }

    public Consulta atualizar(
            int id,
            Paciente paciente,
            Medico medico,
            LocalDateTime dataHoraAgendamento,
            BigDecimal valor,
            StatusConsulta status,
            ModalidadeConsulta modalidade) {

        if (!consultas.containsKey(id)) {
            return null;
        }

        Consulta consultaAtualizada = new Consulta(
                id,
                paciente,
                medico,
                dataHoraAgendamento,
                valor,
                status,
                modalidade);

        consultas.put(id, consultaAtualizada);

        return consultaAtualizada;
    }
}