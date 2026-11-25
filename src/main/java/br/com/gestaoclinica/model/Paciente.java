package br.com.gestaoclinica.model;

import java.time.LocalDate;

public class Paciente {

    public Paciente(
            int id,
            String nome,
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String telefone,
            String convenio) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do paciente deve ser maior que zero.");
        }
    }
}