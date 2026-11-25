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
import br.com.gestaoclinica.service.MedicoService;

class MenuMedicoTest {

    @Test
    void deveExibirOpcoesDoMenuDeMedicos() {
        MedicoService service = new MedicoService(
                new MedicoRepository(),
                new EspecialidadeRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(service, scanner, output);

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
        MedicoService service = new MedicoService(
                new MedicoRepository(),
                new EspecialidadeRepository(),
                new ConsultaRepository());

        Scanner scanner = new Scanner("0\n");
        ByteArrayOutputStream saida = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(saida, true, StandardCharsets.UTF_8);

        MenuMedico menu = new MenuMedico(service, scanner, output);

        menu.exibir();

        String resultado = saida.toString(StandardCharsets.UTF_8);

        assertTrue(resultado.contains("Voltando ao menu principal"));
        assertFalse(resultado.contains("Opcao invalida."));
    }
}