package models.promocao;

import java.io.Serializable;
import java.time.LocalDate;

// import exception.pedido.CupomInvalidoException;

/**
 * Classe base (abstrata) dos cupons e ponto do Polimorfismo 2 (motor de descontos).
 * Guarda o que todo cupom tem em comum; cada tipo de cupom calcula o desconto do seu jeito.
 */
public abstract class CupomDesconto implements Serializable {
    private static final long serialVersionUID = 1L;

    // private final String codigo;
    // private final String descricao;
    // private final LocalDate validade; // pode ser null = sem prazo

    // protected CupomDesconto(String codigo, String descricao, LocalDate validade) {
    //     if (codigo == null || codigo.isBlank()) {
    //         throw new CupomInvalidoException("O código do cupom não pode ser vazio.");
    //     }
    //     this.codigo = codigo.trim().toUpperCase();
    //     this.descricao = descricao;
    //     this.validade = validade;
    // }

    // public String getCodigo() { return codigo; }
    // public String getDescricao() { return descricao; }
    // public LocalDate getValidade() { return validade; }

    // /**
    //  * @param valor valor sobre o qual o desconto é calculado (ex: subtotal do pedido)
    //  * @return QUANTO será descontado, em reais (nunca negativo)
    //  */
    // public abstract double aplicarDesconto(double valor);

    // public boolean estaVencido() {
    //     return validade != null && LocalDate.now().isAfter(validade);
    // }

    /** Lança exceção se o cupom estiver vencido. */
    // public void validarUso() {
    //     if (estaVencido()) {
    //         throw new CupomInvalidoException("O cupom " + codigo + " está vencido.");
    //     }
    // }
}
