package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PacienteServiceTest {

    @Test
    void deveCadastrarPacienteComDadosValidos() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        Paciente paciente = service.cadastrar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        assertNotNull(paciente);
        assertEquals(1, paciente.getId());
        assertEquals("Joao Silva", paciente.getNome());
        assertEquals("12345678901", paciente.getCpf());
    }
}