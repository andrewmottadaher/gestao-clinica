package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Paciente;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PacienteRepositoryTest {

    @Test
    void deveAtribuirIdAutomaticamenteAoSalvarPaciente() {
        PacienteRepository repository = new PacienteRepository();

        Paciente paciente = repository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        assertEquals(1, paciente.getId());
    }

    @Test
    void deveIncrementarIdAoSalvarMultiplosPacientes() {
        PacienteRepository repository = new PacienteRepository();

        Paciente primeiro = repository.salvar(
                "Joao Silva", "12345678901",
                LocalDate.of(1990, 5, 10), "M",
                "31999999999", null);

        Paciente segundo = repository.salvar(
                "Maria Silva", "98765432100",
                LocalDate.of(1992, 8, 15), "F",
                "31988888888", null);

        Paciente terceiro = repository.salvar(
                "Pedro Souza", "11122233344",
                LocalDate.of(1985, 3, 20), "M",
                "31977777777", null);

        assertAll(
                () -> assertEquals(1, primeiro.getId()),
                () -> assertEquals(2, segundo.getId()),
                () -> assertEquals(3, terceiro.getId()));
    }

    @Test
    void deveBuscarPacientePorId() {
        PacienteRepository repository = new PacienteRepository();

        Paciente salvo = repository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Paciente encontrado = repository.buscarPorId(salvo.getId());

        assertEquals(salvo, encontrado);
    }

    @Test
    void deveRetornarNullAoBuscarPacienteInexistente() {
        PacienteRepository repository = new PacienteRepository();

        Paciente encontrado = repository.buscarPorId(99);

        assertEquals(null, encontrado);
    }

    @Test
    void deveListarTodosOsPacientesCadastrados() {
        PacienteRepository repository = new PacienteRepository();

        Paciente primeiro = repository.salvar(
                "Joao Silva", "12345678901",
                LocalDate.of(1990, 5, 10), "M",
                "31999999999", null);

        Paciente segundo = repository.salvar(
                "Maria Silva", "98765432100",
                LocalDate.of(1992, 8, 15), "F",
                "31988888888", null);

        List<Paciente> pacientes = repository.listarTodos();

        assertEquals(2, pacientes.size());
        assertTrue(pacientes.contains(primeiro));
        assertTrue(pacientes.contains(segundo));
    }

    @Test
    void devePreservarPacientesArmazenadosAoModificarListaRetornada() {
        PacienteRepository repository = new PacienteRepository();

        repository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        List<Paciente> pacientes = repository.listarTodos();

        pacientes.clear();

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void deveExcluirPacienteExistente() {
        PacienteRepository repository = new PacienteRepository();

        Paciente paciente = repository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        boolean excluido = repository.excluir(paciente.getId());

        assertTrue(excluido);
        assertNull(repository.buscarPorId(paciente.getId()));
    }

    @Test
    void deveRetornarFalseAoExcluirPacienteInexistente() {
        PacienteRepository repository = new PacienteRepository();

        assertFalse(repository.excluir(99));
    }
}