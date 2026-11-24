package br.com.gestaoclinica.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PacienteTest {

    @Test
    void deveRejeitarIdZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Paciente(
                        0,
                        "Joao Silva",
                        "12345678901",
                        LocalDate.of(1990, 5, 10),
                        "M",
                        "31999999999",
                        null));
    }
}