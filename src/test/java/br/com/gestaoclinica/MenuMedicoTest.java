package br.com.gestaoclinica;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
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
}