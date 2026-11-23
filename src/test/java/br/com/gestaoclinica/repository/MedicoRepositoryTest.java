package br.com.gestaoclinica.repository;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;

import org.junit.jupiter.api.Test;

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
}