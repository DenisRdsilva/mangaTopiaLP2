package mangaTopiaLP2;

import java.util.Scanner;

import mangaTopiaLP2.models.usuario.Administrador;
import mangaTopiaLP2.repository.BancoDadosMemoria;
import mangaTopiaLP2.repository.Dados.AssinaturaRepository;
import mangaTopiaLP2.repository.Dados.CatalogoRepository;
import mangaTopiaLP2.repository.Dados.CupomRepository;
import mangaTopiaLP2.repository.Dados.PedidoRepository;
import mangaTopiaLP2.repository.Dados.UsuarioRepository;
import mangaTopiaLP2.repository.PersistenciaArquivo;
import mangaTopiaLP2.service.AssinaturaService;
import mangaTopiaLP2.service.GestaoMangaService;
import mangaTopiaLP2.service.LojaVirtualService;
import mangaTopiaLP2.service.PromocaoCupomService;
import mangaTopiaLP2.service.autenticacao.AutenticacaoService;
import mangaTopiaLP2.view.MenuPrincipal;

public class Main {

    private static final String ARQUIVO_DADOS = "mangatopia_dados.dat";

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   Iniciando o sistema MangaTopia...     ");
        System.out.println("=========================================");

        // 1. BOOT: Carrega os dados do arquivo para a memória (Requisito h)
        BancoDadosMemoria bancoDados = PersistenciaArquivo.carregarDados(ARQUIVO_DADOS);

        // 2. INICIALIZAÇÃO DE REPOSITÓRIOS (Camada de Dados)
        UsuarioRepository usuarioRepo = new UsuarioRepository(bancoDados);
        CatalogoRepository catalogoRepo = new CatalogoRepository(bancoDados);
        AssinaturaRepository assinaturaRepo = new AssinaturaRepository(bancoDados);
        PedidoRepository pedidoRepo = new PedidoRepository(bancoDados);
        CupomRepository cupomRepo = new CupomRepository(bancoDados);

        // Garante que existe um Admin padrão na primeira execução do sistema
        inicializarAdminPadrao(usuarioRepo);

        // 3. INJEÇÃO DE DEPENDÊNCIAS NOS SERVICES (Camada de Regras de Negócio)
        AutenticacaoService authService = new AutenticacaoService(usuarioRepo);
        AssinaturaService assinaturaService = new AssinaturaService(assinaturaRepo, usuarioRepo);
        GestaoMangaService mangaService = new GestaoMangaService(catalogoRepo, assinaturaService, usuarioRepo);
        PromocaoCupomService cupomService = new PromocaoCupomService(cupomRepo, assinaturaService);
        LojaVirtualService lojaService = new LojaVirtualService(pedidoRepo, catalogoRepo, usuarioRepo, cupomService);

        // 4. SHUTDOWN HOOK: Salva os dados automaticamente mesmo se o terminal for fechado à força
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[Sistema] Salvando alterações em disco...");
            PersistenciaArquivo.salvarDados(bancoDados, ARQUIVO_DADOS);
            System.out.println("[Sistema] Dados salvos com segurança!");
        }));

        // 5. EXECUÇÃO DA INTERFACE (Camada View)
        Scanner scanner = new Scanner(System.in);
        MenuPrincipal menuPrincipal = new MenuPrincipal(
                scanner,
                authService,
                mangaService,
                assinaturaService,
                lojaService,
                cupomService
        );

        try {
            menuPrincipal.exibir();
        } catch (Exception e) {
            System.err.println("\n[Erro Crítico Inesperado]: " + e.getMessage());
        } finally {
            scanner.close();
            System.out.println("Encerrando MangaTopia. Até logo!");
        }
    }

    /**
     * Cria um administrador padrão caso o arquivo de dados esteja vazio na primeira vez,
     * evitando que o grupo fique travado sem conseguir logar na apresentação.
     */
    private static void inicializarAdminPadrao(UsuarioRepository usuarioRepo) {
        if (usuarioRepo.listarTodos().isEmpty()) {
            Administrador adminPadrao = new Administrador(
                    "ADM-001",
                    "Administrador Master",
                    "admin@mangatopia.com",
                    "123456",
                    "COD-MASTER",
                    1
            );
            usuarioRepo.salvar(adminPadrao);
            System.out.println("[Setup Inicial] Admin padrão criado: admin@mangatopia.com | Senha: 123456");
        }
    }
}