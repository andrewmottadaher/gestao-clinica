package br.com.gestaoclinica;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.ProntuarioRepository;
import br.com.gestaoclinica.service.ConsultaService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + medico.getId() + "\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe o ID do paciente:"));
    }

    @Test
    void deveSolicitarIdDoMedicoAposInformarIdDoPaciente() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + medico.getId() + "\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe o ID do medico:"));
    }

    @Test
    void deveSolicitarDataHoraAposInformarIdDoMedico() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + medico.getId() + "\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe a data e hora da consulta:"));
    }

    @Test
    void deveSolicitarValorAposInformarDataHora() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + medico.getId() + "\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe o valor da consulta:"));
    }

    @Test
    void deveSolicitarModalidadeAposInformarValor() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + medico.getId() + "\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

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

    @Test
    void deveAgendarConsultaComModalidadePresencial() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + medico.getId() + "\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        assertTrue(consultaRepository.listarTodos().size() == 1);

        assertTrue(
                consultaRepository.listarTodos()
                        .get(0)
                        .getModalidade() == ModalidadeConsulta.PRESENCIAL);
    }

    @Test
    void deveInformarQuandoIdDoPacienteForInvalido() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + "abc\n"
                        + "1\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("ID do paciente invalido."));
        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveInformarQuandoIdDoMedicoForInvalido() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository(),
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + "1\n"
                        + "abc\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("ID do medico invalido."));
        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveInformarQuandoPacienteNaoForEncontrado() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + "999\n"
                        + "1\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Paciente nao encontrado."));
        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveInformarQuandoMedicoNaoForEncontrado() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + "999\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Medico nao encontrado."));
        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveInformarQuandoMedicoEstiverInativo() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        medicoRepository.inativar(medico.getId());

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + paciente.getId() + "\n"
                        + medico.getId() + "\n"
                        + "26/11/2026 14:30\n"
                        + "150.00\n"
                        + "PRESENCIAL\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains(
                "Nao e possivel agendar consulta com medico inativo."));
        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }
    @Test
    void deveListarConsultasCadastradasPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        service.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDateTime.of(2026, 11, 27, 14, 30),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        Scanner scanner = new Scanner("2\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Joao da Silva"));
        assertTrue(resultado.contains("Carlos Oliveira"));
    }
    @Test
    void deveBuscarConsultaPorIdPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade medica");

        var paciente = pacienteRepository.salvar(
                "Joao da Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                "Particular");

        var medico = medicoRepository.salvar(
                "Carlos Oliveira",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                new ProntuarioRepository());

        service.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDateTime.of(2026, 11, 27, 14, 30),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        Scanner scanner = new Scanner("3\n1\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuConsulta menu = new MenuConsulta(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Joao da Silva"));
        assertTrue(resultado.contains("Carlos Oliveira"));
    }
}