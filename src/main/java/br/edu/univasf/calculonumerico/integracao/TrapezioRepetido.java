package br.edu.univasf.calculonumerico.integracao;

/** Regra do Trapezio Repetida para amostras igualmente espacadas. */
public final class TrapezioRepetido {
    private TrapezioRepetido() { }

    public static double calcular(double[] valores, double passo) {
        if (valores == null || valores.length < 2) {
            throw new IllegalArgumentException("Sao necessarios pelo menos 2 pontos para a Regra do Trapezio.");
        }
        if (!Double.isFinite(passo) || passo <= 0.0) {
            throw new IllegalArgumentException("O passo (h) deve ser um numero finito e maior que zero.");
        }
        for (double y : valores) {
            if (!Double.isFinite(y)) {
                throw new IllegalArgumentException("Todos os valores da amostra devem ser finitos.");
            }
        }

        int ultimo = valores.length - 1;
        double soma = 0.5 * (valores[0] + valores[ultimo]);
        for (int i = 1; i < ultimo; i++) {
            soma += valores[i];
        }

        return passo * soma;
    }
}