package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}