package br.com.gestaoclinica.service;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsultaServiceTest {

    @Test
    void deveRejeitarAgendamentoQuandoPacienteNaoExiste() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository());

        assertThrows(IllegalArgumentException.class, () -> service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveRejeitarAgendamentoQuandoMedicoNaoExiste() {
        PacienteRepository pacienteRepository = new PacienteRepository();

        pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                pacienteRepository,
                new MedicoRepository());

        assertThrows(IllegalArgumentException.class, () -> service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                ModalidadeConsulta.PRESENCIAL));
    }
}