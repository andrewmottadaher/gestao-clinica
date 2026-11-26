package br.com.gestaoclinica;

import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.service.ConsultaService;

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

                int idPaciente = Integer.parseInt(pacienteId);
                int idMedico = Integer.parseInt(medicoId);

                ModalidadeConsulta modalidadeConsulta = ModalidadeConsulta.valueOf(modalidade.toUpperCase());

                service.agendar(
                        idPaciente,
                        idMedico,
                        dataHora,
                        valor,
                        modalidadeConsulta);

                output.println("Consulta agendada com sucesso.");
            }
            output.println("Opcao invalida.");
        }
    }
}