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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void deveRejeitarSegundoProntuarioParaMesmaConsulta() {
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

        service.cadastrar(
                consulta.getId(),
                "Diagnostico inicial",
                null,
                null);

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> service.cadastrar(
                        consulta.getId(),
                        "Segundo diagnostico",
                        null,
                        null));

        assertEquals(
                "A consulta ja possui um prontuario.",
                excecao.getMessage());
    }

    @Test
    void deveBuscarProntuarioPorIdExistente() {
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

        Prontuario cadastrado = service.cadastrar(
                consulta.getId(),
                "Diagnostico inicial",
                "Prescricao inicial",
                null);

        Prontuario encontrado = service.buscarPorId(cadastrado.getId());

        assertNotNull(encontrado);
        assertEquals(cadastrado.getId(), encontrado.getId());
        assertEquals("Diagnostico inicial", encontrado.getDiagnostico());
    }

    @Test
    void deveRetornarNullAoBuscarProntuarioPorIdInexistente() {
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        ConsultaRepository consultaRepository = new ConsultaRepository();

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        Prontuario encontrado = service.buscarPorId(999);

        assertNull(encontrado);
    }

    @Test
    void deveListarTodosOsProntuariosCadastrados() {
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

        Consulta consulta1 = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.now(),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Consulta consulta2 = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.now(),
                new BigDecimal("300.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.TELEMEDICINA);

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        service.cadastrar(
                consulta1.getId(), "Diagnostico 1", null, null);

        service.cadastrar(
                consulta2.getId(), "Diagnostico 2", null, null);

        List<Prontuario> prontuarios = service.listarTodos();

        assertEquals(2, prontuarios.size());
        assertEquals("Diagnostico 1", prontuarios.get(0).getDiagnostico());
        assertEquals("Diagnostico 2", prontuarios.get(1).getDiagnostico());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverProntuarios() {
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        ConsultaRepository consultaRepository = new ConsultaRepository();

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        List<Prontuario> prontuarios = service.listarTodos();

        assertNotNull(prontuarios);
        assertTrue(prontuarios.isEmpty());
    }

    @Test
    void deveAtualizarProntuarioExistente() {
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

        Prontuario cadastrado = service.cadastrar(
                consulta.getId(),
                "Diagnostico inicial",
                "Prescricao inicial",
                "Observacoes iniciais");

        Prontuario atualizado = service.atualizar(
                cadastrado.getId(),
                consulta.getId(),
                "Diagnostico atualizado",
                "Prescricao atualizada",
                "Observacoes atualizadas");

        assertNotNull(atualizado);
        assertEquals(cadastrado.getId(), atualizado.getId());
        assertEquals(consulta.getId(), atualizado.getConsulta().getId());
        assertEquals("Diagnostico atualizado", atualizado.getDiagnostico());
        assertEquals("Prescricao atualizada", atualizado.getPrescricao());
        assertEquals("Observacoes atualizadas", atualizado.getObservacoes());
    }

    @Test
    void deveRetornarNullAoAtualizarProntuarioInexistente() {
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        ConsultaRepository consultaRepository = new ConsultaRepository();

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        Prontuario atualizado = service.atualizar(
                999,
                1,
                "Diagnostico",
                "Prescricao",
                "Observacoes");

        assertNull(atualizado);
    }

    @Test
    void devePreservarProntuarioOriginalQuandoAtualizacaoForInvalida() {
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

        Prontuario cadastrado = service.cadastrar(
                consulta.getId(),
                "Diagnostico original",
                "Prescricao original",
                "Observacoes originais");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizar(
                        cadastrado.getId(),
                        consulta.getId(),
                        "",
                        "Nova prescricao",
                        "Novas observacoes"));

        Prontuario encontrado = service.buscarPorId(cadastrado.getId());

        assertEquals("Diagnostico original", encontrado.getDiagnostico());
        assertEquals("Prescricao original", encontrado.getPrescricao());
        assertEquals("Observacoes originais", encontrado.getObservacoes());
    }

    @Test
    void deveRejeitarAlteracaoDaConsultaVinculadaAoProntuario() {
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

        Consulta consultaOriginal = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.now(),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        Consulta outraConsulta = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.now(),
                new BigDecimal("300.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.TELEMEDICINA);

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        Prontuario cadastrado = service.cadastrar(
                consultaOriginal.getId(),
                "Diagnostico original",
                null,
                null);

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> service.atualizar(
                        cadastrado.getId(),
                        outraConsulta.getId(),
                        "Novo diagnostico",
                        null,
                        null));

        assertEquals(
                "Nao e permitido alterar a consulta do prontuario.",
                excecao.getMessage());

        Prontuario encontrado = service.buscarPorId(cadastrado.getId());

        assertEquals(
                consultaOriginal.getId(),
                encontrado.getConsulta().getId());
    }

    @Test
    void deveExcluirProntuarioExistente() {
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

        Prontuario cadastrado = service.cadastrar(
                consulta.getId(),
                "Diagnostico",
                "Prescricao",
                "Observacoes");

        boolean excluido = service.excluir(cadastrado.getId());

        assertTrue(excluido);
        assertNull(service.buscarPorId(cadastrado.getId()));
    }

    @Test
    void deveRetornarFalseAoExcluirProntuarioInexistente() {
        ProntuarioRepository prontuarioRepository = new ProntuarioRepository();

        ConsultaRepository consultaRepository = new ConsultaRepository();

        ProntuarioService service = new ProntuarioService(
                prontuarioRepository,
                consultaRepository);

        boolean excluido = service.excluir(999);

        assertFalse(excluido);
    }
}