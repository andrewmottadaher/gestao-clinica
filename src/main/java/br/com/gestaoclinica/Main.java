package br.com.gestaoclinica;

import java.util.Scanner;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.ProntuarioRepository;

import br.com.gestaoclinica.service.ConsultaService;
import br.com.gestaoclinica.service.EspecialidadeService;
import br.com.gestaoclinica.service.MedicoService;
import br.com.gestaoclinica.service.PacienteService;
import br.com.gestaoclinica.service.ProntuarioService;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

    private static final MedicoRepository medicoRepository = new MedicoRepository();

    private static final PacienteRepository pacienteRepository = new PacienteRepository();

    private static final ConsultaRepository consultaRepository = new ConsultaRepository();

    private static final ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

    private static final EspecialidadeService especialidadeService = new EspecialidadeService(
            especialidadeRepository,
            medicoRepository);

    private static final MedicoService medicoService = new MedicoService(
            medicoRepository,
            especialidadeRepository,
            consultaRepository);

    private static final PacienteService pacienteService = new PacienteService(
            pacienteRepository,
            consultaRepository);

    private static final ConsultaService consultaService = new ConsultaService(
            consultaRepository,
            pacienteRepository,
            medicoRepository,
            prontuarioRepository);

    private static final ProntuarioService prontuarioService = new ProntuarioService(
            prontuarioRepository,
            consultaRepository);

    private static void exibirMenuPrincipal() {
        System.out.println("\n==================================");
        System.out.println("         GESTAO CLINICA");
        System.out.println("==================================");
        System.out.println("1 - Gerenciar especialidades");
        System.out.println("2 - Gerenciar medicos");
        System.out.println("3 - Gerenciar pacientes");
        System.out.println("4 - Gerenciar consultas");
        System.out.println("5 - Gerenciar prontuarios");
        System.out.println("0 - Sair");
        System.out.println("==================================");
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Informe um numero inteiro valido.");
            }
        }
    }

    private static void gerenciarEspecialidades() {
        MenuEspecialidade menu = new MenuEspecialidade(
                especialidadeService,
                scanner,
                System.out);

        menu.exibir();
    }

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> gerenciarEspecialidades();
                case 2 -> System.out.println("Gerenciamento de medicos.");
                case 3 -> System.out.println("Gerenciamento de pacientes.");
                case 4 -> System.out.println("Gerenciamento de consultas.");
                case 5 -> System.out.println("Gerenciamento de prontuarios.");
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}