package exception.usuario;

import exception.MangaTopiaException;

public class FaixaEtariaIncompativelException extends MangaTopiaException {
    public FaixaEtariaIncompativelException(int idade, int classificacao) {
        super("Conteúdo indicado para maiores de " + classificacao
                + " anos (sua idade: " + idade + ").");
    }
}