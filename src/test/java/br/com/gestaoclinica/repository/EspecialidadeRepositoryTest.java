package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Especialidade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadeRepositoryTest {

    @Test
    void deveSalvarEspecialidadeComIdGeradoAutomaticamente() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade especialidade = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertAll(
                () -> assertEquals(1, especialidade.getId()),
                () -> assertEquals("Cardiologia", especialidade.getNome()),
                () -> assertEquals("Especialidade do coracao", especialidade.getDescricao()));
    }

    @Test
    void deveGerarIdsSequenciaisAoSalvarEspecialidades() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade primeira = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade segunda = repository.salvar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        assertAll(
                () -> assertEquals(1, primeira.getId()),
                () -> assertEquals(2, segunda.getId()));
    }

    @Test
    void deveBuscarEspecialidadePorId() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade cadastrada = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade encontrada = repository.buscarPorId(cadastrada.getId());

        assertAll(
                () -> assertNotNull(encontrada),
                () -> assertEquals(cadastrada.getId(), encontrada.getId()),
                () -> assertEquals("Cardiologia", encontrada.getNome()),
                () -> assertEquals("Especialidade do coracao", encontrada.getDescricao()));
    }

    @Test
    void deveRetornarNuloAoBuscarEspecialidadeComIdInexistente() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade encontrada = repository.buscarPorId(999);

        assertNull(encontrada);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremEspecialidades() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        assertTrue(repository.listarTodos().isEmpty());
    }

    @Test
    void deveListarTodasAsEspecialidadesCadastradas() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade primeira = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade segunda = repository.salvar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        var especialidades = repository.listarTodos();

        assertAll(
                () -> assertEquals(2, especialidades.size()),
                () -> assertTrue(especialidades.contains(primeira)),
                () -> assertTrue(especialidades.contains(segunda)));
    }

    @Test
    void deveRetornarVerdadeiroQuandoNomeJaEstiverCadastrado() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertTrue(repository.existeNome("Cardiologia"));
    }

    @Test
    void deveRetornarFalsoQuandoNomeNaoEstiverCadastrado() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        assertFalse(repository.existeNome("Cardiologia"));
    }

    @Test
    void deveIgnorarDiferencasEntreLetrasMaiusculasEMinusculas() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertTrue(repository.existeNome("CARDIOLOGIA"));
    }

    @Test
    void deveIgnorarEspacosNasExtremidadesDoNome() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertTrue(repository.existeNome("  Cardiologia  "));
    }

    @Test
    void deveRetornarFalsoQuandoNomeForNulo() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        assertFalse(repository.existeNome(null));
    }

    @Test
    void deveAtualizarEspecialidadePreservandoSeuId() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade cadastrada = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade atualizada = repository.atualizar(
                cadastrada.getId(),
                "Neurologia",
                "Especialidade do sistema nervoso");

        assertAll(
                () -> assertNotNull(atualizada),
                () -> assertEquals(cadastrada.getId(), atualizada.getId()),
                () -> assertEquals("Neurologia", atualizada.getNome()),
                () -> assertEquals(
                        "Especialidade do sistema nervoso",
                        atualizada.getDescricao()));
    }

    @Test
    void deveRetornarNuloAoAtualizarEspecialidadeInexistente() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade atualizada = repository.atualizar(
                999,
                "Neurologia",
                "Especialidade do sistema nervoso");

        assertNull(atualizada);
        assertTrue(repository.listarTodos().isEmpty());
    }

    @Test
    void naoDeveAlterarEspecialidadeQuandoNomeForInvalido() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade cadastrada = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.atualizar(
                        cadastrada.getId(),
                        " ",
                        "Nova descricao"));

        Especialidade encontrada = repository.buscarPorId(cadastrada.getId());

        assertAll(
                () -> assertNotNull(encontrada),
                () -> assertEquals("Cardiologia", encontrada.getNome()),
                () -> assertEquals(
                        "Especialidade do coracao",
                        encontrada.getDescricao()));
    }

    @Test
    void deveExcluirEspecialidadeCadastrada() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade cadastrada = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        boolean excluida = repository.excluir(cadastrada.getId());

        assertAll(
                () -> assertTrue(excluida),
                () -> assertNull(repository.buscarPorId(cadastrada.getId())),
                () -> assertTrue(repository.listarTodos().isEmpty()));
    }

    @Test
    void deveRetornarFalsoAoExcluirEspecialidadeInexistente() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        boolean excluida = repository.excluir(999);

        assertFalse(excluida);
        assertTrue(repository.listarTodos().isEmpty());
    }

    @Test
    void deveIdentificarNomeCadastradoEmOutraEspecialidade() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade primeira = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        repository.salvar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        assertTrue(repository.existeNome(
                "Neurologia",
                primeira.getId()));
    }

    @Test
    void deveIgnorarNomeDaPropriaEspecialidade() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade especialidade = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertFalse(repository.existeNome(
                "Cardiologia",
                especialidade.getId()));
    }

    @Test
    void deveRetornarFalsoQuandoNomeNaoEstiverEmOutraEspecialidade() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        Especialidade especialidade = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertFalse(repository.existeNome(
                "Neurologia",
                especialidade.getId()));
    }

    @Test
    void naoDeveConsumirIdAoTentarSalvarEspecialidadeInvalida() {
        EspecialidadeRepository repository = new EspecialidadeRepository();

        assertThrows(IllegalArgumentException.class, () -> repository.salvar(
                null,
                "Especialidade sem nome"));

        Especialidade especialidade = repository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        assertEquals(1, especialidade.getId());
    }
}