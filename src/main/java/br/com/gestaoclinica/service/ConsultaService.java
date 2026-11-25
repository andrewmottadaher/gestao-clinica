package br.com.gestaoclinica.service;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.StatusConsulta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
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
}