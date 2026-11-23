
package br.com.gestaoclinica.model;

public class Especialidade {

    private int id;
    private String nome;
    private String descricao;

    public Especialidade(int id, String nome, String descricao) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da especialidade e obrigatorio.");
        }

        if (descricao == null) {
            throw new IllegalArgumentException("A descricao da especialidade e obrigatoria.");
        }

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}