package br.com.gestaoclinica.service;

import br.com.gestaoclinica.model.Especialidade;
import br.com.gestaoclinica.model.Medico;
import br.com.gestaoclinica.model.ModalidadeConsulta;
import br.com.gestaoclinica.model.Paciente;
import br.com.gestaoclinica.model.StatusConsulta;
import br.com.gestaoclinica.repository.ConsultaRepository;
import br.com.gestaoclinica.repository.EspecialidadeRepository;
import br.com.gestaoclinica.repository.MedicoRepository;
import br.com.gestaoclinica.repository.PacienteRepository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

class MedicoServiceTest {

    @Test
    void deveCadastrarMedicoComDadosValidos() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico resultado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Joao Silva", resultado.getNome());
        assertEquals("CRM12345", resultado.getCrm());
        assertEquals("Cardiologia",
                resultado.getEspecialidade().getNome());
        assertEquals("S", resultado.getAtivo());
    }

    @Test
    void naoDeveCadastrarMedicoComCrmDuplicado() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(
                "Maria Souza",
                "CRM12345",
                "maria@email.com",
                "31988888888",
                especialidade.getId()));

        assertEquals(1, medicoRepository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarMedicoComCrmDiferenciandoMaiusculas() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(
                "Maria Souza",
                "crm12345",
                "maria@email.com",
                "31988888888",
                especialidade.getId()));

        assertEquals(1, medicoRepository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarMedicoComCrmDiferenciadoPorEspacos() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(
                "Maria Souza",
                "  CRM12345  ",
                "maria@email.com",
                "31988888888",
                especialidade.getId()));

        assertEquals(1, medicoRepository.listarTodos().size());
    }

    @Test
    void naoDeveCadastrarMedicoComEspecialidadeInexistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                999));

        assertEquals(0, medicoRepository.listarTodos().size());
    }

    @Test
    void deveBuscarMedicoPorId() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        Medico resultado = service.buscarPorId(cadastrado.getId());

        assertNotNull(resultado);
        assertEquals(cadastrado.getId(), resultado.getId());
        assertEquals("Joao Silva", resultado.getNome());
        assertEquals("CRM12345", resultado.getCrm());
    }

    @Test
    void deveRetornarNullAoBuscarMedicoComIdInexistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico resultado = service.buscarPorId(999);

        assertEquals(null, resultado);
    }

    @Test
    void deveBuscarMedicoCorretoEntreVariosCadastrados() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        Medico segundoMedico = service.cadastrar(
                "Maria Souza",
                "CRM67890",
                "maria@email.com",
                "31988888888",
                especialidade.getId());

        service.cadastrar(
                "Pedro Santos",
                "CRM54321",
                "pedro@email.com",
                "31977777777",
                especialidade.getId());

        Medico resultado = service.buscarPorId(segundoMedico.getId());

        assertEquals(segundoMedico.getId(), resultado.getId());
        assertEquals("Maria Souza", resultado.getNome());
        assertEquals("CRM67890", resultado.getCrm());
    }

    @Test
    void deveListarTodosOsMedicosCadastrados() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        service.cadastrar(
                "Maria Souza",
                "CRM67890",
                "maria@email.com",
                "31988888888",
                especialidade.getId());

        var resultado = service.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals(
                java.util.Set.of("Joao Silva", "Maria Souza"),
                resultado.stream()
                        .map(Medico::getNome)
                        .collect(java.util.stream.Collectors.toSet()));
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverMedicosCadastrados() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        var resultado = service.listarTodos();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void deveListarUmMedicoCadastrado() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        var resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals(cadastrado.getId(), resultado.get(0).getId());
        assertEquals("Joao Silva", resultado.get(0).getNome());
        assertEquals("CRM12345", resultado.get(0).getCrm());
    }

    @Test
    void deveAtualizarMedicoComDadosValidos() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade cardiologia = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        Especialidade neurologia = especialidadeRepository.salvar(
                "Neurologia",
                "Especialidade do sistema nervoso");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                cardiologia.getId());

        Medico resultado = service.atualizar(
                cadastrado.getId(),
                "Joao Silva Santos",
                "CRM54321",
                "joao.santos@email.com",
                "31988888888",
                neurologia.getId());

        assertEquals(cadastrado.getId(), resultado.getId());
        assertEquals("Joao Silva Santos", resultado.getNome());
        assertEquals("CRM54321", resultado.getCrm());
        assertEquals("joao.santos@email.com", resultado.getEmail());
        assertEquals("31988888888", resultado.getTelefone());
        assertEquals(neurologia.getId(),
                resultado.getEspecialidade().getId());
        assertEquals("S", resultado.getAtivo());
    }

    @Test
    void naoDeveAtualizarMedicoComCrmDeOutroMedico() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico primeiro = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        Medico segundo = service.cadastrar(
                "Maria Souza",
                "CRM67890",
                "maria@email.com",
                "31988888888",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                segundo.getId(),
                "Maria Souza",
                primeiro.getCrm(),
                "maria@email.com",
                "31988888888",
                especialidade.getId()));

        assertEquals("CRM67890",
                medicoRepository.buscarPorId(segundo.getId()).getCrm());
    }

    @Test
    void naoDeveAtualizarMedicoComEspecialidadeInexistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                cadastrado.getId(),
                "Joao Silva Santos",
                "CRM54321",
                "joao.santos@email.com",
                "31988888888",
                999));

        Medico mantido = medicoRepository.buscarPorId(cadastrado.getId());

        assertEquals("Joao Silva", mantido.getNome());
        assertEquals("CRM12345", mantido.getCrm());
        assertEquals(especialidade.getId(),
                mantido.getEspecialidade().getId());
    }

    @Test
    void devePermitirAtualizarMedicoMantendoProprioCrm() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        Medico resultado = service.atualizar(
                cadastrado.getId(),
                "Joao Silva Santos",
                cadastrado.getCrm(),
                "joao.santos@email.com",
                "31988888888",
                especialidade.getId());

        assertEquals(cadastrado.getId(), resultado.getId());
        assertEquals("Joao Silva Santos", resultado.getNome());
        assertEquals("CRM12345", resultado.getCrm());
        assertEquals("joao.santos@email.com", resultado.getEmail());
    }

    @Test
    void deveRetornarNullAoAtualizarMedicoComIdInexistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico resultado = service.atualizar(
                999,
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertEquals(null, resultado);
        assertEquals(0, medicoRepository.listarTodos().size());
    }

    @Test
    void naoDeveAtualizarMedicoComNomeInvalido() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                cadastrado.getId(),
                " ",
                "CRM54321",
                "joao.santos@email.com",
                "31988888888",
                especialidade.getId()));

        Medico mantido = medicoRepository.buscarPorId(cadastrado.getId());

        assertEquals("Joao Silva", mantido.getNome());
        assertEquals("CRM12345", mantido.getCrm());
    }

    @Test
    void naoDeveAtualizarMedicoComCrmInvalido() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                cadastrado.getId(),
                "Joao Silva",
                " ",
                "joao.santos@email.com",
                "31988888888",
                especialidade.getId()));

        Medico mantido = medicoRepository.buscarPorId(cadastrado.getId());

        assertEquals("Joao Silva", mantido.getNome());
        assertEquals("CRM12345", mantido.getCrm());
    }

    @Test
    void naoDeveAtualizarMedicoComEmailInvalido() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                cadastrado.getId(),
                "Joao Silva",
                "CRM12345",
                " ",
                "31988888888",
                especialidade.getId()));

        Medico mantido = medicoRepository.buscarPorId(cadastrado.getId());

        assertEquals("Joao Silva", mantido.getNome());
        assertEquals("joao@email.com", mantido.getEmail());
        assertEquals("31999999999", mantido.getTelefone());
    }

    @Test
    void naoDeveAtualizarMedicoComTelefoneInvalido() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(
                cadastrado.getId(),
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                " ",
                especialidade.getId()));

        Medico mantido = medicoRepository.buscarPorId(cadastrado.getId());

        assertEquals("Joao Silva", mantido.getNome());
        assertEquals("joao@email.com", mantido.getEmail());
        assertEquals("31999999999", mantido.getTelefone());
    }

    @Test
    void deveExcluirMedicoExistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        boolean resultado = service.excluir(cadastrado.getId());

        assertEquals(true, resultado);
        assertEquals(0, service.listarTodos().size());
    }

    @Test
    void deveRetornarFalseAoExcluirMedicoInexistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        boolean resultado = service.excluir(999);

        assertEquals(false, resultado);
        assertEquals(0, service.listarTodos().size());
    }

    @Test
    void deveRetornarFalseAoExcluirMedicoDuasVezes() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        assertEquals(true, service.excluir(cadastrado.getId()));
        assertEquals(false, service.excluir(cadastrado.getId()));
        assertEquals(0, service.listarTodos().size());
    }

    @Test
    void deveInativarMedicoAtivo() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        Medico cadastrado = service.cadastrar(
                "Joao Silva",
                "CRM12345",
                "joao@email.com",
                "31999999999",
                especialidade.getId());

        boolean resultado = service.inativar(cadastrado.getId());

        assertEquals(true, resultado);

        Medico atualizado = service.buscarPorId(cadastrado.getId());

        assertEquals("N", atualizado.getAtivo());
        assertEquals("Joao Silva", atualizado.getNome());
        assertEquals("CRM12345", atualizado.getCrm());
        assertEquals("joao@email.com", atualizado.getEmail());
        assertEquals("31999999999", atualizado.getTelefone());
    }

    @Test
    void deveRetornarFalseAoInativarMedicoInexistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        boolean resultado = service.inativar(999);

        assertEquals(false, resultado);
        assertEquals(0, service.listarTodos().size());
    }

    @Test
    void deveRetornarFalseAoInativarMedicoJaInativo() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade medica");

        Medico medico = medicoRepository.salvar(
                "Joao Silva",
                "CRM123",
                "joao@email.com",
                "31999999999",
                especialidade);

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        service.inativar(medico.getId());

        boolean resultado = service.inativar(medico.getId());

        assertEquals(false, resultado);
        assertEquals("N", service.buscarPorId(medico.getId()).getAtivo());
    }

    @Test
    void deveRetornarFalseAoAtivarMedicoInexistente() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        MedicoRepository medicoRepository = new MedicoRepository();

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        boolean resultado = service.ativar(999);

        assertEquals(false, resultado);
        assertEquals(0, service.listarTodos().size());
    }

    @Test
    void deveRetornarFalseAoAtivarMedicoJaAtivo() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Especialidade medica");

        MedicoRepository medicoRepository = new MedicoRepository();

        Medico medico = medicoRepository.salvar(
                "Joao Silva",
                "CRM123",
                "joao@email.com",
                "31999999999",
                especialidade);

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                new ConsultaRepository());

        boolean resultado = service.ativar(medico.getId());

        assertEquals(false, resultado);
        assertEquals("S", service.buscarPorId(medico.getId()).getAtivo());
    }

    @Test
    void deveRejeitarExclusaoDeMedicoComConsultaVinculada() {
        EspecialidadeRepository especialidadeRepository = new EspecialidadeRepository();
        MedicoRepository medicoRepository = new MedicoRepository();
        ConsultaRepository consultaRepository = new ConsultaRepository();

        Especialidade especialidade = especialidadeRepository.salvar(
                "Cardiologia",
                "Tratamento do coracao");

        MedicoService service = new MedicoService(
                medicoRepository,
                especialidadeRepository,
                consultaRepository);

        Medico medico = service.cadastrar(
                "Carlos Souza",
                "CRM123",
                "carlos@email.com",
                "31988888888",
                especialidade.getId());

        PacienteRepository pacienteRepository = new PacienteRepository();
        Paciente paciente = pacienteRepository.salvar(
                "Joao Silva",
                "12345678901",
                LocalDate.of(1990, 5, 10),
                "M",
                "31999999999",
                null);

        consultaRepository.salvar(
                paciente,
                medico,
                LocalDateTime.of(2026, 11, 26, 10, 0),
                new BigDecimal("200.00"),
                StatusConsulta.AGENDADA,
                ModalidadeConsulta.PRESENCIAL);

        assertThrows(IllegalArgumentException.class,
                () -> service.excluir(medico.getId()));

        assertNotNull(service.buscarPorId(medico.getId()));
    }
}
