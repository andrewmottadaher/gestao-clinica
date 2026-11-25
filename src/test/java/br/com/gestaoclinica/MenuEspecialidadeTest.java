package br.com.gestaoclinica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.service.EspecialidadeService;

class MenuEspecialidadeTest {

    @Test
    void deveExibirOpcoesDoMenuDeEspecialidades() {
        EspecialidadeService service = new EspecialidadeService(
                new EspecialidadeRepository(),
                new MedicoRepository());

        Scanner scanner = new Scanner("0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(
                service,
                scanner,
                output);

        menu.exibir();

        String textoExibido = saida.toString(StandardCharsets.UTF_8);

        assertTrue(textoExibido.contains("Cadastrar especialidade"));
        assertTrue(textoExibido.contains("Listar especialidades"));
        assertTrue(textoExibido.contains("Buscar especialidade por ID"));
        assertTrue(textoExibido.contains("Atualizar especialidade"));
        assertTrue(textoExibido.contains("Excluir especialidade"));
        assertTrue(textoExibido.contains("Voltar ao menu principal"));

        scanner.close();
        output.close();
    }

    @Test
    void deveEncerrarMenuAoSelecionarOpcaoZero() {
        EspecialidadeService service = new EspecialidadeService(
                new EspecialidadeRepository(),
                new MedicoRepository());

        Scanner scanner = new Scanner("0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(
                service,
                scanner,
                output);

        menu.exibir();

        String textoExibido = saida.toString(StandardCharsets.UTF_8);

        assertTrue(textoExibido.contains("Voltando ao menu principal"));
        assertFalse(scanner.hasNextLine());

        scanner.close();
        output.close();
    }

    @Test
    void deveCadastrarEspecialidadePelaOpcaoUm() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        Scanner scanner = new Scanner(
                "1\nCardiologia\nEspecialidade do sistema cardiovascular\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(
                service,
                scanner,
                output);

        menu.exibir();

        String textoExibido = saida.toString(StandardCharsets.UTF_8);

        assertTrue(textoExibido.contains("Nome:"));
        assertTrue(textoExibido.contains("Descricao:"));
        assertTrue(textoExibido.contains("Especialidade cadastrada com sucesso. ID: 1"));
        assertEquals(1, repository.listarTodos().size());
        assertEquals("Cardiologia", repository.buscarPorId(1).getNome());

        scanner.close();
        output.close();
    }

    @Test
    void deveExibirErroAoCadastrarEspecialidadeComNomeVazio() {
        EspecialidadeService service = new EspecialidadeService(
                new EspecialidadeRepository(),
                new MedicoRepository());

        Scanner scanner = new Scanner(
                "1\n\nDescricao de teste\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(
                service,
                scanner,
                output);

        menu.exibir();

        String textoExibido = saida.toString(StandardCharsets.UTF_8);

        assertTrue(textoExibido.contains("Erro:"));
        assertTrue(textoExibido.contains("O nome da especialidade e obrigatorio."));

        scanner.close();
        output.close();
    }

    @Test
    void deveExibirErroAoCadastrarEspecialidadeDuplicada() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        Scanner scanner = new Scanner(
                "1\nCardiologia\nEspecialidade cardiovascular\n"
                        + "1\nCardiologia\nOutra descricao\n"
                        + "0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(
                service,
                scanner,
                output);

        menu.exibir();

        String textoExibido = saida.toString(StandardCharsets.UTF_8);

        assertTrue(textoExibido.contains(
                "Ja existe uma especialidade cadastrada com esse nome."));

        assertEquals(1, repository.listarTodos().size());

        scanner.close();
        output.close();
    }

    @Test
    void deveListarEspecialidadesCadastradas() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        service.cadastrar(
                "Cardiologia",
                "Especialidade do sistema cardiovascular");

        service.cadastrar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        Scanner scanner = new Scanner("2\n0\n");

        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(
                service,
                scanner,
                output);

        menu.exibir();

        String textoExibido = saida.toString(StandardCharsets.UTF_8);

        assertTrue(textoExibido.contains("ID: 1"));
        assertTrue(textoExibido.contains("Cardiologia"));
        assertTrue(textoExibido.contains("Especialidade do sistema cardiovascular"));

        assertTrue(textoExibido.contains("ID: 2"));
        assertTrue(textoExibido.contains("Neurologia"));
        assertTrue(textoExibido.contains("Especialidade do sistema nervoso"));

        scanner.close();
        output.close();
    }

    @Test
    void deveInformarQuandoNaoExistemEspecialidadesCadastradas() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        Scanner scanner = new Scanner("2\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        assertTrue(saida.toString(StandardCharsets.UTF_8)
                .contains("Nenhuma especialidade cadastrada."));
    }

    @Test
    void deveBuscarEspecialidadePorIdPelaInterface() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        service.cadastrar("Cardiologia", "Especialidade do sistema cardiovascular");

        Scanner scanner = new Scanner("3\n1\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Informe o ID da especialidade:"));
        assertTrue(resultado.contains("Nome: Cardiologia"));
        assertTrue(resultado.contains("Descricao: Especialidade do sistema cardiovascular"));
    }

    @Test
    void deveInformarQuandoEspecialidadeNaoForEncontrada() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        Scanner scanner = new Scanner("3\n99\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        assertTrue(saida.toString(StandardCharsets.UTF_8)
                .contains("Especialidade nao encontrada."));
    }

    @Test
    void deveInformarErroQuandoIdInformadoNaoForNumerico() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        Scanner scanner = new Scanner("3\nabc\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        assertTrue(saida.toString(StandardCharsets.UTF_8)
                .contains("Erro: informe um ID numerico valido."));
    }

    @Test
    void deveAtualizarEspecialidadePelaInterface() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        service.cadastrar("Cardiologia", "Descricao antiga");

        Scanner scanner = new Scanner(
                "4\n1\nCardiologia Clinica\nNova descricao\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Especialidade atualizada com sucesso."));
        assertEquals("Cardiologia Clinica", repository.buscarPorId(1).getNome());
        assertEquals("Nova descricao", repository.buscarPorId(1).getDescricao());
    }

    @Test
    void deveInformarQuandoEspecialidadeNaoExistirNaAtualizacao() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        Scanner scanner = new Scanner("4\n99\nCardiologia\nDescricao de teste\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        assertTrue(saida.toString(StandardCharsets.UTF_8)
                .contains("Especialidade nao encontrada."));
    }

    @Test
    void deveExibirErroAoAtualizarEspecialidadeComNomeVazio() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        service.cadastrar("Cardiologia", "Descricao antiga");

        Scanner scanner = new Scanner("4\n1\n\nNova descricao\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        assertTrue(saida.toString(StandardCharsets.UTF_8)
                .contains("O nome da especialidade e obrigatorio."));
    }

    @Test
    void deveExibirErroAoAtualizarEspecialidadeComNomeDuplicado() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(
                repository,
                new MedicoRepository());

        service.cadastrar("Cardiologia", "Descricao da cardiologia");
        service.cadastrar("Neurologia", "Descricao da neurologia");

        Scanner scanner = new Scanner("4\n1\nNeurologia\nNova descricao\n0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuEspecialidade menu = new MenuEspecialidade(service, scanner, output);

        menu.exibir();

        assertTrue(saida.toString(StandardCharsets.UTF_8)
                .contains("Ja existe uma especialidade cadastrada com esse nome."));

        assertEquals("Cardiologia", repository.buscarPorId(1).getNome());
    }
}