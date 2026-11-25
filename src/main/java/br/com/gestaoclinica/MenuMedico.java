package br.com.gestaoclinica;

import java.io.PrintStream;
import java.util.Scanner;

import br.com.gestaoclinica.service.MedicoService;

public class MenuMedico {

    private final MedicoService service;
    private final Scanner scanner;
    private final PrintStream output;

    public MenuMedico(
            MedicoService service,
            Scanner scanner,
            PrintStream output) {
        this.service = service;
        this.scanner = scanner;
        this.output = output;
    }

    public void exibir() {
        int opcao;

        do {
            output.println("\n==================================");
            output.println("       GERENCIAR MEDICOS");
            output.println("==================================");
            output.println("1 - Cadastrar medico");
            output.println("2 - Listar medicos");
            output.println("3 - Buscar medico por ID");
            output.println("4 - Atualizar medico");
            output.println("5 - Excluir medico");
            output.println("6 - Inativar medico");
            output.println("7 - Ativar medico");
            output.println("0 - Voltar ao menu principal");
            output.println("==================================");
            output.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            if (opcao == 0) {
                output.println("Voltando ao menu principal...");
            } else if (opcao < 0 || opcao > 7) {
                output.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }
}