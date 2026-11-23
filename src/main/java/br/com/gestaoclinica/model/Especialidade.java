
package br.com.gestaoclinica.model;

public class Especialidade {

    private int id;
    private String nome;
    private String descricao;

    public Especialidade(int id, String nome, String descricao) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        if (nome == null) {
            throw new IllegalArgumentException("O nome da especialidade e obrigatorio.");
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