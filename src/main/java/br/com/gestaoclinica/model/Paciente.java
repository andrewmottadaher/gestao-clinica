package br.com.gestaoclinica.model;

import java.time.LocalDate;

public class Paciente {

    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String sexo;
    private String telefone;
    private String convenio;

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

        if (!"M".equals(sexo) && !"F".equals(sexo)) {
            throw new IllegalArgumentException(
                    "O sexo do paciente deve ser M ou F.");
        }

        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException(
                    "O telefone do paciente e obrigatorio.");
        }

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.telefone = telefone;
        this.convenio = convenio;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getConvenio() {
        return convenio;
    }
}