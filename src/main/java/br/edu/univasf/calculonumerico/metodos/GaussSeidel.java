package br.edu.univasf.calculonumerico.metodos;

/** Resolve Ax=b pelo metodo iterativo de Gauss-Seidel. */
public final class GaussSeidel {
    private GaussSeidel() { }

    public record Resultado(double[] solucao, int iteracoes, double variacaoMaxima, double residuoMaximo) {
        public Resultado {
            solucao = solucao.clone();
        }
        @Override public double[] solucao() { return solucao.clone(); }
    }

    public static Resultado resolver(double[][] a, double[] b, double[] chute,
                                     double tolerancia, int maxIteracoes) {
        if (a == null || b == null || chute == null || a.length == 0
                || b.length != a.length || chute.length != a.length) {
            throw new IllegalArgumentException("Dimensoes inconsistentes do sistema.");
        }
        if (!(tolerancia > 0.0) || !Double.isFinite(tolerancia) || maxIteracoes <= 0) {
            throw new IllegalArgumentException("Tolerancia e numero maximo de iteracoes invalidos.");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            if (a[i] == null || a[i].length != n || !Double.isFinite(b[i]) || !Double.isFinite(chute[i])) {
                throw new IllegalArgumentException("Matriz/vetores invalidos.");
            }
            for (double coeficiente : a[i]) {
                if (!Double.isFinite(coeficiente)) throw new IllegalArgumentException("Coeficiente nao finito.");
            }
            if (Math.abs(a[i][i]) < 1e-14) {
                throw new IllegalArgumentException("Elemento nulo ou muito pequeno na diagonal principal.");
            }
        }
        double[] x = chute.clone();
        for (int iteracao = 1; iteracao <= maxIteracoes; iteracao++) {
            double[] anterior = x.clone();
            double variacao = 0.0;
            for (int i = 0; i < n; i++) {
                double soma = b[i];
                for (int j = 0; j < n; j++) {
                    if (j != i) soma -= a[i][j] * (j < i ? x[j] : anterior[j]);
                }
                x[i] = soma / a[i][i];
                if (!Double.isFinite(x[i])) {
                    throw new IllegalStateException("Divergencia numerica detectada.");
                }
                variacao = Math.max(variacao, Math.abs(x[i] - anterior[i]));
            }
            double residuo = 0.0;
            for (int i = 0; i < n; i++) {
                double soma = 0.0;
                for (int j = 0; j < n; j++) soma += a[i][j] * x[j];
                residuo = Math.max(residuo, Math.abs(soma - b[i]));
            }
            if (variacao <= tolerancia && residuo <= tolerancia) {
                return new Resultado(x, iteracao, variacao, residuo);
            }
        }
        throw new IllegalStateException("Gauss-Seidel nao convergiu em " + maxIteracoes
                + " iteracoes; revise os dados, a tolerancia ou a convergencia do sistema.");
    }
}
