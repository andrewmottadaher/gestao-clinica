package br.com.gestaoclinica;

import java.io.PrintStream;
import java.util.Scanner;

import br.com.gestaoclinica.service.PacienteService;

public class MenuPaciente {

    private final PacienteService service;
    private final Scanner scanner;
    private final PrintStream output;

    public MenuPaciente(
            PacienteService service,
            Scanner scanner,
            PrintStream output) {
        this.service = service;
        this.scanner = scanner;
        this.output = output;
    }

    public void exibir() {
        while (true) {
            output.println("\n=== GERENCIAR PACIENTES ===");
            output.println("1 - Cadastrar paciente");
            output.println("2 - Listar pacientes");
            output.println("3 - Buscar paciente por ID");
            output.println("4 - Atualizar paciente");
            output.println("5 - Excluir paciente");
            output.println("0 - Voltar ao menu principal");
            output.print("Escolha uma opcao: ");

            int opcao = Integer.parseInt(scanner.nextLine().trim());

            if (opcao == 0) {
                output.println("Voltando ao menu principal...");
                return;
            }
        }
    }
}