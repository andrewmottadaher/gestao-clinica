package br.com.gestaoclinica.service;

import java.util.List;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;

public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public MedicoService(
            MedicoRepository medicoRepository,
            EspecialidadeRepository especialidadeRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    public Medico cadastrar(
            String nome,
            String crm,
            String email,
            String telefone,
            int especialidadeId) {

        Especialidade especialidade = especialidadeRepository.buscarPorId(especialidadeId);

        if (especialidade == null) {
            throw new IllegalArgumentException(
                    "Especialidade nao encontrada.");
        }

        if (medicoRepository.existeCrm(crm)) {
            throw new IllegalArgumentException(
                    "Ja existe um medico cadastrado com esse CRM.");
        }

        return medicoRepository.salvar(
                nome,
                crm,
                email,
                telefone,
                especialidade);
    }

    public Medico buscarPorId(int id) {
        return medicoRepository.buscarPorId(id);
    }

    public List<Medico> listarTodos() {
        return medicoRepository.listarTodos();
    }
}