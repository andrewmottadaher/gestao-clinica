package br.com.gestaoclinica;

import org.junit.jupiter.api.Test;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.ProntuarioRepository;
import br.com.gestaoclinica.service.ConsultaService;
import br.com.gestaoclinica.service.ProntuarioService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

class MenuProntuarioTest {

    @Test
    void deveExistirClasseMenuProntuario() {
        assertDoesNotThrow(
                () -> Class.forName("br.com.gestaoclinica.MenuProntuario"));
    }

    @Test
    void deveExibirOpcoesDoMenuDeProntuarios() {
        ProntuarioService service = new ProntuarioService(
                new ProntuarioRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        assertDoesNotThrow(() -> {
            Class<?> classe = Class.forName(
                    "br.com.gestaoclinica.MenuProntuario");

            Object menu = classe
                    .getConstructor(
                            ProntuarioService.class,
                            Scanner.class,
                            PrintStream.class)
                    .newInstance(service, scanner, output);

            classe.getMethod("exibir").invoke(menu);
        });

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("GERENCIAR PRONTUARIOS"));
        assertTrue(resultado.contains("1 - Cadastrar prontuario"));
        assertTrue(resultado.contains("2 - Listar prontuarios"));
        assertTrue(resultado.contains("3 - Buscar prontuario por ID"));
        assertTrue(resultado.contains("4 - Atualizar prontuario"));
        assertTrue(resultado.contains("5 - Excluir prontuario"));
        assertTrue(resultado.contains("0 - Voltar ao menu principal"));
    }

    @Test
    void deveEncerrarMenuDeProntuariosAoSelecionarZero() throws Exception {
        ProntuarioService service = new ProntuarioService(
                new ProntuarioRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveInformarQuandoOpcaoForInvalida() {
        ProntuarioService service = new ProntuarioService(
                new ProntuarioRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("9\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(service, scanner, output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Opcao invalida."));
        assertTrue(resultado.contains("Voltando ao menu principal..."));
    }

    @Test
    void deveCadastrarProntuarioPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

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

        ConsultaService consultaService = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                prontuarioRepository);

        var consulta = consultaService.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDate.now().atTime(9, 0),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        consultaService.concluir(consulta.getId());

        ProntuarioService prontuarioService = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        Scanner scanner = new Scanner(
                "1\n"
                        + consulta.getId() + "\n"
                        + "Hipertensao arterial\n"
                        + "Uso de medicacao prescrita\n"
                        + "Retorno em 30 dias\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(
                prontuarioService,
                scanner,
                output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Prontuario cadastrado com sucesso."));
        assertEquals(1, prontuarioRepository.listarTodos().size());
        assertEquals(
                "Hipertensao arterial",
                prontuarioRepository.listarTodos().get(0).getDiagnostico());
    }

    @Test
    void deveListarProntuariosPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

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

        ConsultaService consultaService = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                prontuarioRepository);

        var consulta = consultaService.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDate.now().atTime(9, 0),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        consultaService.concluir(consulta.getId());

        ProntuarioService prontuarioService = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        prontuarioService.cadastrar(
                consulta.getId(),
                "Hipertensao arterial",
                "Uso de medicacao prescrita",
                "Retorno em 30 dias");

        Scanner scanner = new Scanner("2\n0\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(
                prontuarioService,
                scanner,
                output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Hipertensao arterial"));
    }

    @Test
    void deveBuscarProntuarioExistentePorIdPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

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

        ConsultaService consultaService = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                prontuarioRepository);

        var consulta = consultaService.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDate.now().atTime(9, 0),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        consultaService.concluir(consulta.getId());

        ProntuarioService prontuarioService = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        var prontuario = prontuarioService.cadastrar(
                consulta.getId(),
                "Hipertensao arterial",
                "Uso de medicacao prescrita",
                "Retorno em 30 dias");

        Scanner scanner = new Scanner(
                "3\n" + prontuario.getId() + "\n0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(
                prontuarioService,
                scanner,
                output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Hipertensao arterial"));
        assertTrue(resultado.contains(
                "ID da consulta: " + consulta.getId()));
    }

    @Test
    void deveInformarQuandoProntuarioNaoExistirNaBuscaPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        ProntuarioService prontuarioService = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        Scanner scanner = new Scanner("3\n999\n0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(
                prontuarioService,
                scanner,
                output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Prontuario nao encontrado."));
    }

    @Test
    void deveAtualizarProntuarioPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

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

        ConsultaService consultaService = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                prontuarioRepository);

        var consulta = consultaService.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDate.now().atTime(9, 0),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        consultaService.concluir(consulta.getId());

        ProntuarioService prontuarioService = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        var prontuario = prontuarioService.cadastrar(
                consulta.getId(),
                "Hipertensao arterial",
                "Uso de medicacao prescrita",
                "Retorno em 30 dias");

        Scanner scanner = new Scanner(
                "4\n"
                        + prontuario.getId() + "\n"
                        + "Diabetes tipo 2\n"
                        + "Controle glicemico\n"
                        + "Retorno em 15 dias\n"
                        + "0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(
                prontuarioService,
                scanner,
                output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Prontuario atualizado com sucesso."));
        assertEquals(
                "Diabetes tipo 2",
                prontuarioRepository.buscarPorId(prontuario.getId()).getDiagnostico());
        assertEquals(
                "Controle glicemico",
                prontuarioRepository.buscarPorId(prontuario.getId()).getPrescricao());
        assertEquals(
                "Retorno em 15 dias",
                prontuarioRepository.buscarPorId(prontuario.getId()).getObservacoes());
    }

    @Test
    void deveSolicitarIdAntesDeExcluirProntuarioPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

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

        ConsultaService consultaService = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                prontuarioRepository);

        var consulta = consultaService.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDate.now().atTime(9, 0),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        consultaService.concluir(consulta.getId());

        ProntuarioService prontuarioService = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        var prontuario = prontuarioService.cadastrar(
                consulta.getId(),
                "Hipertensao arterial",
                "Uso de medicacao prescrita",
                "Retorno em 30 dias");

        Scanner scanner = new Scanner("5\n0\n0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(
                prontuarioService,
                scanner,
                output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Informe o ID do prontuario:"));
    }

    @Test
    void deveExcluirProntuarioPelaInterface() {
        ConsultaRepository consultaRepository = new ConsultaRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

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

        ConsultaService consultaService = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository,
                prontuarioRepository);

        var consulta = consultaService.agendar(
                paciente.getId(),
                medico.getId(),
                LocalDate.now().atTime(9, 0),
                new BigDecimal("150.00"),
                ModalidadeConsulta.PRESENCIAL);

        consultaService.concluir(consulta.getId());

        ProntuarioService prontuarioService = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        var prontuario = prontuarioService.cadastrar(
                consulta.getId(),
                "Hipertensao arterial",
                "Uso de medicacao prescrita",
                "Retorno em 30 dias");

        Scanner scanner = new Scanner(
                "5\n" + prontuario.getId() + "\n0\n");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(outputStream);

        MenuProntuario menu = new MenuProntuario(
                prontuarioService,
                scanner,
                output);

        menu.exibir();

        String resultado = outputStream.toString();

        assertTrue(resultado.contains("Prontuario excluido com sucesso."));
        assertNull(prontuarioRepository.buscarPorId(prontuario.getId()));
    }
}