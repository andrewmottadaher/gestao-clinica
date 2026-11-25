package br.com.gestaoclinica.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PacienteTest {

    @Test
    void deveRejeitarIdZero() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                0,
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null));
    }

    @Test
    void deveRejeitarNomeNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                1,
                null,
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null));
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                1,
                "",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null));
    }

    @Test
    void deveRejeitarNomeComApenasEspacos() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                1,
                "   ",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null));
    }

    @Test
    void deveRejeitarCpfNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                1,
                "Joao Silva",
                null,
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null));
    }

    @Test
    void deveRejeitarCpfVazio() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                1,
                "Joao Silva",
                "",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null));
    }

    @Test
    void deveRejeitarCpfComApenasEspacos() {
        assertThrows(IllegalArgumentException.class, () -> new Paciente(
                1,
                "Joao Silva",
                "   ",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null));
    }
}