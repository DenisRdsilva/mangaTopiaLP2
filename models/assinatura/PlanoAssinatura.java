package models.assinatura;

import java.io.Serializable; //

public class PlanoAssinatura implements Serializable {
    private static final long serialVersionUID = 1L; //Etiquetando a versão do objeto para ser acessado posteriormente

    private final String id;
    private final String nome;
    private final int duracaoMeses;
    private final double valorMensal;

    public PlanoAssinatura(String id, String nome, int duracaoMeses, double valorMensal) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID do plano é obrigatório.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do plano é obrigatório.");
        }
        if (duracaoMeses <= 0) {
            throw new IllegalArgumentException("A duração do plano deve ser maior que zero.");
        }
        if (valorMensal < 0) {
            throw new IllegalArgumentException("O valor mensal não pode ser negativo");
        }

        this.id = id;
        this.nome = nome;
        this.duracaoMeses = duracaoMeses;
        this.valorMensal = valorMensal;
    }

    public double calcularValorTotal() {
        return valorMensal * duracaoMeses;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }

    public double getValorMensal() {
        return valorMensal;
    }

}
