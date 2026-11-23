package br.com.gestaoclinica.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadeTest {

    @Test
    void deveCriarEspecialidadeComDadosValidos() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        assertAll(
                () -> assertEquals(1, especialidade.getId()),
                () -> assertEquals("Cardiologia", especialidade.getNome()),
                () -> assertEquals("Especialidade do coracao", especialidade.getDescricao()));
    }

    @Test
    void naoDeveCriarEspecialidadeComIdInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> new Especialidade(
                        0, "Cardiologia", "Especialidade do coracao"));
    }

    @Test
    void naoDeveCriarEspecialidadeComNomeNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Especialidade(
                        1, null, "Especialidade do coracao"));
    }

    @Test
    void naoDeveCriarEspecialidadeComNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Especialidade(
                        1, "", "Especialidade do coracao"));
    }

    @Test
    void naoDeveCriarEspecialidadeComNomeEmBranco() {
        assertThrows(IllegalArgumentException.class,
                () -> new Especialidade(
                        1, "   ", "Especialidade do coracao"));
    }

    @Test
    void naoDeveCriarEspecialidadeComDescricaoNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new Especialidade(
                        1, "Cardiologia", null));
    }
}