package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacienteRepository {

    private int proximoId = 1;
    private final Map<Integer, Paciente> pacientes = new HashMap<>();

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

        pacientes.put(proximoId, paciente);
        proximoId++;

        return paciente;
    }

    public Paciente buscarPorId(int id) {
        return pacientes.get(id);
    }

    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes.values());
    }

    public Paciente atualizar(
            int id,
            String nome,
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String telefone,
            String convenio) {

        Paciente atual = pacientes.get(id);

        if (atual == null) {
            return null;
        }

        Paciente atualizado = new Paciente(
                id,
                nome,
                cpf,
                dataNascimento,
                sexo,
                telefone,
                convenio);

        pacientes.put(id, atualizado);

        return atualizado;
    }

    public boolean excluir(int id) {
        return pacientes.remove(id) != null;
    }
}