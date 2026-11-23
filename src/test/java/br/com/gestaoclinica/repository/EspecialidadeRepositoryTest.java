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
}