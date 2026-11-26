package br.com.gestaoclinica;

import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.service.ConsultaService;
import br.com.gestaoclinica.model.Consulta;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

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
            if ("4".equals(opcao)) {
                atualizar();
                continue;
            }
            if ("3".equals(opcao)) {
                buscar();
                continue;
            }
            if ("2".equals(opcao)) {
                listar();
                continue;
            }
            if ("1".equals(opcao)) {
                output.print("Informe o ID do paciente: ");
                String pacienteId = scanner.nextLine();

                output.print("Informe o ID do medico: ");
                String medicoId = scanner.nextLine();

                output.print("Informe a data e hora da consulta: ");

                LocalDateTime dataHora;

                try {
                    DateTimeFormatter formato = DateTimeFormatter
                            .ofPattern("dd/MM/uuuu HH:mm")
                            .withResolverStyle(ResolverStyle.STRICT);

                    dataHora = LocalDateTime.parse(scanner.nextLine(), formato);

                } catch (DateTimeParseException e) {
                    output.println("Data e hora invalida.");
                    continue;
                }

                output.print("Informe o valor da consulta: ");

                BigDecimal valor;

                try {
                    valor = new BigDecimal(scanner.nextLine().replace(",", "."));

                    if (valor.compareTo(BigDecimal.ZERO) < 0) {
                        throw new NumberFormatException();
                    }

                } catch (NumberFormatException e) {
                    output.println("Valor invalido.");
                    continue;
                }

                output.println("Informe a modalidade da consulta (PRESENCIAL ou TELEMEDICINA):");
                String modalidade = scanner.nextLine();

                if (!"PRESENCIAL".equalsIgnoreCase(modalidade)
                        && !"TELEMEDICINA".equalsIgnoreCase(modalidade)) {
                    output.println("Modalidade invalida.");
                    continue;
                }

                int idPaciente;
                int idMedico;

                try {
                    idPaciente = Integer.parseInt(pacienteId);
                } catch (NumberFormatException e) {
                    output.println("ID do paciente invalido.");
                    continue;
                }

                try {
                    idMedico = Integer.parseInt(medicoId);
                } catch (NumberFormatException e) {
                    output.println("ID do medico invalido.");
                    continue;
                }

                ModalidadeConsulta modalidadeConsulta = ModalidadeConsulta.valueOf(modalidade.toUpperCase());

                try {
                    service.agendar(
                            idPaciente,
                            idMedico,
                            dataHora,
                            valor,
                            modalidadeConsulta);

                    output.println("Consulta agendada com sucesso.");

                } catch (IllegalArgumentException e) {
                    output.println(e.getMessage());
                }
            }
            output.println("Opcao invalida.");
        }
    }

    private void listar() {
        var consultas = service.listarTodos();

        if (consultas.isEmpty()) {
            output.println("Nenhuma consulta cadastrada.");
            return;
        }

        output.println("\n--- CONSULTAS CADASTRADAS ---");

        for (Consulta consulta : consultas) {
            output.println("ID: " + consulta.getId());
            output.println("Paciente: " + consulta.getPaciente().getNome());
            output.println("Medico: " + consulta.getMedico().getNome());
            output.println("Data e hora: " + consulta.getDataHoraAgendamento());
            output.println("Valor: " + consulta.getValor());
            output.println("Status: " + consulta.getStatus());
            output.println("Modalidade: " + consulta.getModalidade());
            output.println("----------------------------------");
        }
    }

    private void buscar() {
        output.print("Informe o ID da consulta: ");

        int id;

        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            output.println("Informe um ID valido.");
            return;
        }

        Consulta consulta = service.buscarPorId(id);

        if (consulta == null) {
            output.println("Consulta nao encontrada.");
            return;
        }

        output.println("ID: " + consulta.getId());
        output.println("Paciente: " + consulta.getPaciente().getNome());
        output.println("Medico: " + consulta.getMedico().getNome());
        output.println("Data e hora: " + consulta.getDataHoraAgendamento());
        output.println("Valor: " + consulta.getValor());
        output.println("Status: " + consulta.getStatus());
        output.println("Modalidade: " + consulta.getModalidade());
    }

    private void atualizar() {
        output.print("Informe o ID da consulta: ");

        int consultaId;

        try {
            consultaId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            output.println("ID da consulta invalido.");
            return;
        }

        Consulta consultaExistente = service.buscarPorId(consultaId);

        if (consultaExistente == null) {
            output.println("Consulta nao encontrada.");
            return;
        }

        output.print("Informe o ID do paciente: ");
        int pacienteId;

        try {
            pacienteId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            output.println("ID do paciente invalido.");
            return;
        }

        output.print("Informe o ID do medico: ");
        int medicoId;

        try {
            medicoId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            output.println("ID do medico invalido.");
            return;
        }

        output.print("Informe a nova data e hora da consulta: ");

        LocalDateTime dataHora;

        try {
            DateTimeFormatter formato = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);

            dataHora = LocalDateTime.parse(scanner.nextLine(), formato);
        } catch (DateTimeParseException e) {
            output.println("Data e hora invalida.");
            return;
        }

        output.print("Informe o novo valor da consulta: ");

        BigDecimal valor;

        try {
            valor = new BigDecimal(scanner.nextLine().replace(",", "."));

            if (valor.compareTo(BigDecimal.ZERO) < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            output.println("Valor invalido.");
            return;
        }

        output.println("Informe a nova modalidade (PRESENCIAL ou TELEMEDICINA):");
        String modalidade = scanner.nextLine();

        if (!"PRESENCIAL".equalsIgnoreCase(modalidade)
                && !"TELEMEDICINA".equalsIgnoreCase(modalidade)) {
            output.println("Modalidade invalida.");
            return;
        }

        try {
            service.atualizar(
                    consultaId,
                    pacienteId,
                    medicoId,
                    dataHora,
                    valor,
                    ModalidadeConsulta.valueOf(modalidade.toUpperCase()));

            output.println("Consulta atualizada com sucesso.");

        } catch (IllegalArgumentException e) {
            output.println(e.getMessage());
        }
    }
}