package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void deveRejeitarCadastroDePacienteComCpfDuplicado() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        service.cadastrar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar(
                        "Maria Souza",
                        "12345678901",
                        LocalDate.of(1992, 8, 15),
                        "F",
                        "31988888888",
                        null));
    }

    @Test
    void deveBuscarPacientePorIdExistente() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        Paciente cadastrado = service.cadastrar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Paciente encontrado = service.buscarPorId(cadastrado.getId());

        assertNotNull(encontrado);
        assertEquals(cadastrado.getId(), encontrado.getId());
        assertEquals("Joao Silva", encontrado.getNome());
    }

    @Test
    void deveRetornarNullAoBuscarPacientePorIdInexistente() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        Paciente encontrado = service.buscarPorId(999);

        assertNull(encontrado);
    }

    @Test
    void deveListarTodosOsPacientesCadastrados() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        service.cadastrar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        service.cadastrar(
                "Maria Souza",
                "98765432100",
                LocalDate.of(1995, 8, 20),
                "F",
                "31988888888",
                "Convenio A");

        List<Paciente> pacientes = service.listarTodos();

        assertNotNull(pacientes);
        assertEquals(2, pacientes.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverPacientesCadastrados() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        List<Paciente> pacientes = service.listarTodos();

        assertNotNull(pacientes);
        assertTrue(pacientes.isEmpty());
    }

    @Test
    void deveAtualizarPacienteExistente() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        Paciente cadastrado = service.cadastrar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Paciente atualizado = service.atualizar(
                cadastrado.getId(),
                "Joao Santos",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31977777777",
                "Convenio A");

        assertNotNull(atualizado);
        assertEquals(cadastrado.getId(), atualizado.getId());
        assertEquals("Joao Santos", atualizado.getNome());
        assertEquals("31977777777", atualizado.getTelefone());
        assertEquals("Convenio A", atualizado.getConvenio());
    }

    @Test
    void deveRejeitarAtualizacaoComCpfDeOutroPaciente() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        Paciente primeiro = service.cadastrar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Paciente segundo = service.cadastrar(
                "Maria Souza",
                "98765432100",
                LocalDate.of(1995, 8, 20),
                "F",
                "31988888888",
                null);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                segundo.getId(),
                "Maria Souza",
                primeiro.getCpf(),
                LocalDate.of(1995, 8, 20),
                "F",
                "31988888888",
                null));
    }

    @Test
    void devePermitirAtualizacaoMantendoProprioCpf() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                consultaRepository);

        Paciente cadastrado = service.cadastrar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Paciente atualizado = service.atualizar(
                cadastrado.getId(),
                "Joao Santos",
                cadastrado.getCpf(),
                LocalDate.of(1990, 5, 10),
                "M",
                "31977777777",
                null);

        assertNotNull(atualizado);
        assertEquals("12345678901", atualizado.getCpf());
        assertEquals("Joao Santos", atualizado.getNome());
    }
}