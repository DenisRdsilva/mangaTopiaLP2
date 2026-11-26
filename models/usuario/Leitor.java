package models.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leitor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String email;
    private String senha;
    private int idade;
    private String enderecoEntrega;
    private int pontosFidelidade;
    private List<String> historicoLeituraIds;

    public Leitor(String id, String nome, String email, String senha, int idade, String enderecoEntrega) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do leitor não pode ser vazio.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do leitor não pode ser vazio.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        if (senha == null || senha.length() < 4) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 4 caracteres.");
        }
        if (idade < 0 || idade > 120) {
            throw new IllegalArgumentException("Idade inválida.");
        }

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.enderecoEntrega = enderecoEntrega;
        this.pontosFidelidade = 0;
        this.historicoLeituraIds = new ArrayList<>();
    }

    public boolean validarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    public void adicionarPontos(int pontos) {
        if (pontos > 0) {
            this.pontosFidelidade += pontos;
        }
    }

    public void resgatarPontos(int pontos) {
        if (pontos <= 0 || pontos > this.pontosFidelidade) {
            throw new IllegalArgumentException("Saldo de pontos insuficiente.");
        }
        this.pontosFidelidade -= pontos;
    }

    public void registrarLeituraCapitulo(String capituloId) {
        if (capituloId != null && !historicoLeituraIds.contains(capituloId)) {
            historicoLeituraIds.add(capituloId);
        }
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 120) {
            throw new IllegalArgumentException("Idade inválida.");
        }
        this.idade = idade;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public int getPontosFidelidade() {
        return pontosFidelidade;
    }

    public List<String> getHistoricoLeituraIds() {
        return Collections.unmodifiableList(historicoLeituraIds);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%d anos) - Pontos: %d | Endereço: %s",
                id, nome, idade, pontosFidelidade, enderecoEntrega);
    }
}