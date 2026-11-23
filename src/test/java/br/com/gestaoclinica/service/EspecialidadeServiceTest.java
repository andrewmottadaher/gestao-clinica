package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EspecialidadeServiceTest {

    @Test
    void deveCadastrarEspecialidadeComDadosValidos() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        Especialidade resultado = service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Cardiologia", resultado.getNome());
        assertEquals("Especialidade do coracao", resultado.getDescricao());
    }

    @Test
    void naoDeveCadastrarEspecialidadeComNomeDuplicado() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(
                "Cardiologia",
                "Outra descricao"));

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarEspecialidadeComNomeDuplicadoDiferenciandoMaiusculas() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(
                "CARDIOLOGIA",
                "Outra descricao"));

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarEspecialidadeComNomeDuplicadoComEspacosNasExtremidades() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(
                "  Cardiologia  ",
                "Outra descricao"));

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void deveBuscarEspecialidadePorId() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        Especialidade cadastrada = service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade resultado = service.buscarPorId(cadastrada.getId());

        assertNotNull(resultado);
        assertEquals(cadastrada.getId(), resultado.getId());
        assertEquals("Cardiologia", resultado.getNome());
    }

    @Test
    void deveRetornarNuloAoBuscarEspecialidadeComIdInexistente() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        Especialidade resultado = service.buscarPorId(999);

        assertNull(resultado);
    }

    @Test
    void deveListarTodasAsEspecialidadesCadastradas() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        service.cadastrar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        List<Especialidade> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Cardiologia", resultado.get(0).getNome());
        assertEquals("Neurologia", resultado.get(1).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremEspecialidades() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        List<Especialidade> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarEspecialidadeComDadosValidos() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        Especialidade cadastrada = service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade resultado = service.atualizar(
                cadastrada.getId(),
                "Neurologia",
                "Especialidade do sistema nervoso");

        assertNotNull(resultado);
        assertEquals(cadastrada.getId(), resultado.getId());
        assertEquals("Neurologia", resultado.getNome());
        assertEquals("Especialidade do sistema nervoso", resultado.getDescricao());
    }

    @Test
    void naoDeveAtualizarEspecialidadeComNomeDuplicado() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade segunda = service.cadastrar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                segunda.getId(),
                "Cardiologia",
                "Nova descricao"));

        assertEquals("Neurologia",
                repository.buscarPorId(segunda.getId()).getNome());
    }

    @Test
    void deveRetornarNuloAoAtualizarEspecialidadeInexistente() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        Especialidade resultado = service.atualizar(
                999,
                "Cardiologia",
                "Especialidade do coracao");

        assertNull(resultado);
        assertTrue(repository.listarTodos().isEmpty());
    }

    @Test
    void deveExcluirEspecialidadeCadastrada() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        EspecialidadeService service = new EspecialidadeService(repository);

        Especialidade cadastrada = service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        boolean resultado = service.excluir(cadastrada.getId());

        assertTrue(resultado);
        assertNull(service.buscarPorId(cadastrada.getId()));
    }
}