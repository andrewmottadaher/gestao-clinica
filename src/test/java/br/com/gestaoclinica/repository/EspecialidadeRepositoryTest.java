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
}