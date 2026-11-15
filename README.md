# mangaTopiaLP2

📚 MangaTopia - Plataforma de Leitura Digital e E-commerce de Mangás

Sistema desenvolvido como projeto da Unidade 03 da disciplina de Linguagem de Programação II (LP2), ministrada pelo Prof. Jerffeson Gomes Dutra. O MangaTopia é uma aplicação orientada a objetos que unifica um serviço de leitura de mangás por assinatura (streaming) com uma loja virtual de volumes físicos e capítulos avulsos.

👥 Integrantes do Grupo

Flávio - Matrícula: [INSERIR MATRÍCULA] (Módulo: Usuários, Autenticação e Perfis)

Sintia - Matrícula: [INSERIR MATRÍCULA] (Módulo: Catálogo Digital e Leitura Online)

Gabriel - Matrícula: [INSERIR MATRÍCULA] (Módulo: Assinaturas e Máquina de Estados)

Dênis - Matrícula: [INSERIR MATRÍCULA] (Módulo: Loja Física, Estoque e Pedidos)

Victor - Matrícula: [INSERIR MATRÍCULA] (Módulo: Cupons, Persistência, Relatórios e Documentação)

🔗 Repositório GitHub: [INSERIR LINK DO REPOSITÓRIO AQUI](https://github.com/DenisRdsilva/mangaTopiaLP2)

🚀 Instruções para Executar a Aplicação

Pré-requisitos

Java Development Kit (JDK): Versão 17 ou superior instalada.

Terminal / IDE: IntelliJ IDEA, Eclipse, VS Code ou terminal do sistema operacional.

Passo a Passo via Terminal

Clone o repositório ou extraia o arquivo .zip do projeto:

git clone [LINK_DO_REPOSITORIO](https://github.com/DenisRdsilva/mangaTopiaLP2)
cd mangaTopiaLP2

Credenciais Padrão (Primeiro Acesso)

Ao iniciar o sistema pela primeira vez sem arquivos de dados prévios, um administrador padrão é gerado automaticamente:

E-mail: admin@mangatopia.com

Senha: admin123

## 🏗️ Arquitetura do projeto

O sistema segue uma **Arquitetura em Camadas** modularizada por domínio, garantindo o desacoplamento entre a interface de terminal, as regras de negócio, as entidades de domínio e a persistência em arquivos.

```text
mangaTopiaLP2/
│
├── models/                                     # Entidades de Domínio (Classes próprias de POO)
│   ├── assinatura/                             # [Gabriel] Assinatura (Estado Dinâmico), PlanoAssinatura
│   ├── catalogo/                               # [Sintia & Dênis] Manga, Capitulo, ItemCatalogo, CapituloDigital, VolumeFisico
│   ├── enums/                                  # Estados e constantes (EstadoAssinatura, EstadoPedido, PerfilUsuario, etc.)
│   ├── pedido/                                 # [Dênis] Pedido, ItemPedido
│   ├── promocao/                               # [Victor] CupomDesconto (Polimorfismo), CupomAssinante, CupomFreteGratis
│   └── usuario/                                # [Flávio] Usuario (Abstrata), Leitor, Administrador
│
├── exception/                                  # Exceções Personalizadas (Herdam de MangaTopiaException)
│   ├── MangaTopiaException.java                # Exceção base do sistema
│   ├── assinatura/                             # [Gabriel] TransicaoEstadoInvalidaException, AssinaturaInativaException
│   ├── catalogo/                               # [Sintia] ConteudoExclusivoAssinantesException, ItemJaAdquiridoException
│   ├── pedido/                                 # [Dênis & Victor] EstoqueInsuficienteException, CupomInvalidoException
│   └── usuario/                                # [Flávio] CredenciaisInvalidasException, FaixaEtariaIncompativelException
│
├── repository/                                 # Camada de Persistência e Acesso a Dados
│   ├── IRepository.java                        # Contrato genérico de CRUD
│   ├── BancoDadosMemoria.java                  # [Victor] Container Serializable com todas as listas em memória
│   ├── PersistenciaArquivo.java                # [Victor] Motor de leitura/escrita em arquivo no boot e shutdown
│   └── Dados/                                  # Implementações dos repositórios por módulo (Usuario, Catalogo, Pedido, etc.)
│
├── service/                                    # Camada de Regras de Negócio e Orquestração
│   ├── autenticacao/                           # [Flávio] AutenticacaoService (Login, controle de sessão e permissões)
│   ├── validacoes/                             # [Victor] ValidacaoService (Validações transversais e regras de negócio)
│   ├── AssinaturaService.java                  # [Gabriel] Controle de transições de estado e vigência
│   ├── GestaoMangaService.java                 # [Sintia] CRUD de obras, capítulos e leitura online
│   ├── LojaVirtualService.java                 # [Dênis] Carrinho, checkout, baixa de estoque e rastreio
│   └── PromocaoCupomService.java               # [Victor] Aplicação de descontos polimórficos e relatórios
│
├── view/                                       # Camada de Apresentação (CLI / Menus no Terminal)
│   ├── ConsoleUtils.java                       # Utilitários de leitura segura de inputs e formatação
│   ├── MenuPrincipal.java                      # Tela inicial de login, cadastro e roteamento por perfil
│   ├── admin/                                  # [Perfil Administrador] Menus de gestão de catálogo, estoque, pedidos e relatórios
│   └── leitor/                                 # [Perfil Leitor] Menus de leitura online, assinatura e carrinho de compras
│
├── Main.java                                   # Ponto de entrada: Carrega arquivos -> Inicia Menu -> Salva arquivos
└── README.md                                   # Documentação oficial do projeto

🧠 Conceitos de POO Aplicados

Encapsulamento: Todos os atributos das entidades são private e acessados/modificados exclusivamente via métodos de negócio validados ou getters/setters controlados.

Herança Própria:

Leitor e Administrador herdam de Usuario (com atributos próprios como pontosRecompensa e nivelPermissao).

VolumeFisico e CapituloDigital herdam de ItemCatalogo (com atributos próprios como pesoGramas, quantidadeEstoque e exclusivoAssinantes).

Polimorfismo:

Cálculo de Preço (ItemCatalogo.calcularPrecoFinal()): VolumeFisico adiciona o custo de frete proporcional ao peso; CapituloDigital retorna R$ 0,00 caso o leitor possua assinatura ativa ou cobra o valor avulso.

Motor de Descontos (PoliticaDesconto.aplicarDesconto()): CupomAssinante aplica descontos progressivos conforme o tempo de assinatura; CupomFreteGratis abate o valor do frete para compras acima do valor mínimo.

Estado Dinâmico (Assinatura): Controla o ciclo de vida entre os estados PENDENTE, ATIVA, SUSPENSA, EXPIRADA e CANCELADA, impedindo transições inválidas (ex: ir de CANCELADA direto para ATIVA).

📋 Regras de Negócio

Regras Implementadas no Sistema

Bloqueio por Faixa Etária: Leitores com idade inferior à classificação indicativa do Manga não podem ler capítulos ou adquirir volumes da obra.

Baixa Automática de Estoque: Ao confirmar um Pedido contendo VolumeFisico, a quantidadeEstoque é debitada imediatamente.

Exclusividade VIP: Capítulos marcados com exclusivoAssinantes = true exigem que a Assinatura do leitor esteja no estado ATIVA.

Reestocagem por Cancelamento: O cancelamento de um Pedido antes da etapa de envio devolve automaticamente os volumes ao estoque.

Acúmulo de Pontos: Cada leitura concluída e compra finalizada gera pontosRecompensa na conta do Leitor.

Trava de Estoque no Carrinho: Não é permitido adicionar ao carrinho uma quantidade de VolumeFisico superior ao saldo disponível em estoque.

Frete Grátis Fidelidade: Leitores com assinatura ATIVA há mais de 6 meses ganham frete grátis automático em compras físicas acima de R$ 100,00.

Máquina de Estados Estrita: Transições de estado inválidas na Assinatura lançam TransicaoEstadoInvalidaException.

Teto Máximo de Desconto: A aplicação de cupons em um Pedido não pode ultrapassar 40% do valor bruto total.

Prevenção de Duplicidade Digital: O sistema impede que um leitor compre de forma avulsa um CapituloDigital que já pertence à sua biblioteca.

Regras Identificadas (Não Implementadas nesta Versão)

Sistema de Pré-Venda (Pre-order): Permitir a compra de VolumeFisico com quantidadeEstoque = 0 quando a data de lançamento do mangá for futura, gerando fila de envio prioritário.

Assinatura Familiar / Compartilhada: Permitir que um Leitor principal vincule até 3 perfis dependentes sob o mesmo plano, com histórico de leitura e restrições etárias individuais.

Leitura Offline Temporária (DRM): Permitir o download criptografado de até 10 capítulos digitais com expiração automática de licença após 7 dias sem conexão.

Lista de Desejos e Notificação de Reposição: Notificar automaticamente os leitores interessados quando um VolumeFisico esgotado receber reposição de estoque pelo Administrador.

⚠️ Análise de Riscos e Mitigações (Estudos de Caso)

Abaixo estão listadas 5 situações críticas identificadas na arquitetura do MangaTopia, seguindo o fluxo de análise de impacto e mitigação:

1. Compra Simultânea do Último Volume Físico em Estoque

Situação: Dois leitores tentam finalizar simultaneamente a compra da última unidade física disponível do Volume 1 de um mangá popular.

Problema: O método de checkout verifica quantidadeEstoque >= 1 para ambas as requisições antes que a primeira conclua a subtração no repositório.

Impacto: O estoque torna-se negativo (-1), gerando uma venda confirmada sem exemplar físico disponível para envio e causando transtorno logístico.

Mitigação: Sincronização do bloco crítico de baixa de estoque (synchronized) aliada a uma verificação atômica antes da confirmação, lançando EstoqueInsuficienteException para a segunda requisição.

Classes Relacionadas: VolumeFisico, Pedido, LojaService, EstoqueInsuficienteException.

2. Cancelamento da Assinatura Durante Leitura Ativa

Situação: Um leitor inicia a leitura de um capítulo exclusivo em uma sessão e, paralelamente, cancela sua assinatura ou tem o prazo expirado por falta de pagamento.

Problema: O sistema valida o acesso apenas na abertura do mangá e mantém a sessão liberada em memória sem revalidar o estado atual da assinatura ao avançar os capítulos.

Impacto: O usuário continua consumindo conteúdo pago e exclusivo mesmo após sua assinatura transitar para CANCELADA ou EXPIRADA.

Mitigação: Revalidação dinâmica do estado da Assinatura (estado == EstadoAssinatura.ATIVA) a cada solicitação de carregamento de um novo CapituloDigital, disparando AssinaturaInativaException caso não esteja ativa.

Classes Relacionadas: Assinatura, CapituloDigital, Leitor, LeituraService.

3. Corrupção do Arquivo de Persistência em Encerramento Abrupto

Situação: A aplicação é encerrada inesperadamente (queda de energia ou Ctrl+C forçado) exatamente no momento em que o sistema está gravando os registros no arquivo de dados.

Problema: O fluxo de escrita (ObjectOutputStream) é interrompido pela metade, deixando o arquivo .dat truncado ou corrompido.

Impacto: Na próxima inicialização, o método de carregamento falha ao desserializar os objetos, impedindo a abertura do sistema e causando perda dos cadastros.

Mitigação: Implementação de escrita atômica utilizando um arquivo temporário (dados.tmp) que só substitui o arquivo principal (dados.dat) após a gravação completa sem erros, além de manter um arquivo de backup (.bak) restaurado via bloco try-catch.

Classes Relacionadas: PersistenciaArquivo, FalhaPersistenciaException, Main.

4. Aplicação Duplicada de Cupons de Desconto no Mesmo Pedido

Situação: Em uma conexão instável, o leitor envia múltiplas requisições seguidas para aplicar o mesmo código de cupom promocional ao carrinho de compras.

Problema: O serviço de cálculo processa as requisições em sequência sem verificar se o benefício já foi contabilizado no valorTotal do pedido.

Impacto: O desconto é aplicado repetidas vezes, reduzindo indevidamente o valor final da compra para zero ou gerando prejuízo financeiro.

Mitigação: Tornar a operação de aplicação de cupom idempotente, registrando os códigos já aplicados dentro da instância de Pedido e bloqueando reincidências com CupomInvalidoException, além de impor o teto máximo de 40% de desconto.

Classes Relacionadas: Pedido, PoliticaDesconto, CupomAssinante, CupomFreteGratis.

5. Expiração Prematura de Assinatura Durante Compensação Bancária

Situação: A data de vencimento (dataFim) da assinatura é atingida enquanto o pagamento de renovação do leitor ainda está em processamento bancário.

Problema: A rotina de verificação altera o estado da assinatura diretamente de ATIVA para EXPIRADA assim que a data atual ultrapassa dataFim.

Impacto: O leitor tem seu acesso bloqueado imediatamente e perde o histórico de fidelidade (meses consecutivos ativos para frete grátis), mesmo tendo efetuado o pagamento no prazo.

Mitigação: Adoção do estado intermediário SUSPENSA na máquina de estados da Assinatura, garantindo um período de carência de 3 dias úteis antes da transição definitiva para EXPIRADA.

Classes Relacionadas: Assinatura, EstadoAssinatura, AssinaturaService.