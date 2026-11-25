package br.com.gestaoclinica;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
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
                case 3 -> buscar();
                case 4 -> atualizar();
                case 5 -> excluir();
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

        medicos.forEach(this::exibirDadosMedico);

        output.println("------------------------------");
    }

    private void buscar() {
        try {
            output.print("Informe o ID do medico: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            var medico = service.buscarPorId(id);

            if (medico == null) {
                output.println("Medico nao encontrado.");
                return;
            }

            exibirDadosMedico(medico);

        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
        }
    }

    private void exibirDadosMedico(br.com.gestaoclinica.model.Medico medico) {
        output.println("------------------------------");
        output.println("ID: " + medico.getId());
        output.println("Nome: " + medico.getNome());
        output.println("CRM: " + medico.getCrm());
        output.println("Especialidade: " + medico.getEspecialidade().getNome());

        String situacao = "S".equals(medico.getAtivo())
                ? "Ativo"
                : "Inativo";

        output.println("Situacao: " + situacao);
        output.println("------------------------------");
    }

    private void atualizar() {
        try {
            output.print("Informe o ID do medico para atualizar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            var medico = service.buscarPorId(id);

            if (medico == null) {
                output.println("Medico nao encontrado.");
                return;
            }

            output.println("\n--- ESPECIALIDADES DISPONIVEIS ---");

            especialidadeService.listarTodos().forEach(especialidade -> output.println(
                    especialidade.getId() + " - "
                            + especialidade.getNome()));

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

            service.atualizar(
                    id,
                    nome,
                    crm,
                    email,
                    telefone,
                    especialidadeId);

            output.println("Medico atualizado com sucesso.");

        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }

    private void excluir() {
        try {
            output.print("Informe o ID do medico para excluir: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            var medico = service.buscarPorId(id);

            if (medico == null) {
                output.println("Medico nao encontrado.");
            }

        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
        }
    }
}