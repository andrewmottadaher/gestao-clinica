package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;

import java.time.LocalDate;

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
        return pacienteRepository.salvar(
                nome,
                cpf,
                dataNascimento,
                sexo,
                telefone,
                convenio);
    }
}