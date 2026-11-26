package br.com.gestaoclinica;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.ProntuarioRepository;
import br.com.gestaoclinica.service.ConsultaService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuConsultaTest {

    @Test
    void deveExibirOpcoesDoMenuDeConsultas() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();
        System.out.println("SAIDA CAPTURADA:");
        System.out.println(resultado);

        assertTrue(resultado.contains("GERENCIAR CONSULTAS"));
        assertTrue(resultado.contains("1 - Agendar consulta"));
        assertTrue(resultado.contains("2 - Listar consultas"));
        assertTrue(resultado.contains("3 - Buscar consulta por ID"));
        assertTrue(resultado.contains("4 - Atualizar consulta"));
        assertTrue(resultado.contains("5 - Cancelar consulta"));
        assertTrue(resultado.contains("6 - Concluir consulta"));
        assertTrue(resultado.contains("7 - Excluir consulta"));
        assertTrue(resultado.contains("0 - Voltar ao menu principal"));
    }

    @Test
    void deveEncerrarMenuDeConsultasAoSelecionarZero() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveInformarQuandoOpcaoForInvalida() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner("9\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Opcao invalida."));
        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveIniciarAgendamentoAoSelecionarOpcaoUm() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n1\n1\n26/11/2026 14:30\n150.00\nPRESENCIAL\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe o ID do paciente:"));
    }

    @Test
    void deveSolicitarIdDoMedicoAposInformarIdDoPaciente() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n1\n1\n26/11/2026 14:30\n150.00\nPRESENCIAL\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe o ID do medico:"));
    }

    @Test
    void deveSolicitarDataHoraAposInformarIdDoMedico() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n1\n1\n26/11/2026 14:30\n150.00\nPRESENCIAL\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe a data e hora da consulta:"));
    }

    @Test
    void deveSolicitarValorAposInformarDataHora() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n1\n1\n26/11/2026 14:30\n150.00\nPRESENCIAL\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe o valor da consulta:"));
    }

    @Test
    void deveSolicitarModalidadeAposInformarValor() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner("1\n1\n1\n26/11/2026 14:30\n150.00\nPRESENCIAL\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe a modalidade da consulta"));
    }

    @Test
    void deveInformarQuandoModalidadeForInvalida() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n1\n1\n26/11/2026 14:30\n150.00\nINVALIDA\n0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Modalidade invalida."));
    }

    @Test
    void deveInformarQuandoDataHoraForInvalida() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n1\n1\nDATA_INVALIDA\n150.00\nPRESENCIAL\n0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Data e hora invalida."));
    }

    @Test
    void deveInformarQuandoValorForInvalido() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n1\n1\n26/11/2026 14:30\nVALOR_INVALIDO\nPRESENCIAL\n0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Valor invalido."));
    }
}