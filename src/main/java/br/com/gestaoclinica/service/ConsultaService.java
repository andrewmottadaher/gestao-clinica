package br.com.gestaoclinica.service;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.StatusConsulta;
import br.com.gestaoclinica.repository.ProntuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final ProntuarioRepository prontuarioRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {
        this(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());
    }

    public ConsultaService(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository,
            ProntuarioRepository prontuarioRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.prontuarioRepository = prontuarioRepository;
    }

    public Consulta agendar(
            int pacienteId,
            int medicoId,
            LocalDateTime dataHoraAgendamento,
            BigDecimal valor,
            ModalidadeConsulta modalidade) {

        Paciente paciente = pacienteRepository.buscarPorId(pacienteId);

        if (paciente == null) {
            throw new IllegalArgumentException(
                    "Paciente nao encontrado.");
        }

        Medico medico = medicoRepository.buscarPorId(medicoId);

        if (medico == null) {
            throw new IllegalArgumentException(
                    "Medico nao encontrado.");
        }

        if ("N".equals(medico.getAtivo())) {
            throw new IllegalArgumentException(
                    "Nao e possivel agendar consulta com medico inativo.");
        }

        return consultaRepository.salvar(
                paciente,
                medico,
                dataHoraAgendamento,
                valor,
                StatusConsulta.AGENDADA,
                modalidade);
    }

    public Consulta buscarPorId(int id) {
        return consultaRepository.buscarPorId(id);
    }

    public List<Consulta> listarTodos() {
        return consultaRepository.listarTodos();
    }

    public Consulta atualizar(
            int consultaId,
            int pacienteId,
            int medicoId,
            LocalDateTime dataHoraAgendamento,
            BigDecimal valor,
            ModalidadeConsulta modalidade) {

        Consulta consultaExistente = consultaRepository.buscarPorId(consultaId);

        if (consultaExistente == null) {
            return null;
        }

        if (consultaExistente.getStatus() != StatusConsulta.AGENDADA) {
            throw new IllegalArgumentException(
                    "Nao e possivel atualizar uma consulta que nao esteja agendada.");
        }

        Paciente paciente = pacienteRepository.buscarPorId(pacienteId);

        if (paciente == null) {
            throw new IllegalArgumentException("Paciente nao encontrado.");
        }

        Medico medico = medicoRepository.buscarPorId(medicoId);

        if (medico == null) {
            throw new IllegalArgumentException("Medico nao encontrado.");
        }

        if ("N".equals(medico.getAtivo())) {
            throw new IllegalArgumentException(
                    "Nao e possivel atualizar uma consulta para um medico inativo.");
        }

        return consultaRepository.atualizar(
                consultaId,
                paciente,
                medico,
                dataHoraAgendamento,
                valor,
                consultaExistente.getStatus(),
                modalidade);
    }

    public boolean cancelar(int consultaId) {
        Consulta consultaExistente = consultaRepository.buscarPorId(consultaId);

        if (consultaExistente == null) {
            return false;
        }

        if (consultaExistente.getStatus() != StatusConsulta.AGENDADA) {
            throw new IllegalArgumentException(
                    "Nao e possivel cancelar uma consulta que nao esteja agendada.");
        }

        consultaRepository.atualizar(
                consultaId,
                consultaExistente.getPaciente(),
                consultaExistente.getMedico(),
                consultaExistente.getDataHoraAgendamento(),
                consultaExistente.getValor(),
                StatusConsulta.CANCELADA,
                consultaExistente.getModalidade());

        return true;
    }

    public boolean concluir(int consultaId) {
        Consulta consultaExistente = consultaRepository.buscarPorId(consultaId);

        if (consultaExistente == null) {
            return false;
        }

        if (consultaExistente.getStatus() != StatusConsulta.AGENDADA) {
            throw new IllegalArgumentException(
                    "Nao e possivel concluir uma consulta que nao esteja agendada.");
        }

        consultaRepository.atualizar(
                consultaId,
                consultaExistente.getPaciente(),
                consultaExistente.getMedico(),
                consultaExistente.getDataHoraAgendamento(),
                consultaExistente.getValor(),
                StatusConsulta.REALIZADA,
                consultaExistente.getModalidade());

        return true;
    }

    public boolean excluir(int consultaId) {
        if (prontuarioRepository.existePorConsulta(consultaId)) {
            throw new IllegalArgumentException(
                    "Nao e possivel excluir uma consulta que possui prontuario.");
        }

        return consultaRepository.excluir(consultaId);
    }
}