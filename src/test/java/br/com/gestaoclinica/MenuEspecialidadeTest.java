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
}