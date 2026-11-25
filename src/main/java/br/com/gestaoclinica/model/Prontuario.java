package br.com.gestaoclinica.model;

import java.time.LocalDate;

public class Prontuario {

    private int id;
    private Consulta consulta;
    private String diagnostico;
    private String prescricao;
    private String observacoes;
    private LocalDate dataRegistro;

    public Prontuario(
            int id,
            Consulta consulta,
            String diagnostico,
            String prescricao,
            String observacoes) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser maior que zero.");
        }

        this.id = id;
        this.consulta = consulta;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.observacoes = observacoes;
        this.dataRegistro = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }
}