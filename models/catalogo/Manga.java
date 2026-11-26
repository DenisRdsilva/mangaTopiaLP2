package models.catalogo;

import java.io.Serializable;
// import models.enums.GeneroManga;

public class Manga implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String titulo;
    private String autor;
    // private GeneroManga genero;
    private int faixaEtaria;
    private String sinopse;
    private boolean emPublicacao;

    // Construtor principal (utilizado pelo CatalogoRepository ao carregar o JSON)
    public Manga(String id, String titulo, String autor, int faixaEtaria) {
        this(id, titulo, autor, faixaEtaria, "Sinopse não informada.", true);
    }

    // Construtor completo
    public Manga(String id, String titulo, String autor, int faixaEtaria, String sinopse, boolean emPublicacao) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do mangá não pode ser vazio.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do mangá não pode ser vazio.");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor do mangá não pode ser vazio.");
        }
        // if (genero == null) {
        //     throw new IllegalArgumentException("O gênero do mangá não pode ser nulo.");
        // }
        if (faixaEtaria < 0 || faixaEtaria > 18) {
            throw new IllegalArgumentException("A faixa etária deve estar entre 0 (Livre) e 18 anos.");
        }

        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        // this.genero = genero;
        this.faixaEtaria = faixaEtaria;
        this.sinopse = sinopse;
        this.emPublicacao = emPublicacao;
    }

    // Regra de domínio: verifica se um leitor tem idade mínima para ler a obra
    public boolean isPermitidoParaIdade(int idadeLeitor) {
        return idadeLeitor >= this.faixaEtaria;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ser vazio.");
        }
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O autor não pode ser vazio.");
        }
        this.autor = autor;
    }

    // public GeneroManga getGenero() {
    //     return genero;
    // }

    // public void setGenero(GeneroManga genero) {
    //     if (genero == null) {
    //         throw new IllegalArgumentException("O gênero não pode ser nulo.");
    //     }
    //     this.genero = genero;
    // }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        if (faixaEtaria < 0 || faixaEtaria > 18) {
            throw new IllegalArgumentException("A faixa etária deve estar entre 0 e 18 anos.");
        }
        this.faixaEtaria = faixaEtaria;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public boolean isEmPublicacao() {
        return emPublicacao;
    }

    public void setEmPublicacao(boolean emPublicacao) {
        this.emPublicacao = emPublicacao;
    }

    @Override
    public String toString() {
        String classificacao = (faixaEtaria == 0) ? "Livre" : "+" + faixaEtaria;
        String status = emPublicacao ? "Em lançamento" : "Concluído";
        return String.format("[%s] %s - Autor: %s | Gênero: %s | Classificação: %s (%s)",
                id, titulo, autor, classificacao, status);
    }
}