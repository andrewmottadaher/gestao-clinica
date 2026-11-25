package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.Prontuario;
import br.com.gestaoclinica.model.StatusConsulta;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.ProntuarioRepository;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProntuarioServiceTest {

    @Test
    void deveCadastrarProntuarioParaConsultaRealizada() {
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        ConsultaRepository consultaRepository = new ConsultaRepository();

        PacienteRepository pacienteRepository = new PacienteRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1, "Cardiologia", "Doencas do coracao");

        Paciente paciente = pacienteRepository.salvar(
                "Joao",
                "12345678900",
                LocalDate.of(1990, 5, 10),
                "M",
                "31888888888",
                null);

        Medico medico = medicoRepository.salvar(
                "Carlos",
                "CRM123",
                "carlos@email.com",
                "31999999999",
                especialidade);

        Consulta consulta = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.now(),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        Prontuario prontuario = service.cadastrar(
                consulta.getId(),
                "Hipertensao arterial",
                "Uso de medicamento",
                "Retorno em 30 dias");

        assertNotNull(prontuario);
        assertEquals(1, prontuario.getId());
        assertEquals(consulta.getId(), prontuario.getConsulta().getId());
        assertEquals(
                "Hipertensao arterial",
                prontuario.getDiagnostico());
    }
   
    @Test
    void deveRejeitarCadastroParaConsultaInexistente() {
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        ConsultaRepository consultaRepository = new ConsultaRepository();

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar(
                        999,
                        "Diagnostico",
                        null,
                        null));

        assertEquals("Consulta nao encontrada.", excecao.getMessage());
    }
}