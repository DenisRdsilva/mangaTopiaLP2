// package mangaTopiaLP2.models.promocao;

// import java.time.LocalDate;

// import mangaTopiaLP2.exception.pedido.CupomInvalidoException;

// /** O desconto é o próprio valor do frete, se a compra atingir o valor mínimo. */
// public class CupomFreteGratis extends CupomDesconto {
//     private static final long serialVersionUID = 1L;

//     public static final double VALOR_MINIMO_PADRAO = 100.0;
//     public static final int MESES_FIDELIDADE = 6;

//     private final double valorMinimo;
//     private double valorFrete;

//     public CupomFreteGratis(String codigo, String descricao, LocalDate validade, double valorMinimo) {
//         super(codigo, descricao, validade);
//         if (valorMinimo < 0) {
//             throw new CupomInvalidoException("Valor mínimo não pode ser negativo.");
//         }
//         this.valorMinimo = valorMinimo;
//     }

//     public CupomFreteGratis(String codigo, String descricao, LocalDate validade) {
//         this(codigo, descricao, validade, VALOR_MINIMO_PADRAO);
//     }

//     /** O service informa o frete calculado do pedido (soma dos fretes dos volumes físicos). */
//     public void setValorFrete(double valorFrete) {
//         if (valorFrete < 0) {
//             throw new CupomInvalidoException("Frete não pode ser negativo.");
//         }
//         this.valorFrete = valorFrete;
//     }

//     public double getValorMinimo() { return valorMinimo; }

//     @Override
//     public double aplicarDesconto(double valor) {
//         return valor >= valorMinimo ? valorFrete : 0.0;
//     }

//     /**
//      * Regra "Frete Grátis Fidelidade": assinatura ATIVA há mais de 6 meses
//      * e compra física acima de R$ 100,00.
//      */
//     public static boolean elegivelFidelidade(boolean assinaturaAtiva, int mesesAtiva, double subtotalFisico) {
//         return assinaturaAtiva && mesesAtiva > MESES_FIDELIDADE && subtotalFisico > VALOR_MINIMO_PADRAO;
//     }
// }
