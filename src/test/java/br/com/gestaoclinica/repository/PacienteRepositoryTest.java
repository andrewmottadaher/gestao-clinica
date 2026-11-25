package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Paciente;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
}