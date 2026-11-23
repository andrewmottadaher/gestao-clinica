package br.com.gestaoclinica.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadeTest {

    @Test
    void deveCriarEspecialidadeComDadosValidos() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao"
        );

        assertAll(
                () -> assertEquals(1, especialidade.getId()),
                () -> assertEquals("Cardiologia", especialidade.getNome()),
                () -> assertEquals("Especialidade do coracao", especialidade.getDescricao())
        );
    }
}