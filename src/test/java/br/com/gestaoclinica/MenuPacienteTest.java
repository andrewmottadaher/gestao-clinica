package br.com.gestaoclinica;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
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
}
