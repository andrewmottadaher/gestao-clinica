package br.com.gestaoclinica;

import org.junit.jupiter.api.Test;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.ProntuarioRepository;
import br.com.gestaoclinica.service.ProntuarioService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
}