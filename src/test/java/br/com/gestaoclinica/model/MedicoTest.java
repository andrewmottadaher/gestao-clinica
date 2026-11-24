package br.com.gestaoclinica.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicoTest {

    @Test
    void deveCriarMedicoComDadosValidos() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        Medico medico = new Medico(
                1,
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade);

        assertEquals(1, medico.getId());
        assertEquals("Joao Silva", medico.getNome());
        assertEquals("CRM12345", medico.getCrm());
        assertEquals("joao@email.com", medico.getEmail());
        assertEquals("31999999999", medico.getTelefone());
        assertEquals(especialidade, medico.getEspecialidade());
        assertEquals("S", medico.getAtivo());
    }

    @Test
    void deveRejeitarIdMenorOuIgualAZero() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(0, "Joao Silva", "CRM12345",
                                "joao@email.com", "31999999999", especialidade)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(-1, "Joao Silva", "CRM12345",
                                "joao@email.com", "31999999999", especialidade)));
    }

    @Test
    void deveRejeitarNomeNuloOuVazio() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, null, "CRM12345", "joao@email.com",
                                "31999999999", especialidade)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, "   ", "CRM12345", "joao@email.com",
                                "31999999999", especialidade)));
    }

    @Test
    void deveRejeitarCrmNuloOuVazio() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, "Joao Silva", null, "joao@email.com",
                                "31999999999", especialidade)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, "Joao Silva", "   ", "joao@email.com",
                                "31999999999", especialidade)));
    }

    @Test
    void deveRejeitarEmailNuloOuVazio() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, "Joao Silva", "CRM12345", null,
                                "31999999999", especialidade)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, "Joao Silva", "CRM12345", "   ",
                                "31999999999", especialidade)));
    }

    @Test
    void deveRejeitarTelefoneNuloOuVazio() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, "Joao Silva", "CRM12345", "joao@email.com",
                                null, especialidade)),

                () -> assertThrows(IllegalArgumentException.class,
                        () -> new Medico(1, "Joao Silva", "CRM12345", "joao@email.com",
                                "   ", especialidade)));
    }

    @Test
    void deveRejeitarEspecialidadeNula() {
        assertThrows(IllegalArgumentException.class, () -> new Medico(
                1,
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                null));
    }

    @Test
    void deveCriarMedicoComStatusInativo() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Especialidade do coracao");

        Medico medico = new Medico(
                1,
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade,
                "N");

        assertEquals("N", medico.getAtivo());
    }

    @Test
    void deveRejeitarStatusNulo() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Descricao");

        assertThrows(IllegalArgumentException.class, () -> new Medico(
                1, "Joao Silva", "CRM123",
                "joao@email.com", "31999999999",
                especialidade, null));
    }

    @Test
    void deveRejeitarStatusVazio() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Descricao");

        assertThrows(IllegalArgumentException.class, () -> new Medico(
                1, "Joao Silva", "CRM123",
                "joao@email.com", "31999999999",
                especialidade, ""));
    }

    @Test
    void deveRejeitarStatusDiferenteDeSeNS() {
        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Descricao");

        assertThrows(IllegalArgumentException.class, () -> new Medico(
                1, "Joao Silva", "CRM123",
                "joao@email.com", "31999999999",
                especialidade, "X"));
    }
}