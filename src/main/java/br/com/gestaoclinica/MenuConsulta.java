package br.com.gestaoclinica;

import br.com.gestaoclinica.service.ConsultaService;

import java.io.PrintStream;
import java.util.Scanner;

public class MenuConsulta {

    private final ConsultaService service;
    private final Scanner scanner;
    private final PrintStream output;

    public MenuConsulta(
            ConsultaService service,
            Scanner scanner,
            PrintStream output) {
        this.service = service;
        this.scanner = scanner;
        this.output = output;
    }

    public void exibir() {
        while (true) {
            output.println();
            output.println("=== GERENCIAR CONSULTAS ===");
            output.println("1 - Agendar consulta");
            output.println("2 - Listar consultas");
            output.println("3 - Buscar consulta por ID");
            output.println("4 - Atualizar consulta");
            output.println("5 - Cancelar consulta");
            output.println("6 - Concluir consulta");
            output.println("7 - Excluir consulta");
            output.println("0 - Voltar ao menu principal");
            output.print("Escolha uma opcao: ");

            String opcao = scanner.nextLine();

            if ("0".equals(opcao)) {
                output.println("Voltando ao menu principal...");
                return;
            }
            if ("1".equals(opcao)) {
                output.print("Informe o ID do paciente: ");
                String pacienteId = scanner.nextLine();

                output.print("Informe o ID do medico: ");
                String medicoId = scanner.nextLine();

                output.println("Informe a data e hora da consulta:");
                continue;
            }
            output.println("Opcao invalida.");
        }
    }
}