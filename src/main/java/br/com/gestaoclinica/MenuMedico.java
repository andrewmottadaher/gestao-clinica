package br.com.gestaoclinica;

import java.io.PrintStream;
import java.util.Scanner;

import br.com.gestaoclinica.service.EspecialidadeService;
import br.com.gestaoclinica.service.MedicoService;

public class MenuMedico {

    private final MedicoService service;
    private final EspecialidadeService especialidadeService;
    private final Scanner scanner;
    private final PrintStream output;

    public MenuMedico(
            MedicoService service,
            EspecialidadeService especialidadeService,
            Scanner scanner,
            PrintStream output) {
        this.service = service;
        this.especialidadeService = especialidadeService;
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

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 0 -> output.println("Voltando ao menu principal...");
                default -> output.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }

    private void cadastrar() {
        if (especialidadeService.listarTodos().isEmpty()) {
            output.println("Nao existem especialidades cadastradas.");
            return;
        }

        output.println("\n--- ESPECIALIDADES DISPONIVEIS ---");

        especialidadeService.listarTodos().forEach(especialidade -> output.println(
                especialidade.getId() + " - "
                        + especialidade.getNome()));

        try {
            output.print("Nome: ");
            String nome = scanner.nextLine().trim();

            output.print("CRM: ");
            String crm = scanner.nextLine().trim();

            output.print("E-mail: ");
            String email = scanner.nextLine().trim();

            output.print("Telefone: ");
            String telefone = scanner.nextLine().trim();

            output.print("ID da especialidade: ");
            int especialidadeId = Integer.parseInt(scanner.nextLine().trim());

            service.cadastrar(
                    nome,
                    crm,
                    email,
                    telefone,
                    especialidadeId);

            output.println("Medico cadastrado com sucesso.");

        } catch (NumberFormatException e) {
            output.println("Informe um ID de especialidade valido.");
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }

    private void listar() {
        var medicos = service.listarTodos();

        if (medicos.isEmpty()) {
            output.println("Nenhum medico cadastrado.");
            return;
        }

        output.println("\n--- MEDICOS CADASTRADOS ---");

        medicos.forEach(medico -> {
            output.println("------------------------------");
            output.println("ID: " + medico.getId());
            output.println("Nome: " + medico.getNome());
            output.println("CRM: " + medico.getCrm());
            output.println("Especialidade: " + medico.getEspecialidade().getNome());

            String situacao = "S".equals(medico.getAtivo())
                    ? "Ativo"
                    : "Inativo";

            output.println("Situacao: " + situacao);
        });

        output.println("------------------------------");
    }
}