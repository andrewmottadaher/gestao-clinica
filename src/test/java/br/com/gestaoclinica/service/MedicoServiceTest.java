package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void naoDeveCadastrarMedicoComCrmDuplicado() {
        EspecialidadeRepository especialidadeRepository =
                new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository);

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrar(
                        "Maria Souza",
                        "CRM12345",
                        "maria@email.com",
                        "31988888888",
                        especialidade.getId()));

        assertEquals(1, medicoRepository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarMedicoComCrmDiferenciandoMaiusculas() {
        EspecialidadeRepository especialidadeRepository =
                new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository);

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrar(
                        "Maria Souza",
                        "crm12345",
                        "maria@email.com",
                        "31988888888",
                        especialidade.getId()));

        assertEquals(1, medicoRepository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarMedicoComCrmDiferenciadoPorEspacos() {
        EspecialidadeRepository especialidadeRepository =
                new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository);

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrar(
                        "Maria Souza",
                        "  CRM12345  ",
                        "maria@email.com",
                        "31988888888",
                        especialidade.getId()));

        assertEquals(1, medicoRepository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarMedicoComEspecialidadeInexistente() {
        EspecialidadeRepository especialidadeRepository =
                new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository);

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrar(
                        "Joao Silva",
                        "CRM12345",
                        "joao@email.com",
                        "31999999999",
                        999));

        assertEquals(0, medicoRepository.listarTodos().size());
    }

    @Test
    void deveBuscarMedicoPorId() {
        EspecialidadeRepository especialidadeRepository =
                new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository);

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        Medico resultado = service.buscarPorId(cadastrado.getId());

        assertNotNull(resultado);
        assertEquals(cadastrado.getId(), resultado.getId());
        assertEquals("Joao Silva", resultado.getNome());
        assertEquals("CRM12345", resultado.getCrm());
    }
}