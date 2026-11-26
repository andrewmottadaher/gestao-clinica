package br.com.gestaoclinica;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

import br.com.gestaoclinica.model.Paciente;
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

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> buscar();
                case 4 -> atualizar();
                case 5 -> excluir();
                case 0 -> {
                    output.println("Voltando ao menu principal...");
                    return;
                }
                default -> output.println("Opcao invalida.");
            }
        }
    }

    private void cadastrar() {
        try {
            output.println("\n=== CADASTRAR PACIENTE ===");

            output.print("Nome: ");
            String nome = scanner.nextLine().trim();

            output.print("CPF: ");
            String cpf = scanner.nextLine().trim();

            output.print("Data de nascimento (dd/MM/yyyy): ");
            String dataTexto = scanner.nextLine().trim();

            DateTimeFormatter formatoData = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            LocalDate dataNascimento = LocalDate.parse(
                    dataTexto,
                    formatoData);

            output.print("Sexo (M/F): ");
            String sexo = scanner.nextLine().trim().toUpperCase();

            output.print("Telefone: ");
            String telefone = scanner.nextLine().trim();

            output.print("Convenio (opcional): ");
            String convenio = scanner.nextLine().trim();

            if (convenio.isBlank()) {
                convenio = null;
            }

            service.cadastrar(
                    nome,
                    cpf,
                    dataNascimento,
                    sexo,
                    telefone,
                    convenio);

            output.println("Paciente cadastrado com sucesso.");

        } catch (DateTimeParseException e) {
            output.println("Erro: informe uma data valida no formato dd/MM/aaaa.");
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }

    private void listar() {
        output.println("\n=== LISTA DE PACIENTES ===");

        var pacientes = service.listarTodos();

        if (pacientes.isEmpty()) {
            output.println("Nenhum paciente cadastrado.");
            return;
        }

        for (Paciente paciente : pacientes) {
            exibirDadosPaciente(paciente);
            output.println("------------------------------");
        }
    }

    private void exibirDadosPaciente(Paciente paciente) {
        output.println("ID: " + paciente.getId());
        output.println("Nome: " + paciente.getNome());
        output.println("CPF: " + paciente.getCpf());
        output.println("Data de nascimento: "
                + paciente.getDataNascimento().format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        output.println("Sexo: " + paciente.getSexo());
        output.println("Telefone: " + paciente.getTelefone());
        output.println("Convenio: "
                + (paciente.getConvenio() == null
                        ? "Nao informado"
                        : paciente.getConvenio()));
    }

    private void buscar() {
        try {
            output.print("Informe o ID do paciente: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Paciente paciente = service.buscarPorId(id);

            if (paciente == null) {
                output.println("Paciente nao encontrado.");
                return;
            }

            exibirDadosPaciente(paciente);

        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
        }
    }

    private void atualizar() {
        try {
            output.print("Informe o ID do paciente: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Paciente paciente = service.buscarPorId(id);

            if (paciente == null) {
                output.println("Paciente nao encontrado.");
                return;
            }

            output.print("Nome: ");
            String nome = scanner.nextLine().trim();

            output.print("CPF: ");
            String cpf = scanner.nextLine().trim();

            output.print("Data de nascimento (dd/MM/yyyy): ");
            String dataTexto = scanner.nextLine().trim();

            DateTimeFormatter formatoData = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            LocalDate dataNascimento = LocalDate.parse(
                    dataTexto,
                    formatoData);

            output.print("Sexo (M/F): ");
            String sexo = scanner.nextLine().trim().toUpperCase();

            output.print("Telefone: ");
            String telefone = scanner.nextLine().trim();

            output.print("Convenio (opcional): ");
            String convenio = scanner.nextLine().trim();

            if (convenio.isBlank()) {
                convenio = null;
            }

            service.atualizar(
                    id,
                    nome,
                    cpf,
                    dataNascimento,
                    sexo,
                    telefone,
                    convenio);

            output.println("Paciente atualizado com sucesso.");

        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
        } catch (DateTimeParseException e) {
            output.println("Erro: informe uma data valida no formato dd/MM/aaaa.");
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }

    private void excluir() {
        try {
            output.print("Informe o ID do paciente: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean excluido = service.excluir(id);

            if (excluido) {
                output.println("Paciente excluido com sucesso.");
            } else {
                output.println("Paciente nao encontrado.");
            }

        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }
}