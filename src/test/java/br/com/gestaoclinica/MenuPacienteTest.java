package br.com.gestaoclinica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.StatusConsulta;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
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

    @Test
    void deveCadastrarPacienteSemConvenio() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner(
                "1\n"
                        + "Carlos Souza\n"
                        + "98765432100\n"
                        + "10/08/1980\n"
                        + "M\n"
                        + "31977777777\n"
                        + "\n"
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
        assertEquals(null, service.buscarPorId(1).getConvenio());
    }

    @Test
    void deveListarPacientesPelaInterface() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        service.cadastrar(
                "Maria da Silva",
                "12345678900",
                LocalDate.of(1985, 10, 20),
                "F",
                "31988888888",
                "Unimed");

        Scanner scanner = new Scanner("2\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Maria da Silva"));
        assertTrue(resultado.contains("12345678900"));
        assertTrue(resultado.contains("20/10/1985"));
    }

    @Test
    void deveInformarQuandoNaoExistiremPacientesCadastrados() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("2\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Nenhum paciente cadastrado."));
    }

    @Test
    void deveBuscarPacientePorIdPelaInterface() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        service.cadastrar(
                "Maria da Silva",
                "12345678900",
                LocalDate.of(1985, 10, 20),
                "F",
                "31988888888",
                "Unimed");

        Scanner scanner = new Scanner("3\n1\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Maria da Silva"));
        assertTrue(resultado.contains("12345678900"));
        assertTrue(resultado.contains("20/10/1985"));
    }

    @Test
    void deveInformarQuandoPacienteNaoForEncontradoNaBusca() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("3\n99\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Paciente nao encontrado."));
    }

    @Test
    void deveInformarErroAoBuscarPacienteComIdNaoNumerico() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("3\nabc\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe um ID valido."));
    }

    @Test
    void deveIniciarAtualizacaoDePacientePelaInterface() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("4\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe o ID do paciente:"));
    }

    @Test
    void deveAtualizarPacientePelaInterface() {
        PacienteRepository repository = new PacienteRepository();

        PacienteService service = new PacienteService(
                repository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Scanner scanner = new Scanner(
                "4\n1\nJoao Santos\n12345678900\n15/06/1990\nM\n31888888888\nUnimed\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        Paciente pacienteAtualizado = service.buscarPorId(1);

        assertEquals("Joao Santos", pacienteAtualizado.getNome());
        assertEquals("31888888888", pacienteAtualizado.getTelefone());
        assertEquals("Unimed", pacienteAtualizado.getConvenio());
    }

    @Test
    void deveInformarErroAoAtualizarPacienteInexistente() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("4\n99\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Paciente nao encontrado."));
    }

    @Test
    void deveRejeitarDataInvalidaNaAtualizacaoDePaciente() {
        PacienteRepository repository = new PacienteRepository();

        PacienteService service = new PacienteService(
                repository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Scanner scanner = new Scanner(
                "4\n1\nJoao Santos\n12345678900\n31/02/1990\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);
        Paciente paciente = service.buscarPorId(1);

        assertTrue(resultado.contains(
                "Erro: informe uma data valida no formato dd/MM/aaaa."));
        assertEquals("Joao Silva", paciente.getNome());
    }

    @Test
    void deveRejeitarCpfDuplicadoNaAtualizacaoDePaciente() {
        PacienteRepository repository = new PacienteRepository();

        PacienteService service = new PacienteService(
                repository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        service.cadastrar(
                "Maria Silva",
                "98765432100",
                LocalDate.of(1992, 8, 15),
                "F",
                "31988888888",
                null);

        Scanner scanner = new Scanner(
                "4\n2\nMaria Santos\n12345678900\n15/08/1992\nF\n31988888888\nUnimed\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);
        Paciente paciente = service.buscarPorId(2);

        assertTrue(resultado.contains(
                "Ja existe um paciente cadastrado com esse CPF."));
        assertEquals("Maria Silva", paciente.getNome());
        assertEquals("98765432100", paciente.getCpf());
    }

    @Test
    void deveInformarErroAoAtualizarPacienteComIdNaoNumerico() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("4\nabc\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe um ID valido."));
    }

    @Test
    void deveExcluirPacientePelaInterface() {
        PacienteRepository repository = new PacienteRepository();

        PacienteService service = new PacienteService(
                repository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Scanner scanner = new Scanner("5\n1\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertNull(service.buscarPorId(1));
        assertTrue(resultado.contains("Paciente excluido com sucesso."));
    }

    @Test
    void deveInformarErroAoExcluirPacienteInexistente() {
        PacienteService service = new PacienteService(
                new PacienteRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("5\n99\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Paciente nao encontrado."));
    }

    @Test
    void deveBloquearExclusaoDePacienteComConsultaVinculada() {
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

        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Tratamento do coracao");

        MedicoRepository medicoRepository = new MedicoRepository();

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 11, 26, 10, 0),
                new BigDecimal("200.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        Scanner scanner = new Scanner("5\n1\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuPaciente menu = new MenuPaciente(
                service,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains(
                "Nao e possivel excluir um paciente vinculado a consultas."));
        assertNotNull(service.buscarPorId(paciente.getId()));
    }
}
