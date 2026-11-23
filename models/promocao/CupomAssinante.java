package mangaTopiaLP2.models.promocao;

import java.time.LocalDate;

import mangaTopiaLP2.exception.pedido.CupomInvalidoException;

/** Desconto progressivo: quanto mais meses de assinatura, maior o desconto. */
public class CupomAssinante extends CupomDesconto {
    private static final long serialVersionUID = 1L;

    public static final double PERCENTUAL_POR_MES = 0.02; // 2% por mês
    public static final double PERCENTUAL_MAXIMO = 0.20;  // limite de 20%

    private int mesesAssinatura;

    public CupomAssinante(String codigo, String descricao, LocalDate validade) {
        super(codigo, descricao, validade);
    }

    /** O service informa há quantos meses a assinatura do leitor está ativa. */
    public void setMesesAssinatura(int mesesAssinatura) {
        if (mesesAssinatura < 0) {
            throw new CupomInvalidoException("Meses de assinatura não pode ser negativo.");
        }
        this.mesesAssinatura = mesesAssinatura;
    }

    public int getMesesAssinatura() { return mesesAssinatura; }

    public double getPercentual() {
        return Math.min(mesesAssinatura * PERCENTUAL_POR_MES, PERCENTUAL_MAXIMO);
    }

    @Override
    public double aplicarDesconto(double valor) {
        if (valor <= 0) return 0.0;
        return valor * getPercentual();
    }
}
