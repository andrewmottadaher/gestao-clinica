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
    }
}