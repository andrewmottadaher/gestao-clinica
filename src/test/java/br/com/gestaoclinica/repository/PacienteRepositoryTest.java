package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Paciente;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}