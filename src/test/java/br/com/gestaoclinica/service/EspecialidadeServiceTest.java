package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EspecialidadeServiceTest {

    @Test
    void deveCadastrarEspecialidadeComDadosValidos() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        Especialidade resultado = service.buscarPorId(999);

        assertNull(resultado);
    }

    @Test
    void deveListarTodasAsEspecialidadesCadastradas() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        service.cadastrar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        List<Especialidade> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream()
                .anyMatch(e -> e.getNome().equals("Cardiologia")));

        assertTrue(resultado.stream()
                .anyMatch(e -> e.getNome().equals("Neurologia")));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremEspecialidades() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        List<Especialidade> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarEspecialidadeComDadosValidos() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

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
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        Especialidade cadastrada = service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        boolean resultado = service.excluir(cadastrada.getId());

        assertTrue(resultado);
        assertNull(service.buscarPorId(cadastrada.getId()));
    }

    @Test
    void deveRetornarFalsoAoExcluirEspecialidadeInexistente() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        boolean resultado = service.excluir(999);

        assertFalse(resultado);
        assertTrue(repository.listarTodos().isEmpty());
    }

    @Test
    void deveBuscarEspecialidadeCorretaEntreVariosRegistros() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade segunda = service.cadastrar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        service.cadastrar(
                "Ortopedia",
                "Especialidade dos ossos");

        Especialidade resultado = service.buscarPorId(segunda.getId());

        assertNotNull(resultado);
        assertEquals(segunda.getId(), resultado.getId());
        assertEquals("Neurologia", resultado.getNome());
    }

    @Test
    void deveListarUmaEspecialidadeCadastrada() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        Especialidade cadastrada = service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        List<Especialidade> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals(cadastrada.getId(), resultado.get(0).getId());
        assertEquals("Cardiologia", resultado.get(0).getNome());
    }

    @Test
    void deveManterOutrasEspecialidadesAposExcluirUma() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade segunda = service.cadastrar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        service.cadastrar(
                "Ortopedia",
                "Especialidade dos ossos");

        boolean resultado = service.excluir(segunda.getId());

        assertTrue(resultado);
        assertEquals(2, service.listarTodos().size());
        assertNull(service.buscarPorId(segunda.getId()));
        assertNotNull(service.buscarPorId(1));
        assertNotNull(service.buscarPorId(3));
    }

    @Test
    void deveImpedirExclusaoDeEspecialidadeComMedicoVinculado() {
        EspecialidadeRepository repository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        EspecialidadeService service = new EspecialidadeService(repository, medicoRepository);

        Especialidade especialidade = service.cadastrar(
                "Cardiologia",
                "Especialidade do coracao");

        medicoRepository.salvar(
                "Joao Silva",
                "CRM123",
                "joao@email.com",
                "31999999999",
                especialidade);

        assertThrows(IllegalArgumentException.class,
                () -> service.excluir(especialidade.getId()));

        assertNotNull(service.buscarPorId(especialidade.getId()));
    }
}