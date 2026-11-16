package br.edu.univasf.calculonumerico.integracao;

/**
 * Regra de Simpson Repetida para amostras igualmente espacadas.
 *
 * <p>Para numero par de subintervalos (m >= 2), aplica Simpson 1/3 composta.
 * Para m = 3, aplica Simpson 3/8 simples. Para quantidade impar m >= 5,
 * combina Simpson 1/3 nos primeiros (m - 3) subintervalos e Simpson 3/8 nos
 * ultimos 3 subintervalos.
 */
public final class SimpsonRepetido {
    private SimpsonRepetido() { }

    public record Resultado(
            double areaTotal,
            double areaSimpson13,
            double areaSimpson38,
            int subintervalosSimpson13,
            int subintervalosSimpson38,
            String estrategia) { }

    public static double calcular(double[] valores, double passo) {
        return calcularDetalhado(valores, passo).areaTotal();
    }

    public static Resultado calcularDetalhado(double[] valores, double passo) {
        if (valores == null || valores.length < 3) {
            throw new IllegalArgumentException(
                    "Sao necessarios pelo menos 3 pontos (2 subintervalos) para a Regra de Simpson.");
        }
        if (!Double.isFinite(passo) || passo <= 0.0) {
            throw new IllegalArgumentException("O passo (h) deve ser um numero finito e maior que zero.");
        }
        for (double y : valores) {
            if (!Double.isFinite(y)) {
                throw new IllegalArgumentException("Todos os valores da amostra devem ser finitos.");
            }
        }

        int m = valores.length - 1;
        if (m % 2 == 0) {
            double area13 = simpson13Composta(valores, 0, m, passo);
            String estrategia = String.format("Simpson 1/3 composta (%d subintervalos)", m);
            return new Resultado(area13, area13, 0.0, m, 0, estrategia);
        }

        if (m == 3) {
            double area38 = simpson38(valores, 0, passo);
            return new Resultado(area38, 0.0, area38, 0, 3, "Simpson 3/8 simples (3 subintervalos)");
        }

        int corte = m - 3;
        double area13 = simpson13Composta(valores, 0, corte, passo);
        double area38 = simpson38(valores, corte, passo);
        String estrategia = String.format(
                "Composicao: Simpson 1/3 (%d subintervalos) + Simpson 3/8 (3 subintervalos)", corte);
        return new Resultado(area13 + area38, area13, area38, corte, 3, estrategia);
    }

    private static double simpson13Composta(double[] valores, int inicio, int fim, double passo) {
        double soma = valores[inicio] + valores[fim];
        for (int i = inicio + 1; i < fim; i++) {
            int offset = i - inicio;
            soma += (offset % 2 == 1 ? 4.0 : 2.0) * valores[i];
        }
        return (passo / 3.0) * soma;
    }

    private static double simpson38(double[] valores, int inicio, double passo) {
        return (3.0 * passo / 8.0) * (valores[inicio]
                + 3.0 * valores[inicio + 1]
                + 3.0 * valores[inicio + 2]
                + valores[inicio + 3]);
    }
}
