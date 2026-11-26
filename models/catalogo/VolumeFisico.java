package models.catalogo;

// import exception.pedido.EstoqueInsuficienteException;
// import models.enums.TipoCapa;

public class VolumeFisico extends ItemCatalogo {
    private static final long serialVersionUID = 1L;

    private int numeroVolume;
    private double pesoGramas;
    private String dimensoes;
    private int quantidadeEstoque;

    public VolumeFisico(String id, Manga manga, double precoBase, int numeroVolume,
                        double pesoGramas, String dimensoes, int quantidadeEstoque) {
        super(id, manga, precoBase);
        if (numeroVolume <= 0) {
            throw new IllegalArgumentException("O número do volume deve ser maior que zero.");
        }
        if (pesoGramas <= 0) {
            throw new IllegalArgumentException("O peso do volume deve ser maior que zero.");
        }
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("O estoque inicial não pode ser negativo.");
        }

        this.numeroVolume = numeroVolume;
        this.pesoGramas = pesoGramas;
        this.dimensoes = dimensoes;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    /**
     * Polimorfismo: calcula o preço final somando o frete baseado no peso
     * e um adicional caso seja edição de capa dura ou colecionador.
     */
    @Override
    public double calcularPrecoFinal() {
        double custoFrete = this.pesoGramas * 0.02; // R$ 0,02 por grama
        double adicionalCapa = 0.0;

        return getPrecoBase() + custoFrete + adicionalCapa;
    }

    @Override
    public String getTitulo() {
        return String.format("%s - Vol. %d (%s)", getManga().getTitulo(), numeroVolume);
    }

    // --- Controle de Estoque ---

    // public void decrementarEstoque(int quantidade) throws EstoqueInsuficienteException {
    //     if (quantidade <= 0) {
    //         throw new IllegalArgumentException("A quantidade para baixa deve ser positiva.");
    //     }
    //     // if (this.quantidadeEstoque < quantidade) {
    //     //     throw new EstoqueInsuficienteException(
    //     //         "Estoque insuficiente para " + getTitulo() + ". Disponível: " + this.quantidadeEstoque
    //     //     );
    //     // }
    //     this.quantidadeEstoque -= quantidade;
    // }

    public void incrementarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para reposição deve ser positiva.");
        }
        this.quantidadeEstoque += quantidade;
    }

    // Getters e Setters
    public int getNumeroVolume() {
        return numeroVolume;
    }

    public double getPesoGramas() {
        return pesoGramas;
    }

    public String getDimensoes() {
        return dimensoes;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
}