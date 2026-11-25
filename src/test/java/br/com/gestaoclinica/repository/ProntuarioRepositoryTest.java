package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.Prontuario;
import br.com.gestaoclinica.model.StatusConsulta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProntuarioRepositoryTest {

    @Test
    void deveAtribuirIdAutomaticamenteAoSalvarProntuario() {
        ProntuarioRepository repository = new ProntuarioRepository();

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
                LocalDate.now().atTime(10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Prontuario prontuario = repository.salvar(
                consulta,
                "Hipertensao arterial",
                null,
                null);

        assertNotNull(prontuario);
        assertEquals(1, prontuario.getId());
    }

    @Test
    void deveIncrementarIdAoSalvarSegundoProntuario() {
        ProntuarioRepository repository = new ProntuarioRepository();

        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        Consulta primeiraConsulta = new Consulta(
                1, paciente, medico,
                LocalDate.now().atTime(10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Consulta segundaConsulta = new Consulta(
                2, paciente, medico,
                LocalDate.now().atTime(11, 0),
                new BigDecimal("300.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        repository.salvar(
                primeiraConsulta, "Hipertensao arterial", null, null);

        Prontuario segundoProntuario = repository.salvar(
                segundaConsulta, "Diabetes tipo 2", null, null);

        assertEquals(2, segundoProntuario.getId());
    }

    @Test
    void deveBuscarProntuarioPorIdExistente() {
        ProntuarioRepository repository = new ProntuarioRepository();

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
                1, paciente, medico,
                LocalDate.now().atTime(10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Prontuario prontuarioSalvo = repository.salvar(
                consulta, "Hipertensao arterial", null, null);

        Prontuario prontuarioEncontrado = repository.buscarPorId(prontuarioSalvo.getId());

        assertNotNull(prontuarioEncontrado);
        assertEquals(prontuarioSalvo, prontuarioEncontrado);
    }

    @Test
    void deveRetornarNullAoBuscarProntuarioPorIdInexistente() {
        ProntuarioRepository repository = new ProntuarioRepository();

        Prontuario prontuarioEncontrado = repository.buscarPorId(999);

        assertNull(prontuarioEncontrado);
    }

    @Test
    void deveListarTodosOsProntuariosCadastrados() {
        ProntuarioRepository repository = new ProntuarioRepository();

        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Medico medico = new Medico(
                1, "Carlos", "CRM123", "carlos@email.com",
                "31999999999", especialidade);

        Paciente paciente = new Paciente(
                1, "Joao", "12345678900",
                LocalDate.of(1990, 5, 10),
                "M", "31888888888", null);

        Consulta primeiraConsulta = new Consulta(
                1, paciente, medico,
                LocalDate.now().atTime(10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Consulta segundaConsulta = new Consulta(
                2, paciente, medico,
                LocalDate.now().atTime(11, 0),
                new BigDecimal("300.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        repository.salvar(
                primeiraConsulta, "Hipertensao arterial", null, null);

        repository.salvar(
                segundaConsulta, "Diabetes tipo 2", null, null);

        assertNotNull(repository.listarTodos());
        assertEquals(2, repository.listarTodos().size());
    }

    @Test
    void devePreservarProntuariosAoModificarListaRetornada() {
        ProntuarioRepository repository = new ProntuarioRepository();

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
                1, paciente, medico,
                LocalDate.now().atTime(10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        repository.salvar(
                consulta, "Hipertensao arterial", null, null);

        List<Prontuario> prontuarios = repository.listarTodos();
        prontuarios.clear();

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void deveAtualizarProntuarioExistente() {
        ProntuarioRepository repository = new ProntuarioRepository();

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
                1, paciente, medico,
                LocalDate.now().atTime(10, 0),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Prontuario original = repository.salvar(
                consulta, "Diagnostico inicial", null, null);

        Prontuario atualizado = repository.atualizar(
                original.getId(),
                consulta,
                "Diagnostico atualizado",
                "Prescricao atualizada",
                "Observacoes atualizadas");

        assertNotNull(atualizado);
        assertEquals(original.getId(), atualizado.getId());
        assertEquals("Diagnostico atualizado", atualizado.getDiagnostico());
        assertEquals("Prescricao atualizada", atualizado.getPrescricao());
        assertEquals("Observacoes atualizadas", atualizado.getObservacoes());
        assertEquals(
                "Diagnostico atualizado",
                repository.buscarPorId(original.getId()).getDiagnostico());
    }
}