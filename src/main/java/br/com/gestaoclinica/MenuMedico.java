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
    }
}