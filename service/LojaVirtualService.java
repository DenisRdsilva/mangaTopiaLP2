package service;

import java.util.List;
import java.util.UUID;

import exception.MangaTopiaException;
// import exception.pedido.EstoqueInsuficienteException;
import models.catalogo.ItemCatalogo;
// import models.catalogo.VolumeFisico;
import models.pedido.ItemPedido;
import models.pedido.Pedido;
// import models.promocao.CupomDesconto;
import models.usuario.Leitor;
import repository.dados.CatalogoRepository;
import repository.dados.PedidoRepository;

public class LojaVirtualService {
    private PedidoRepository pedidoRepo;
    private CatalogoRepository catalogoRepo;

    public LojaVirtualService(PedidoRepository pedidoRepo, CatalogoRepository catalogoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.catalogoRepo = catalogoRepo;
    }

    public List<ItemCatalogo> listarCatalogoDisponivel() {
        return catalogoRepo.listarTodos();
    }

    public Pedido criarNovoPedido(Leitor leitor) {
        String idCurto = "PED-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        return new Pedido(idCurto, leitor);
    }

    public void adicionarProdutoAoPedido(Pedido pedido, String idItemCatalogo, int quantidade) throws MangaTopiaException {
        ItemCatalogo item = catalogoRepo.buscarPorId(idItemCatalogo);
        if (item == null) {
            throw new MangaTopiaException("Produto com ID '" + idItemCatalogo + "' não encontrado no catálogo.");
        }

        // if (item instanceof VolumeFisico vf) {
        //     int jaNoCarrinho = pedido.getItens().stream()
        //             .filter(i -> i.getItem().getId().equals(idItemCatalogo))
        //             .mapToInt(ItemPedido::getQuantidade)
        //             .sum();

        //     if ((jaNoCarrinho + quantidade) > vf.getQuantidadeEstoque()) {
        //         throw new EstoqueInsuficienteException(
        //             String.format("Estoque insuficiente para '%s'. Disponível: %d unidade(s).",
        //             vf.getManga().getTitulo(), vf.getQuantidadeEstoque())
        //         );
        //     }
        // }

        ItemPedido itemPedido = new ItemPedido(item, quantidade);
        pedido.adicionarItem(itemPedido);
    }

    // public void aplicarCupomNoPedido(Pedido pedido, CupomDesconto cupom) throws MangaTopiaException {
    //     try {
    //         pedido.aplicarCupom(cupom);
    //     } catch (IllegalStateException e) {
    //         throw new MangaTopiaException(e.getMessage());
    //     }
    // }

    public void finalizarEPagarPedido(Pedido pedido) throws MangaTopiaException {
        if (pedido.getItens().isEmpty()) {
            throw new MangaTopiaException("Adicione ao menos um produto antes de finalizar o pedido.");
        }

        // for (ItemPedido itemPedido : pedido.getItens()) {
        //     if (itemPedido.getItem() instanceof VolumeFisico vf) {
        //         if (vf.getQuantidadeEstoque() < itemPedido.getQuantidade()) {
        //             throw new EstoqueInsuficienteException("O item " + vf.getManga().getTitulo() + " esgotou durante a compra.");
        //         }
        //         vf.decrementarEstoque(itemPedido.getQuantidade());
        //     }
        // }

        // pedido.confirmarPagamento();

        int pontosGanhos = (int) (pedido.calcularValorFinal() / 10);
        pedido.getLeitor().adicionarPontos(pontosGanhos);

        catalogoRepo.atualizar();
        pedidoRepo.salvar(pedido);
    }

    // public void cancelarPedido(String pedidoId, Leitor leitor) throws MangaTopiaException {
    //     Pedido pedido = pedidoRepo.buscarPorId(pedidoId);
    //     if (pedido == null || !pedido.getLeitor().getId().equals(leitor.getId())) {
    //         throw new MangaTopiaException("Pedido não encontrado para este usuário.");
    //     }

    //     boolean estavaPago = (pedido.getEstado().name().equals("PAGO"));
    //     pedido.cancelar();

    //     if (estavaPago) {
    //         for (ItemPedido itemPedido : pedido.getItens()) {
    //             if (itemPedido.getItem() instanceof VolumeFisico vf) {
    //                 vf.incrementarEstoque(itemPedido.getQuantidade());
    //             }
    //         }
    //         catalogoRepo.atualizar();
    //     }
    // }

    public List<Pedido> listarPedidosDoLeitor(String leitorId) {
        return pedidoRepo.listarPorLeitor(leitorId);
    }
}