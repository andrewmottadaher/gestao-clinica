package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MedicoServiceTest {

    @Test
    void deveCadastrarMedicoComDadosValidos() {
        EspecialidadeRepository especialidadeRepository =
                new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository);

        Medico resultado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Joao Silva", resultado.getNome());
        assertEquals("CRM12345", resultado.getCrm());
        assertEquals("Cardiologia",
                resultado.getEspecialidade().getNome());
        assertEquals("S", resultado.getAtivo());
    }
}