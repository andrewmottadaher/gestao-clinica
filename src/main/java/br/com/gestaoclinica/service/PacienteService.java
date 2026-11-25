package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;

import java.time.LocalDate;
import java.util.List;

public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public PacienteService(
            PacienteRepository pacienteRepository,
            ConsultaRepository consultaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
    }

    public Paciente cadastrar(
            String nome,
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String telefone,
            String convenio) {

        if (pacienteRepository.existeCpf(cpf)) {
            throw new IllegalArgumentException(
                    "Ja existe um paciente cadastrado com esse CPF.");
        }

        return pacienteRepository.salvar(
                nome,
                cpf,
                dataNascimento,
                sexo,
                telefone,
                convenio);
    }

    public Paciente buscarPorId(int id) {
        return pacienteRepository.buscarPorId(id);
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.listarTodos();
    }
}