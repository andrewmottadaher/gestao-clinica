package br.com.gestaoclinica;

import java.io.PrintStream;
import java.util.Scanner;

import br.com.gestaoclinica.service.EspecialidadeService;

public class MenuEspecialidade {

    private final EspecialidadeService service;
    private final Scanner scanner;
    private final PrintStream output;

    public MenuEspecialidade(
            EspecialidadeService service,
            Scanner scanner,
            PrintStream output) {
        this.service = service;
        this.scanner = scanner;
        this.output = output;
    }

    public void exibir() {
        output.println("\n==================================");
        output.println("     GERENCIAR ESPECIALIDADES");
        output.println("==================================");
        output.println("1 - Cadastrar especialidade");
        output.println("2 - Listar especialidades");
        output.println("3 - Buscar especialidade por ID");
        output.println("4 - Atualizar especialidade");
        output.println("5 - Excluir especialidade");
        output.println("0 - Voltar ao menu principal");
        output.println("==================================");
    }
}