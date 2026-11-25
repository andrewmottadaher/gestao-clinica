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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    void deveBuscarConsultaPorIdExistente() {
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

        Consulta consultaSalva = repository.salvar(
                paciente, medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        Consulta consultaEncontrada = repository.buscarPorId(consultaSalva.getId());

        assertNotNull(consultaEncontrada);
        assertEquals(consultaSalva, consultaEncontrada);
    }

    @Test
    void deveRetornarNullAoBuscarConsultaPorIdInexistente() {
        ConsultaRepository repository = new ConsultaRepository();

        Consulta consultaEncontrada = repository.buscarPorId(999);

        assertNull(consultaEncontrada);
    }

    @Test
    void deveListarTodasAsConsultasCadastradas() {
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

        repository.salvar(
                paciente, medico,
                LocalDateTime.of(2026, 12, 11, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        assertNotNull(repository.listarTodos());
        assertEquals(2, repository.listarTodos().size());
    }

    @Test
    void devePreservarConsultasAoModificarListaRetornada() {
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

        List<Consulta> consultas = repository.listarTodos();
        consultas.clear();

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void deveAtualizarConsultaExistente() {
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

        Consulta consultaSalva = repository.salvar(
                paciente, medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        Consulta consultaAtualizada = repository.atualizar(
                consultaSalva.getId(),
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 15, 10, 0),
                new BigDecimal("300.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.TELEMEDICINA);

        assertNotNull(consultaAtualizada);
        assertEquals(consultaSalva.getId(), consultaAtualizada.getId());
        assertEquals(
                LocalDateTime.of(2026, 12, 15, 10, 0),
                consultaAtualizada.getDataHoraAgendamento());
        assertEquals(new BigDecimal("300.00"), consultaAtualizada.getValor());
        assertEquals(ModalidadeConsulta.TELEMEDICINA,
                consultaAtualizada.getModalidade());
    }

    @Test
    void deveRetornarNullAoAtualizarConsultaInexistente() {
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

        Consulta consultaAtualizada = repository.atualizar(
                999,
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 15, 10, 0),
                new BigDecimal("300.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.TELEMEDICINA);

        assertNull(consultaAtualizada);
        assertEquals(0, repository.listarTodos().size());
    }
}