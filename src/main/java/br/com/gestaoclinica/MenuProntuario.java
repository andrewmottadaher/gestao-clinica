package br.com.gestaoclinica;

import br.com.gestaoclinica.model.Prontuario;
import br.com.gestaoclinica.service.ProntuarioService;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuProntuario {

    private final ProntuarioService service;
    private final Scanner scanner;
    private final PrintStream output;

    public MenuProntuario(
            ProntuarioService service,
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
            output.println("       GERENCIAR PRONTUARIOS");
            output.println("==================================");
            output.println("1 - Cadastrar prontuario");
            output.println("2 - Listar prontuarios");
            output.println("3 - Buscar prontuario por ID");
            output.println("4 - Atualizar prontuario");
            output.println("5 - Excluir prontuario");
            output.println("0 - Voltar ao menu principal");
            output.println("==================================");
            output.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                output.println("Informe um numero inteiro valido.");
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 0 -> output.println("Voltando ao menu principal...");
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3, 4, 5 ->
                    output.println("Opcao ainda nao implementada.");
                default -> output.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        try {
            output.print("Informe o ID da consulta: ");
            int consultaId = Integer.parseInt(scanner.nextLine().trim());

            output.print("Informe o diagnostico: ");
            String diagnostico = scanner.nextLine();

            output.print("Informe a prescricao: ");
            String prescricao = scanner.nextLine();

            output.print("Informe as observacoes: ");
            String observacoes = scanner.nextLine();

            service.cadastrar(
                    consultaId,
                    diagnostico,
                    prescricao,
                    observacoes);

            output.println("Prontuario cadastrado com sucesso.");

        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }

    private void listar() {
        output.println("\n=== LISTA DE PRONTUARIOS ===");

        var prontuarios = service.listarTodos();

        if (prontuarios.isEmpty()) {
            output.println("Nenhum prontuario cadastrado.");
            return;
        }

        for (Prontuario prontuario : prontuarios) {
            exibirDadosProntuario(prontuario);
            output.println("------------------------------");
        }
    }

    private void exibirDadosProntuario(Prontuario prontuario) {
        output.println("ID: " + prontuario.getId());
        output.println("ID da consulta: " + prontuario.getConsulta().getId());
        output.println("Diagnostico: " + prontuario.getDiagnostico());
        output.println("Prescricao: " + prontuario.getPrescricao());
        output.println("Observacoes: " + prontuario.getObservacoes());
        output.println("Data de registro: "
                + prontuario.getDataRegistro().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
}