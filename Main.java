import java.util.Scanner;

import models.usuario.Leitor;
import repository.dados.CatalogoRepository;
import repository.dados.PedidoRepository;
import service.LojaVirtualService;
import view.leitor.ViewLojaCarrinho;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CatalogoRepository catalogoRepo = new CatalogoRepository();
        PedidoRepository pedidoRepo = new PedidoRepository();

        LojaVirtualService lojaService = new LojaVirtualService(pedidoRepo, catalogoRepo);

        Leitor leitorTeste = new Leitor(
                "USR-001",
                "Leitor Teste",
                "leitor@gmail.com",
                "123456",
                20,
                "Rua Macedo Vieira, 132 - Natal/RN"
        );

        ViewLojaCarrinho viewLoja = new ViewLojaCarrinho(scanner, lojaService);
        viewLoja.exibirMenuLoja(leitorTeste);

        scanner.close();
        System.out.println("Sistema encerrado.");
    }
}