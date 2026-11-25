package br.com.gestaoclinica.service;

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
        return null;
    }
}