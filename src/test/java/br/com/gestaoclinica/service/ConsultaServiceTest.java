package br.com.gestaoclinica.service;

import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.PacienteRepository;
import br.com.gestaoclinica.model.Consulta;
import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.StatusConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.Medico;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsultaServiceTest {

    @Test
    void deveRejeitarAgendamentoQuandoPacienteNaoExiste() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository());

        assertThrows(IllegalArgumentException.class, () -> service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveRejeitarAgendamentoQuandoMedicoNaoExiste() {
        PacienteRepository pacienteRepository = new PacienteRepository();

        pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                pacienteRepository,
                new MedicoRepository());

        assertThrows(IllegalArgumentException.class, () -> service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveRejeitarAgendamentoQuandoMedicoEstaInativo() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        medicoRepository.inativar(1);

        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                ModalidadeConsulta.PRESENCIAL));
    }

    @Test
    void deveAgendarConsultaComPacienteEMedicoValidos() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                pacienteRepository,
                medicoRepository);

        LocalDateTime dataHora = LocalDateTime.of(2026, 12, 10, 14, 30);
        BigDecimal valor = new BigDecimal("250.00");

        Consulta consulta = service.agendar(
                1,
                1,
                dataHora,
                valor,
                ModalidadeConsulta.PRESENCIAL);

        assertNotNull(consulta);
        assertEquals(1, consulta.getId());
        assertEquals(1, consulta.getPaciente().getId());
        assertEquals(1, consulta.getMedico().getId());
        assertEquals(dataHora, consulta.getDataHoraAgendamento());
        assertEquals(0, valor.compareTo(consulta.getValor()));
        assertEquals(StatusConsulta.AGENDADA, consulta.getStatus());
        assertEquals(ModalidadeConsulta.PRESENCIAL, consulta.getModalidade());
    }

    @Test
    void deveBuscarConsultaPorIdExistente() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                pacienteRepository,
                medicoRepository);

        Consulta consultaCadastrada = service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                ModalidadeConsulta.PRESENCIAL);

        Consulta consultaEncontrada = service.buscarPorId(1);

        assertSame(consultaCadastrada, consultaEncontrada);
    }

    @Test
    void deveRetornarNullAoBuscarConsultaPorIdInexistente() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository());

        Consulta consulta = service.buscarPorId(999);

        assertNull(consulta);
    }

    @Test
    void deveListarTodasAsConsultasCadastradas() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                pacienteRepository,
                medicoRepository);

        service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                ModalidadeConsulta.PRESENCIAL);

        service.agendar(
                1,
                1,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA);

        List<Consulta> consultas = service.listarTodos();

        assertNotNull(consultas);
        assertEquals(2, consultas.size());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistemConsultas() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository());

        List<Consulta> consultas = service.listarTodos();

        assertNotNull(consultas);
        assertTrue(consultas.isEmpty());
    }

    @Test
    void deveRejeitarAtualizacaoDeConsultaCancelada() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.CANCELADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                1,
                1,
                1,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA));
    }

    @Test
    void deveRejeitarAtualizacaoDeConsultaRealizada() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.REALIZADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                1,
                1,
                1,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA));
    }

    @Test
    void deveAtualizarConsultaAgendada() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        Consulta consultaAtualizada = service.atualizar(
                1,
                1,
                1,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA);

        assertNotNull(consultaAtualizada);
        assertEquals(LocalDateTime.of(2026, 12, 11, 15, 30),
                consultaAtualizada.getDataHoraAgendamento());
        assertEquals(new BigDecimal("300.00"), consultaAtualizada.getValor());
        assertEquals(ModalidadeConsulta.TELEMEDICINA,
                consultaAtualizada.getModalidade());
        assertEquals(StatusConsulta.AGENDADA, consultaAtualizada.getStatus());
    }

    @Test
    void deveRetornarNullAoAtualizarConsultaInexistente() {
        ConsultaService service = new ConsultaService(
                new ConsultaRepository(),
                new PacienteRepository(),
                new MedicoRepository());

        Consulta resultado = service.atualizar(
                999,
                1,
                1,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA);

        assertNull(resultado);
    }

    @Test
    void deveRejeitarAtualizacaoQuandoPacienteNaoExiste() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        consultaRepository.salvar(
                pacienteRepository.salvar(
                        "Joao Silva",
                        "12345678901",
                        LocalDate.of(1990, 5, 10),
                        "M",
                        "31999999999",
                        null),
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                1,
                999,
                1,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA));
    }

    @Test
    void deveRejeitarAtualizacaoQuandoMedicoNaoExiste() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                1,
                1,
                999,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA));
    }

    @Test
    void deveRejeitarAtualizacaoQuandoMedicoEstaInativo() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        medicoRepository.inativar(medico.getId());

        ConsultaRepository consultaRepository = new ConsultaRepository();

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                1,
                1,
                1,
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA));
    }

    @Test
    void devePreservarConsultaOriginalQuandoAtualizacaoForInvalida() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        Consulta original = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                original.getId(),
                999,
                medico.getId(),
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA));

        Consulta consultaAtual = consultaRepository.buscarPorId(original.getId());

        assertNotNull(consultaAtual);
        assertEquals(LocalDateTime.of(2026, 12, 10, 14, 30),
                consultaAtual.getDataHoraAgendamento());
        assertEquals(new BigDecimal("250.00"), consultaAtual.getValor());
        assertEquals(ModalidadeConsulta.PRESENCIAL, consultaAtual.getModalidade());
        assertEquals(StatusConsulta.AGENDADA, consultaAtual.getStatus());
        assertEquals(paciente.getId(), consultaAtual.getPaciente().getId());
        assertEquals(medico.getId(), consultaAtual.getMedico().getId());
    }

    @Test
    void deveAtualizarPacienteEMedicoDaConsulta() {
        PacienteRepository pacienteRepository = new PacienteRepository();

        Paciente pacienteOriginal = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        Paciente novoPaciente = pacienteRepository.salvar(
                "Maria Souza",
                "98765432100",
                LocalDate.of(1985, 8, 20),
                "F",
                "31977777777",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medicoOriginal = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        Medico novoMedico = medicoRepository.salvar(
                "Ana Lima",
                "CRM456",
                "ana@email.com",
                "31966666666",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        Consulta original = consultaRepository.salvar(
                pacienteOriginal,
                medicoOriginal,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        Consulta atualizada = service.atualizar(
                original.getId(),
                novoPaciente.getId(),
                novoMedico.getId(),
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("300.00"),
                ModalidadeConsulta.TELEMEDICINA);

        assertNotNull(atualizada);
        assertEquals(novoPaciente.getId(), atualizada.getPaciente().getId());
        assertEquals(novoMedico.getId(), atualizada.getMedico().getId());
    }

    @Test
    void deveRejeitarAtualizacaoComValorNegativo() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        Consulta original = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                original.getId(),
                paciente.getId(),
                medico.getId(),
                LocalDateTime.of(2026, 12, 11, 15, 30),
                new BigDecimal("-10.00"),
                ModalidadeConsulta.TELEMEDICINA));

        Consulta consultaAtual = consultaRepository.buscarPorId(original.getId());

        assertEquals(new BigDecimal("250.00"), consultaAtual.getValor());
        assertEquals(ModalidadeConsulta.PRESENCIAL, consultaAtual.getModalidade());
    }

    @Test
    void deveCancelarConsultaAgendada() {
        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        MedicoRepository medicoRepository = new MedicoRepository();
        Especialidade especialidade = new Especialidade(
                1,
                "Cardiologia",
                "Atendimento cardiologico");

        Medico medico = medicoRepository.salvar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade);

        ConsultaRepository consultaRepository = new ConsultaRepository();

        Consulta original = consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 12, 10, 14, 30),
                new BigDecimal("250.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        ConsultaService service = new ConsultaService(
                consultaRepository,
                pacienteRepository,
                medicoRepository);

        boolean cancelada = service.cancelar(original.getId());

        assertTrue(cancelada);

        Consulta consultaCancelada = consultaRepository.buscarPorId(original.getId());

        assertNotNull(consultaCancelada);
        assertEquals(StatusConsulta.CANCELADA, consultaCancelada.getStatus());
    }
}