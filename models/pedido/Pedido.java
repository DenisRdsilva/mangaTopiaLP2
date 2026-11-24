package mangaTopiaLP2.models.pedido;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mangaTopiaLP2.exception.pedido.CancelamentoPedidoInvalidoException;
import mangaTopiaLP2.models.enums.EstadoPedido;
import mangaTopiaLP2.models.promocao.CupomDesconto;
import mangaTopiaLP2.models.usuario.Leitor;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Leitor leitor;
    private List<ItemPedido> itens;
    private LocalDateTime dataCriacao;
    private EstadoPedido estado;
    private CupomDesconto cupomAplicado;
    private double valorDesconto;

    public Pedido(String id, Leitor leitor) {
        this.id = id;
        this.leitor = leitor;
        this.itens = new ArrayList<>();
        this.dataCriacao = LocalDateTime.now();
        this.estado = EstadoPedido.AGUARDANDO_PAGAMENTO;
        this.valorDesconto = 0.0;
    }

    // --- Gestão de Itens ---

    public void adicionarItem(ItemPedido novoItem) {
        validarPedidoModificavel();
        // Se o item já existir no pedido, apenas incrementa a quantidade
        for (ItemPedido itemExistente : itens) {
            if (itemExistente.getItem().getId().equals(novoItem.getItem().getId())) {
                itemExistente.setQuantidade(itemExistente.getQuantidade() + novoItem.getQuantidade());
                return;
            }
        }
        this.itens.add(novoItem);
    }

    public void removerItem(String itemCatalogoId) {
        validarPedidoModificavel();
        this.itens.removeIf(i -> i.getItem().getId().equals(itemCatalogoId));
    }

    private void validarPedidoModificavel() {
        if (this.estado != EstadoPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Não é possível alterar itens de um pedido com status: " + this.estado);
        }
    }

    // --- Cálculos Financeiros e Polimorfismo ---

    public double calcularValorBruto() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void aplicarCupom(CupomDesconto cupom) {
        validarPedidoModificavel();
        if (this.cupomAplicado != null) {
            throw new IllegalStateException("Este pedido já possui um cupom aplicado.");
        }
        double bruto = calcularValorBruto();
        // Polimorfismo: delega o cálculo do desconto à implementação específica do cupom
        double descontoCalculado = cupom.aplicarDesconto(bruto);
        
        // Regra de negócio: teto máximo de 40% de desconto
        double tetoMaximo = bruto * 0.40;
        this.valorDesconto = Math.min(descontoCalculado, tetoMaximo);
        this.cupomAplicado = cupom;
    }

    public double calcularValorFinal() {
        double finalComDesconto = calcularValorBruto() - this.valorDesconto;
        return Math.max(0.0, finalComDesconto);
    }

    // --- Máquina de Estados Dinâmicos ---

    public void confirmarPagamento() {
        if (this.estado != EstadoPedido.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Apenas pedidos aguardando pagamento podem ser pagos. Estado atual: " + this.estado);
        }
        if (this.itens.isEmpty()) {
            throw new IllegalStateException("Não é possível pagar um pedido vazio.");
        }
        this.estado = EstadoPedido.PAGO;
    }

    public void marcarComoEnviado() {
        if (this.estado != EstadoPedido.PAGO) {
            throw new IllegalStateException("O pedido precisa estar PAGO antes de ser enviado. Estado atual: " + this.estado);
        }
        this.estado = EstadoPedido.ENVIADO;
    }

    public void confirmarEntrega() {
        if (this.estado != EstadoPedido.ENVIADO) {
            throw new IllegalStateException("O pedido precisa ter sido ENVIADO antes de ser entregue. Estado atual: " + this.estado);
        }
        this.estado = EstadoPedido.ENTREGUE;
    }

    public void cancelar() throws CancelamentoPedidoInvalidoException {
        if (this.estado == EstadoPedido.ENVIADO || this.estado == EstadoPedido.ENTREGUE) {
            throw new CancelamentoPedidoInvalidoException(
                "Não é possível cancelar um pedido que já foi " + this.estado + "."
            );
        }
        if (this.estado == EstadoPedido.CANCELADO) {
            throw new CancelamentoPedidoInvalidoException("Este pedido já se encontra cancelado.");
        }
        this.estado = EstadoPedido.CANCELADO;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public List<ItemPedido> getItens() {
        // Retorna lista imutável para proteger o encapsulamento
        return Collections.unmodifiableList(itens);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public CupomDesconto getCupomAplicado() {
        return cupomAplicado;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Pedido #%s [%s] ===\n", id, estado));
        sb.append("Cliente: ").append(leitor.getNome()).append(" | Data: ").append(dataCriacao.format(fmt)).append("\n");
        for (ItemPedido item : itens) {
            sb.append("  - ").append(item.toString()).append("\n");
        }
        sb.append(String.format("Subtotal: R$ %.2f | Desconto: R$ %.2f | Total Final: R$ %.2f",
                calcularValorBruto(), valorDesconto, calcularValorFinal()));
        return sb.toString();
    }
}