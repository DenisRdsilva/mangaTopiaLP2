package view.leitor;

import java.util.List;
import java.util.Scanner;

import exception.MangaTopiaException;
import models.catalogo.ItemCatalogo;
import models.catalogo.VolumeFisico;
import models.pedido.Pedido;
import models.usuario.Leitor;
import service.LojaVirtualService;

public class ViewLojaCarrinho {
    private Scanner scanner;
    private LojaVirtualService lojaService;

    public ViewLojaCarrinho(Scanner scanner, LojaVirtualService lojaService) {
        this.scanner = scanner;
        this.lojaService = lojaService;
    }

    public void exibirMenuLoja(Leitor leitorLogado) {
        Pedido carrinhoAtual = lojaService.criarNovoPedido(leitorLogado);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==============================================");
            System.out.println("       LOJA FÍSICA MANGATOPIA - CARRINHO      ");
            System.out.println("==============================================");
            System.out.printf("Carrinho atual: %d item(ns) | Total: R$ %.2f\n",
                    carrinhoAtual.getItens().size(), carrinhoAtual.calcularValorFinal());
            System.out.println("1. Listar Catálogo de Mangás Físicos");
            System.out.println("2. Adicionar Produto ao Carrinho");
            System.out.println("3. Ver Resumo do Carrinho");
            System.out.println("4. Finalizar e Pagar Pedido");
            System.out.println("5. Meus Pedidos Realizados / Cancelar Pedido");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[Erro] Digite apenas números válidos.");
                continue;
            }

            switch (opcao) {
                case 1 -> listarCatalogo();
                case 2 -> adicionarAoCarrinho(carrinhoAtual);
                case 3 -> System.out.println("\n" + carrinhoAtual.toString());
                case 4 -> {
                    if (finalizarCompra(carrinhoAtual)) {
                        // Cria um novo carrinho limpo após finalizar o anterior
                        carrinhoAtual = lojaService.criarNovoPedido(leitorLogado);
                    }
                }
                case 5 -> gerenciarMeusPedidos(leitorLogado);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void listarCatalogo() {
        System.out.println("\n--- CATÁLOGO DE VOLUMES FÍSICOS ---");
        List<ItemCatalogo> itens = lojaService.listarCatalogoDisponivel();
        if (itens.isEmpty()) {
            System.out.println("Nenhum volume cadastrado no momento.");
            return;
        }

        for (ItemCatalogo item : itens) {
            if (item instanceof VolumeFisico vf) {
                System.out.printf("[%s] %s (Vol. %d) | Estoque: %d | Preço c/ Frete: R$ %.2f\n",
                        vf.getId(),
                        vf.getManga().getTitulo(),
                        vf.getNumeroVolume(),
                        vf.getQuantidadeEstoque(),
                        vf.calcularPrecoFinal());
            }
        }
    }

    private void adicionarAoCarrinho(Pedido carrinho) {
        listarCatalogo();
        System.out.print("\nDigite o ID do volume que deseja comprar (ex: VOL-OPC-01): ");
        String idProduto = scanner.nextLine().trim();

        System.out.print("Digite a quantidade: ");
        int qtd;
        try {
            qtd = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[Erro] Quantidade inválida.");
            return;
        }

        try {
            lojaService.adicionarProdutoAoPedido(carrinho, idProduto, qtd);
            System.out.println("Produto adicionado ao carrinho com sucesso!");
        } catch (MangaTopiaException | IllegalArgumentException e) {
            System.out.println("\n[AVISO]: " + e.getMessage());
        }
    }

    private boolean finalizarCompra(Pedido carrinho) {
        System.out.println("\n" + carrinho.toString());
        System.out.print("\nConfirmar pagamento e fechar pedido? (S/N): ");
        String confirmacao = scanner.nextLine().trim();

        if (!confirmacao.equalsIgnoreCase("S")) {
            System.out.println("Pagamento não confirmado. Os itens continuam no seu carrinho.");
            return false;
        }

        try {
            lojaService.finalizarEPagarPedido(carrinho);
            System.out.println("\n[SUCESSO] Pedido " + carrinho.getId() + " pago e confirmado!");
            System.out.println("Estoque atualizado automaticamente.");
            return true;
        } catch (MangaTopiaException e) {
            System.out.println("\n[FALHA NA COMPRA]: " + e.getMessage());
            return false;
        }
    }

    private void gerenciarMeusPedidos(Leitor leitor) {
        List<Pedido> meusPedidos = lojaService.listarPedidosDoLeitor(leitor.getId());
        if (meusPedidos.isEmpty()) {
            System.out.println("\nVocê ainda não possui pedidos realizados.");
            return;
        }

        System.out.println("\n--- MEUS PEDIDOS ---");
        for (Pedido p : meusPedidos) {
            System.out.println(p.toString());
            System.out.println("----------------------------------------------");
        }

        System.out.print("Deseja cancelar algum pedido? Digite o ID (ou ENTER para sair): ");
        // String idCancelar = scanner.nextLine().trim();
        // if (!idCancelar.isEmpty()) {
        //     try {
        //         lojaService.cancelarPedido(idCancelar, leitor);
        //         System.out.println("[SUCESSO] Pedido cancelado e estoque estornado!");
        //     } catch (MangaTopiaException e) {
        //         System.out.println("\n[ERRO AO CANCELAR]: " + e.getMessage());
        //     }
        // }
    }
}