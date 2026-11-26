package br.com.gestaoclinica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import br.com.gestaoclinica.model.StatusConsulta;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.service.EspecialidadeService;
import br.com.gestaoclinica.service.MedicoService;

class MenuMedicoTest {

    @Test
    void deveExibirOpcoesDoMenuDeMedicos() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("GERENCIAR MEDICOS"));
        assertTrue(resultado.contains("1 - Cadastrar medico"));
        assertTrue(resultado.contains("2 - Listar medicos"));
        assertTrue(resultado.contains("3 - Buscar medico por ID"));
        assertTrue(resultado.contains("4 - Atualizar medico"));
        assertTrue(resultado.contains("5 - Excluir medico"));
        assertTrue(resultado.contains("6 - Inativar medico"));
        assertTrue(resultado.contains("7 - Ativar medico"));
        assertTrue(resultado.contains("0 - Voltar ao menu principal"));
    }

    @Test
    void deveEncerrarMenuAoSelecionarZero() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Voltando ao menu principal"));
        assertFalse(resultado.contains("Opcao invalida."));
    }

    @Test
    void deveCadastrarMedicoComSucesso() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        especialidadeService.cadastrar("Cardiologia", "Doencas do coracao");

        Scanner scanner = new Scanner(
                "1\n"
                        + "Carlos Silva\n"
                        + "CRM123\n"
                        + "carlos@email.com\n"
                        + "31999999999\n"
                        + "1\n"
                        + "0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        assertTrue(saida.toString(StandardCharsets.UTF_8)
                .contains("Medico cadastrado com sucesso."));

        assertTrue(medicoRepository.buscarPorId(1) != null);
    }

    @Test
    void naoDeveCadastrarMedicoSemEspecialidadesDisponiveis() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Nao existem especialidades cadastradas."));
        assertTrue(medicoRepository.listarTodos().isEmpty());
    }

    @Test
    void naoDeveCadastrarMedicoComCrmDuplicado() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        especialidadeService.cadastrar("Cardiologia", "Doencas do coracao");

        service.cadastrar(
                "Carlos Silva",
                "CRM123",
                "carlos@email.com",
                "31999999999",
                1);

        Scanner scanner = new Scanner(
                "1\n"
                        + "Maria Souza\n"
                        + "CRM123\n"
                        + "maria@email.com\n"
                        + "31888888888\n"
                        + "1\n"
                        + "0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains(
                "Ja existe um medico cadastrado com esse CRM."));

        assertTrue(medicoRepository.listarTodos().size() == 1);
    }

    @Test
    void deveListarMedicosCadastrados() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        especialidadeService.cadastrar("Cardiologia", "Doencas do coracao");

        service.cadastrar(
                "Carlos Silva",
                "CRM123",
                "carlos@email.com",
                "31999999999",
                1);

        Scanner scanner = new Scanner("2\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("ID: 1"));
        assertTrue(resultado.contains("Nome: Carlos Silva"));
        assertTrue(resultado.contains("CRM: CRM123"));
        assertTrue(resultado.contains("Especialidade: Cardiologia"));
        assertTrue(resultado.contains("Situacao: Ativo"));
    }

    @Test
    void deveInformarQuandoNaoExistiremMedicosCadastrados() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("2\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Nenhum medico cadastrado."));
    }

    @Test
    void deveBuscarMedicoPorId() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        especialidadeService.cadastrar("Cardiologia", "Doencas do coracao");

        service.cadastrar(
                "Carlos Silva",
                "CRM123",
                "carlos@email.com",
                "31999999999",
                1);

        Scanner scanner = new Scanner("3\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Nome: Carlos Silva"));
        assertTrue(resultado.contains("CRM: CRM123"));
        assertTrue(resultado.contains("Especialidade: Cardiologia"));
        assertTrue(resultado.contains("Situacao: Ativo"));
    }

    @Test
    void deveInformarQuandoMedicoNaoExistirNaBusca() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("3\n99\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Medico nao encontrado."));
    }

    @Test
    void deveInformarErroQuandoIdDaBuscaNaoForNumerico() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("3\nabc\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe um ID valido."));
    }

    @Test
    void deveIniciarAtualizacaoDeMedico() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("4\n0\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe o ID do medico para atualizar:"));
    }

    @Test
    void deveAtualizarDadosDeMedicoPelaInterface() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        especialidadeService.cadastrar("Cardiologia", "Doencas do coracao");
        especialidadeService.cadastrar("Neurologia", "Doencas do sistema nervoso");

        service.cadastrar(
                "Carlos Silva",
                "CRM123",
                "carlos@email.com",
                "31999999999",
                1);

        Scanner scanner = new Scanner(
                "4\n1\nCarlos Souza\nCRM456\n"
                        + "carlos.souza@email.com\n"
                        + "31888888888\n2\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        Medico medicoAtualizado = service.buscarPorId(1);

        assertEquals("Carlos Souza", medicoAtualizado.getNome());
        assertEquals("CRM456", medicoAtualizado.getCrm());
        assertEquals("carlos.souza@email.com", medicoAtualizado.getEmail());
        assertEquals("31888888888", medicoAtualizado.getTelefone());
        assertEquals("Neurologia", medicoAtualizado.getEspecialidade().getNome());
    }

    @Test
    void deveInformarQuandoMedicoNaoExistirNaAtualizacao() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("4\n99\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Medico nao encontrado."));
        assertFalse(resultado.contains("Nome:"));
    }

    @Test
    void deveInformarErroQuandoIdDaAtualizacaoNaoForNumerico() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("4\nabc\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe um ID valido."));
    }

    @Test
    void deveExcluirMedicoPelaInterface() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        especialidadeService.cadastrar("Cardiologia", "Doencas do coracao");

        service.cadastrar(
                "Carlos Silva",
                "CRM123",
                "carlos@email.com",
                "31999999999",
                1);

        Scanner scanner = new Scanner("5\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        assertNull(service.buscarPorId(1));
    }

    @Test
    void deveIniciarExclusaoDeMedico() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("5\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe o ID do medico para excluir:"));
    }

    @Test
    void deveInformarQuandoMedicoNaoExistirNaExclusao() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("5\n999\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Medico nao encontrado."));
    }

    @Test
    void naoDeveExcluirMedicoComConsultasVinculadas() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();
        PacienteRepository pacienteRepository = new PacienteRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                consultaRepository);

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Especialidade especialidade = especialidadeService.cadastrar(
                "Cardiologia",
                "Especialidade cardiologica");

        service.cadastrar(
                "Carlos Silva",
                "123456",
                "carlos@email.com",
                "31999999999",
                especialidade.getId());

        var medico = medicoRepository.buscarPorId(1);

        var paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31988888888",
                null);

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        Scanner scanner = new Scanner("5\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        assertNotNull(service.buscarPorId(1));
    }

    @Test
    void deveIniciarInativacaoDeMedico() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("6\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe o ID do medico para inativar:"));
    }

    @Test
    void deveInativarMedicoPelaInterface() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Especialidade especialidade = especialidadeService.cadastrar(
                "Cardiologia",
                "Especialidade cardiologica");

        service.cadastrar(
                "Carlos Silva",
                "123456",
                "carlos@email.com",
                "31999999999",
                especialidade.getId());

        Scanner scanner = new Scanner("6\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        assertEquals("N", service.buscarPorId(1).getAtivo());
    }

    @Test
    void deveInformarQuandoMedicoNaoExistirNaInativacao() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("6\n999\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Medico nao encontrado."));
    }

    @Test
    void naoDeveInativarMedicoQueJaEstaInativo() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Especialidade especialidade = especialidadeService.cadastrar(
                "Cardiologia",
                "Especialidade cardiologica");

        service.cadastrar(
                "Carlos Silva",
                "123456",
                "carlos@email.com",
                "31999999999",
                especialidade.getId());

        service.inativar(1);

        Scanner scanner = new Scanner("6\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        assertEquals("N", service.buscarPorId(1).getAtivo());
    }

    @Test
    void deveIniciarAtivacaoDeMedico() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("7\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe o ID do medico para ativar:"));
    }

    @Test
    void deveAtivarMedicoPelaInterface() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Especialidade especialidade = especialidadeService.cadastrar(
                "Cardiologia",
                "Especialidade cardiologica");

        service.cadastrar(
                "Carlos Silva",
                "123456",
                "carlos@email.com",
                "31999999999",
                especialidade.getId());

        service.inativar(1);

        Scanner scanner = new Scanner("7\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        assertEquals("S", service.buscarPorId(1).getAtivo());
    }

    @Test
    void deveInformarQuandoMedicoNaoExistirNaAtivacao() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Scanner scanner = new Scanner("7\n999\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Medico nao encontrado ou ja esta ativo."));
    }

    @Test
    void deveManterMedicoAtivoAoTentarAtivaLoNovamente() {
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        EspecialidadeService especialidadeService = new EspecialidadeService(
                especialidadeRepository,
                medicoRepository);

        Especialidade especialidade = especialidadeService.cadastrar(
                "Cardiologia",
                "Especialidade cardiologica");

        service.cadastrar(
                "Carlos Silva",
                "123456",
                "carlos@email.com",
                "31999999999",
                especialidade.getId());

        Scanner scanner = new Scanner("7\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(
                service,
                especialidadeService,
                scanner,
                output);

        menu.exibir();

        assertEquals("S", service.buscarPorId(1).getAtivo());

        String resultado = saida.toString(StandardCharsets.UTF_8);
        assertTrue(resultado.contains("Medico nao encontrado ou ja esta ativo."));
    }
}