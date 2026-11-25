package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Paciente;

import java.time.LocalDate;

public class PacienteRepository {

    private int proximoId = 1;

    public Paciente salvar(
            String nome,
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String telefone,
            String convenio) {

        Paciente paciente = new Paciente(
                proximoId,
                nome,
                cpf,
                dataNascimento,
                sexo,
                telefone,
                convenio);

        proximoId++;

        return paciente;
    }
}