package exception.assinatura;

import exception.MangaTopiaException;

public class TransicaoEstadoInvalidaException extends MangaTopiaException {
    public TransicaoEstadoInvalidaException(String message) {
        super(message);
    }
}
