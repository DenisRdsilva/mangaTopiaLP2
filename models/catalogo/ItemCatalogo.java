package models.catalogo;

import java.io.Serializable;

public abstract class ItemCatalogo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Manga manga;
    private double precoBase;

    public ItemCatalogo(String id, Manga manga, double precoBase) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID do item de catálogo não pode ser vazio.");
        }
        if (manga == null) {
            throw new IllegalArgumentException("O item de catálogo deve estar vinculado a um mangá.");
        }
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo.");
        }
        this.id = id;
        this.manga = manga;
        this.precoBase = precoBase;
    }

    /**
     * Método polimórfico obrigatório:
     * - VolumeFisico sobrescreve somando o custo do frete baseado no peso.
     * - CapituloDigital sobrescreve aplicando regras de mídia digital.
     */
    public abstract double calcularPrecoFinal();

    /**
     * Retorna uma descrição resumida do item para exibição no carrinho e recibos.
     */
    public String getTitulo() {
        return manga.getTitulo();
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        if (manga == null) {
            throw new IllegalArgumentException("O mangá não pode ser nulo.");
        }
        this.manga = manga;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo.");
        }
        this.precoBase = precoBase;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Preço Base: R$ %.2f", id, manga.getTitulo(), precoBase);
    }
}