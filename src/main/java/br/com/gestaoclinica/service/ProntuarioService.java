package br.com.gestaoclinica.service;

import java.util.List;

import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Prontuario;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.ProntuarioRepository;

public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;
    private final ConsultaRepository consultaRepository;

    public ProntuarioService(
            ProntuarioRepository prontuarioRepository,
            ConsultaRepository consultaRepository) {
        this.prontuarioRepository = prontuarioRepository;
        this.consultaRepository = consultaRepository;
    }

    public Prontuario cadastrar(
            int consultaId,
            String diagnostico,
            String prescricao,
            String observacoes) {

        Consulta consulta = consultaRepository.buscarPorId(consultaId);

        if (consulta == null) {
            throw new IllegalArgumentException(
                    "Consulta nao encontrada.");
        }
        if (prontuarioRepository.existePorConsulta(consultaId)) {
            throw new IllegalArgumentException(
                    "A consulta ja possui um prontuario.");
        }

        return prontuarioRepository.salvar(
                consulta,
                diagnostico,
                prescricao,
                observacoes);
    }

    public Prontuario buscarPorId(int id) {
        return prontuarioRepository.buscarPorId(id);
    }

    public List<Prontuario> listarTodos() {
        return prontuarioRepository.listarTodos();
    }

    public Prontuario atualizar(
            int prontuarioId,
            int consultaId,
            String diagnostico,
            String prescricao,
            String observacoes) {

        Prontuario existente = prontuarioRepository.buscarPorId(prontuarioId);

        if (existente == null) {
            return null;
        }

        if (existente.getConsulta().getId() != consultaId) {
            throw new IllegalArgumentException(
                    "Nao e permitido alterar a consulta do prontuario.");
        }

        return prontuarioRepository.atualizar(
                prontuarioId,
                existente.getConsulta(),
                diagnostico,
                prescricao,
                observacoes);
    }
}