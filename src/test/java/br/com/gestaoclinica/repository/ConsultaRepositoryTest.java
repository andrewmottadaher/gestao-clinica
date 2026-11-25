package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.StatusConsulta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsultaRepositoryTest {

    @Test
    void deveAtribuirIdAutomaticamenteAoSalvarConsulta() {
        ConsultaRepository repository = new ConsultaRepository();

        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        Consulta consulta = repository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        assertNotNull(consulta);
        assertEquals(1, consulta.getId());
    }

    @Test
    void deveIncrementarIdAoSalvarSegundaConsulta() {
        ConsultaRepository repository = new ConsultaRepository();

        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        repository.salvar(
                paciente, medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        Consulta segundaConsulta = repository.salvar(
                paciente, medico,
                LocalDateTime.of(2026, 12, 11, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        assertEquals(2, segundaConsulta.getId());
    }
}