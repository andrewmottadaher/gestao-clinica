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
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do paciente e obrigatorio.");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException(
                    "O CPF do paciente e obrigatorio.");
        }
        if (dataNascimento == null) {
            throw new IllegalArgumentException(
                    "A data de nascimento do paciente e obrigatoria.");
        }
    }
}