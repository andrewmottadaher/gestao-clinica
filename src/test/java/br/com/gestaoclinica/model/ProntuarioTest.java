package br.com.gestaoclinica.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProntuarioTest {

    @Test
    void deveCriarProntuarioComDadosValidos() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        Consulta consulta = new Consulta(
                1,
                paciente,
                medico,
                LocalDateTime.of(2026, 11, 25, 10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Prontuario prontuario = new Prontuario(
                1,
                consulta,
                "Hipertensao arterial",
                "Uso de medicacao",
                "Retorno em 30 dias");

        assertEquals(1, prontuario.getId());
        assertEquals(consulta, prontuario.getConsulta());
        assertEquals("Hipertensao arterial", prontuario.getDiagnostico());
        assertEquals("Uso de medicacao", prontuario.getPrescricao());
        assertEquals("Retorno em 30 dias", prontuario.getObservacoes());
        assertEquals(LocalDate.now(), prontuario.getDataRegistro());
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

        Consulta consulta = new Consulta(
                1,
                paciente,
                medico,
                LocalDateTime.of(2026, 11, 25, 10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        assertThrows(IllegalArgumentException.class,
                () -> new Prontuario(
                        0,
                        consulta,
                        "Hipertensao arterial",
                        null,
                        null));
    }

    @Test
    void deveRejeitarConsultaNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new Prontuario(
                        1,
                        null,
                        "Hipertensao arterial",
                        null,
                        null));
    }

    @Test
    void deveRejeitarConsultaNaoRealizada() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        Consulta consulta = new Consulta(
                1,
                paciente,
                medico,
                LocalDateTime.of(2026, 11, 25, 10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        assertThrows(IllegalArgumentException.class,
                () -> new Prontuario(
                        1,
                        consulta,
                        "Hipertensao arterial",
                        null,
                        null));
    }
}