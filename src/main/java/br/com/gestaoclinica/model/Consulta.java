package br.com.gestaoclinica.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Consulta {

    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHoraAgendamento;
    private BigDecimal valor;
    private StatusConsulta status;
    private ModalidadeConsulta modalidade;

    public Consulta(
            int id,
            Paciente paciente,
            Medico medico,
            LocalDateTime dataHoraAgendamento,
            BigDecimal valor,
            StatusConsulta status,
            ModalidadeConsulta modalidade) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da consulta deve ser maior que zero.");
        }
        if (paciente == null) {
            throw new IllegalArgumentException(
                    "O paciente da consulta e obrigatorio.");
        }
        if (medico == null) {
            throw new IllegalArgumentException(
                    "O medico da consulta e obrigatorio.");
        }
        if (dataHoraAgendamento == null) {
            throw new IllegalArgumentException(
                    "A data e hora do agendamento sao obrigatorias.");
        }

        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.valor = valor;
        this.status = status;
        this.modalidade = modalidade;
    }

    public int getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public LocalDateTime getDataHoraAgendamento() {
        return dataHoraAgendamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public ModalidadeConsulta getModalidade() {
        return modalidade;
    }
}