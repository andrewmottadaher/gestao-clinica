package br.com.gestaoclinica.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsultaTest {

    @Test
    void deveCriarConsultaComDadosValidos() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        LocalDateTime dataHora = LocalDateTime.of(
                2026, 12, 10, 14, 30);

        BigDecimal valor = new BigDecimal("250.00");

        Consulta consulta = new Consulta(
                1,
                paciente,
                medico,
                dataHora,
                valor,
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        assertEquals(1, consulta.getId());
        assertEquals(paciente, consulta.getPaciente());
        assertEquals(medico, consulta.getMedico());
        assertEquals(dataHora, consulta.getDataHoraAgendamento());
        assertEquals(valor, consulta.getValor());
        assertEquals(StatusConsulta.AGENDADA, consulta.getStatus());
        assertEquals(ModalidadeConsulta.PRESENCIAL, consulta.getModalidade());
    }

    @Test
    void deveRejeitarIdZero() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        assertThrows(IllegalArgumentException.class, () -> new Consulta(
                0,
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveRejeitarPacienteNulo() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        assertThrows(IllegalArgumentException.class, () -> new Consulta(
                1,
                null,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveRejeitarMedicoNulo() {
        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        assertThrows(IllegalArgumentException.class, () -> new Consulta(
                1,
                paciente,
                null,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveRejeitarDataHoraAgendamentoNula() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        assertThrows(IllegalArgumentException.class, () -> new Consulta(
                1,
                paciente,
                medico,
                null,
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveRejeitarValorNulo() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        assertThrows(IllegalArgumentException.class, () -> new Consulta(
                1,
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                null,
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL));
    }
}