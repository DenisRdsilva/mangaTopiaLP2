package mangaTopiaLP2.models.usuario;

import mangaTopiaLP2.exception.usuario.FaixaEtariaIncompativelException;
import mangaTopiaLP2.models.enums.PerfilUsuario;

import java.time.LocalDate;

public class Leitor extends Usuario {
    private static final long serialVersionUID = 1L;

    private int pontosRecompensa;
    // private Assinatura assinatura;  // Gabriel: descomentar quando a classe existir

    public Leitor(String nome, String email, String senhaHash, LocalDate dataNascimento) {
        super(nome, email, senhaHash, dataNascimento);
        this.pontosRecompensa = 0;
    }

    @Override
    public PerfilUsuario getPerfil() {
        return PerfilUsuario.LEITOR;
    }

    public int getPontosRecompensa() {
        return pontosRecompensa;
    }

    public void adicionarPontos(int pontos) {
        if (pontos <= 0)
            throw new IllegalArgumentException("Pontos devem ser positivos.");
        pontosRecompensa += pontos;
    }

    // Sintia e Dênis chamam isto antes de liberar leitura ou compra
    public void verificarFaixaEtaria(int classificacaoIndicativa) {
        if (getIdade() < classificacaoIndicativa)
            throw new FaixaEtariaIncompativelException(getIdade(), classificacaoIndicativa);
    }
}
    

