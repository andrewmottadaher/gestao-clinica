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
        while (true) {
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

            output.print("Escolha uma opcao: ");

            String opcao = scanner.nextLine().trim();
            if (opcao.equals("1")) {
                cadastrarEspecialidade();
            }
            if (opcao.equals("0")) {
                output.println("Voltando ao menu principal...");
                return;
            }
            if (opcao.equals("2")) {
                listarEspecialidades();
            }
            if (opcao.equals("3")) {
                buscarEspecialidadePorId();
            }
            if (opcao.equals("4")) {
                atualizarEspecialidade();
            }
        }
    }

    private void cadastrarEspecialidade() {
        output.print("Nome: ");
        String nome = scanner.nextLine();

        output.print("Descricao: ");
        String descricao = scanner.nextLine();

        try {
            var especialidade = service.cadastrar(nome, descricao);

            output.println(
                    "Especialidade cadastrada com sucesso. ID: "
                            + especialidade.getId());

        } catch (IllegalArgumentException e) {
            output.println("Erro: " + e.getMessage());
        }
    }

    private void listarEspecialidades() {
        var especialidades = service.listarTodos();

        if (especialidades.isEmpty()) {
            output.println("Nenhuma especialidade cadastrada.");
            return;
        }

        output.println("\n--- ESPECIALIDADES CADASTRADAS ---");

        for (var especialidade : especialidades) {
            output.println("ID: " + especialidade.getId());
            output.println("Nome: " + especialidade.getNome());
            output.println("Descricao: " + especialidade.getDescricao());
            output.println("----------------------------------");
        }
    }

    private void buscarEspecialidadePorId() {
        output.print("Informe o ID da especialidade: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());

            var especialidade = service.buscarPorId(id);

            if (especialidade == null) {
                output.println("Especialidade nao encontrada.");
                return;
            }

            output.println("ID: " + especialidade.getId());
            output.println("Nome: " + especialidade.getNome());
            output.println("Descricao: " + especialidade.getDescricao());

        } catch (NumberFormatException e) {
            output.println("Erro: informe um ID numerico valido.");
        } catch (IllegalArgumentException e) {
            output.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarEspecialidade() {
        output.print("Informe o ID da especialidade: ");

        try {
            int id = Integer.parseInt(scanner.nextLine().trim());

            output.print("Novo nome: ");
            String nome = scanner.nextLine();

            output.print("Nova descricao: ");
            String descricao = scanner.nextLine();

            var especialidade = service.atualizar(id, nome, descricao);

            if (especialidade == null) {
                output.println("Especialidade nao encontrada.");
                return;
            }

            output.println("Especialidade atualizada com sucesso.");

        } catch (NumberFormatException e) {
            output.println("Erro: informe um ID numerico valido.");
        } catch (IllegalArgumentException e) {
            output.println("Erro: " + e.getMessage());
        }
    }
}