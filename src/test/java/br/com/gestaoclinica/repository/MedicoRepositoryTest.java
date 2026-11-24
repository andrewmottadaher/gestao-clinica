package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicoRepositoryTest {

    @Test
    void deveSalvarMedicoComIdGeradoAutomaticamente() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        Medico medico = repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        assertAll(
                () -> assertNotNull(medico),
                () -> assertEquals(1, medico.getId()),
                () -> assertEquals("Joao Silva", medico.getNome()),
                () -> assertEquals("CRM12345", medico.getCrm()),
                () -> assertEquals("joao@email.com", medico.getEmail()),
                () -> assertEquals("31999999999", medico.getTelefone()),
                () -> assertEquals(especialidade, medico.getEspecialidade()),
                () -> assertEquals("S", medico.getAtivo()));
    }

    @Test
    void deveGerarIdsSequenciaisAoSalvarMedicos() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        Medico primeiro = repository.salvar(
                "Joao Silva", "CRM12345", "joao@email.com",
                "31999999999", especialidade);

        Medico segundo = repository.salvar(
                "Maria Souza", "CRM67890", "maria@email.com",
                "31988888888", especialidade);

        assertAll(
                () -> assertEquals(1, primeiro.getId()),
                () -> assertEquals(2, segundo.getId()));
    }

    @Test
    void naoDeveConsumirIdQuandoCadastroForInvalido() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        assertThrows(IllegalArgumentException.class, () -> repository.salvar(
                null,
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade));

        Medico medico = repository.salvar(
                "Maria Souza",
                "CRM67890",
                "maria@email.com",
                "31988888888",
                especialidade);

        assertEquals(1, medico.getId());
    }

    @Test
    void deveBuscarMedicoPorId() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        Medico cadastrado = repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        Medico encontrado = repository.buscarPorId(1);

        assertNotNull(encontrado);

        assertAll(
                () -> assertEquals(cadastrado, encontrado),
                () -> assertEquals("Joao Silva", encontrado.getNome()));
    }

    @Test
    void deveRetornarNullQuandoMedicoNaoExistir() {
        MedicoRepository repository = new MedicoRepository();

        Medico encontrado = repository.buscarPorId(99);

        assertNull(encontrado);
    }

    @Test
    void deveLocalizarMedicoCorretoEntreVariosCadastrados() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        repository.salvar(
                "Joao Silva", "CRM12345", "joao@email.com",
                "31999999999", especialidade);

        Medico segundo = repository.salvar(
                "Maria Souza", "CRM67890", "maria@email.com",
                "31988888888", especialidade);

        repository.salvar(
                "Pedro Santos", "CRM54321", "pedro@email.com",
                "31977777777", especialidade);

        Medico encontrado = repository.buscarPorId(2);

        assertAll(
                () -> assertNotNull(encontrado),
                () -> assertEquals(segundo, encontrado),
                () -> assertEquals("Maria Souza", encontrado.getNome()));
    }

    @Test
    void deveListarTodosOsMedicosCadastrados() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        repository.salvar(
                "Joao Silva", "CRM12345", "joao@email.com",
                "31999999999", especialidade);

        repository.salvar(
                "Maria Souza", "CRM67890", "maria@email.com",
                "31988888888", especialidade);

        repository.salvar(
                "Pedro Santos", "CRM54321", "pedro@email.com",
                "31977777777", especialidade);

        List<Medico> medicos = repository.listarTodos();

        assertNotNull(medicos);

        assertAll(
                () -> assertEquals(3, medicos.size()),
                () -> assertTrue(medicos.stream()
                        .anyMatch(m -> m.getNome().equals("Joao Silva"))),
                () -> assertTrue(medicos.stream()
                        .anyMatch(m -> m.getNome().equals("Maria Souza"))),
                () -> assertTrue(medicos.stream()
                        .anyMatch(m -> m.getNome().equals("Pedro Santos"))));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremMedicos() {
        MedicoRepository repository = new MedicoRepository();

        var medicos = repository.listarTodos();

        assertNotNull(medicos);
        assertTrue(medicos.isEmpty());
    }

    @Test
    void deveRetornarCopiaIndependenteDaListaDeMedicos() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        var medicos = repository.listarTodos();

        medicos.clear();

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void deveVerificarSeCrmJaEstaCadastrado() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        assertTrue(repository.existeCrm("CRM12345"));
    }

    @Test
    void deveRetornarFalseQuandoCrmNaoEstiverCadastrado() {
        MedicoRepository repository = new MedicoRepository();

        assertFalse(repository.existeCrm("CRM99999"));
    }

    @Test
    void deveIgnorarMaiusculasEMinusculasNaVerificacaoDoCrm() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        assertTrue(repository.existeCrm("crm12345"));
    }

    @Test
    void deveIgnorarEspacosNasExtremidadesNaVerificacaoDoCrm() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        assertTrue(repository.existeCrm("  CRM12345  "));
    }

    @Test
    void deveRetornarFalseQuandoCrmForNull() {
        MedicoRepository repository = new MedicoRepository();

        assertFalse(repository.existeCrm(null));
    }

    @Test
    void deveAtualizarDadosDeMedicoCadastrado() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        Medico cadastrado = repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        Medico atualizado = repository.atualizar(
                cadastrado.getId(),
                "Joao Santos",
                "CRM54321",
                "joao.santos@email.com",
                "31988888888",
                especialidade);

        assertNotNull(atualizado);

        assertAll(
                () -> assertEquals(cadastrado.getId(), atualizado.getId()),
                () -> assertEquals("Joao Santos", atualizado.getNome()),
                () -> assertEquals("CRM54321", atualizado.getCrm()),
                () -> assertEquals("joao.santos@email.com", atualizado.getEmail()),
                () -> assertEquals("31988888888", atualizado.getTelefone()),
                () -> assertEquals(especialidade, atualizado.getEspecialidade()));
    }

    @Test
    void deveRetornarNullAoAtualizarMedicoInexistente() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        Medico atualizado = repository.atualizar(
                999,
                "Joao Santos",
                "CRM54321",
                "joao.santos@email.com",
                "31988888888",
                especialidade);

        assertNull(atualizado);
    }
    
    @Test
    void naoDeveAlterarDadosOriginaisQuandoAtualizacaoForInvalida() {
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Especialidade do coracao");

        MedicoRepository repository = new MedicoRepository();

        Medico cadastrado = repository.salvar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.atualizar(
                        cadastrado.getId(),
                        " ",
                        "CRM54321",
                        "joao.santos@email.com",
                        "31988888888",
                        especialidade));

        Medico encontrado = repository.buscarPorId(cadastrado.getId());

        assertSame(cadastrado, encontrado);
        assertEquals("Joao Silva", encontrado.getNome());
        assertEquals("CRM12345", encontrado.getCrm());
    }
}