package models.usuario;

import models.enums.PerfilUsuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id = UUID.randomUUID().toString();
    private String nome;
    private String email;
    private String senhaHash;
    private LocalDate dataNascimento;

    protected Usuario(String nome, String email, String senhaHash, LocalDate dataNascimento) {
        setNome(nome);
        setEmail(email);
        this.senhaHash = senhaHash;
        this.dataNascimento = dataNascimento;
    }

    
    public abstract PerfilUsuario getPerfil();

    public boolean senhaConfere(String hashInformado) {
        return senhaHash.equals(hashInformado);
    }

    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public LocalDate getDataNascimento() { return dataNascimento; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        this.nome = nome.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new IllegalArgumentException("E-mail inválido.");
        this.email = email.trim().toLowerCase();
    }

    public void alterarSenha(String novoHash) {
        this.senhaHash = novoHash;
    }
}