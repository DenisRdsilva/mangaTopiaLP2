package mangaTopiaLP2.exception.usuario;

import mangaTopiaLP2.exception.MangaTopiaException;

public class FaixaEtariaIncompativelException extends MangaTopiaException {
    public FaixaEtariaIncompativelException(int idade, int classificacao) {
        super("Conteúdo indicado para maiores de " + classificacao
                + " anos (sua idade: " + idade + ").");
    }
}