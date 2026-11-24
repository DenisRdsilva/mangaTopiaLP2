package mangaTopiaLP2.models.pedido;

import java.io.Serializable;
import mangaTopiaLP2.models.catalogo.ItemCatalogo;

public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private ItemCatalogo item;
    private int quantidade;
    private double precoUnitarioAplicado;

    public ItemPedido(ItemCatalogo item, int quantidade) {
        if (item == null) {
            throw new IllegalArgumentException("O item do catálogo não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.item = item;
        this.quantidade = quantidade;
        // Polimorfismo: chama calcularPrecoFinal() de VolumeFisico (com frete) ou CapituloDigital
        this.precoUnitarioAplicado = item.calcularPrecoFinal();
    }

    public double calcularSubtotal() {
        return this.precoUnitarioAplicado * this.quantidade;
    }

    // Getters e Setters
    public ItemCatalogo getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    public double getPrecoUnitarioAplicado() {
        return precoUnitarioAplicado;
    }

    @Override
    public String toString() {
        return String.format("%dx %s (Unit: R$ %.2f | Subtotal: R$ %.2f)",
                quantidade, item.getTitulo(), precoUnitarioAplicado, calcularSubtotal());
    }
}
