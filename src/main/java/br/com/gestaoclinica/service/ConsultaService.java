package br.com.gestaoclinica.service;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.ModalidadeConsulta;

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

        if (pacienteRepository.buscarPorId(pacienteId) == null) {
            throw new IllegalArgumentException(
                    "Paciente nao encontrado.");
        }

        return null;
    }
}