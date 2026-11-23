package br.com.gestaoclinica.model;

public class Medico {

    private int id;
    private String nome;
    private String crm;
    private String email;
    private String telefone;
    private Especialidade especialidade;
    private String ativo;

    public Medico(int id, String nome, String crm, String email,
            String telefone, Especialidade especialidade) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do medico deve ser maior que zero.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "O nome do medico e obrigatorio.");
        }
        if (crm == null || crm.isBlank()) {
            throw new IllegalArgumentException(
                    "O CRM do medico e obrigatorio.");
        }

        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.email = email;
        this.telefone = telefone;
        this.especialidade = especialidade;
        this.ativo = "S";
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCrm() {
        return crm;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public String getAtivo() {
        return ativo;
    }
}