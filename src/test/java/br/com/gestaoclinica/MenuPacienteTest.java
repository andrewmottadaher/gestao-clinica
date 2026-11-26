package br.com.gestaoclinica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.service.PacienteService;

class MenuPacienteTest {

    @Test
    void deveExibirOpcoesDoMenuDePacientes() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("GERENCIAR PACIENTES"));
        assertTrue(resultado.contains("1 - Cadastrar paciente"));
        assertTrue(resultado.contains("2 - Listar pacientes"));
        assertTrue(resultado.contains("3 - Buscar paciente por ID"));
        assertTrue(resultado.contains("4 - Atualizar paciente"));
        assertTrue(resultado.contains("5 - Excluir paciente"));
        assertTrue(resultado.contains("0 - Voltar ao menu principal"));
    }

    @Test
    void deveEncerrarMenuAoSelecionarOpcaoZero() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveCadastrarPacientePelaInterface() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + "Joao da Silva\n"
                        + "12345678900\n"
                        + "15/05/1990\n"
                        + "M\n"
                        + "31999999999\n"
                        + "Unimed\n"
                        + "0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Paciente cadastrado com sucesso."));
        assertEquals("Joao da Silva", service.buscarPorId(1).getNome());
        assertEquals("12345678900", service.buscarPorId(1).getCpf());
        assertEquals(
                LocalDate.of(1990, 5, 15),
                service.buscarPorId(1).getDataNascimento());
    }

    @Test
    void deveInformarErroAoCadastrarPacienteComDataInvalida() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + "Joao da Silva\n"
                        + "12345678900\n"
                        + "31/02/1990\n"
                        + "0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains(
                "Erro: informe uma data valida no formato dd/MM/aaaa."));
    }

    @Test
    void deveInformarErroAoCadastrarPacienteComCpfDuplicado() {
        PacienteRepository pacienteRepository = new PacienteRepository();

        PacienteService service = new PacienteService(
                pacienteRepository,
                new ConsultaRepository());

        service.cadastrar(
                "Maria da Silva",
                "12345678900",
                LocalDate.of(1985, 10, 20),
                "F",
                "31988888888",
                null);

        Scanner scanner = new Scanner(
                "1\n"
                        + "Joao da Silva\n"
                        + "12345678900\n"
                        + "15/05/1990\n"
                        + "M\n"
                        + "31999999999\n"
                        + "Unimed\n"
                        + "0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains(
                "Ja existe um paciente cadastrado com esse CPF."));

        assertEquals(1, service.listarTodos().size());
    }
}
