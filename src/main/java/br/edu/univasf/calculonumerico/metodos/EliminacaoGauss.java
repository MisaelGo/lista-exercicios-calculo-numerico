package br.edu.univasf.calculonumerico.metodos;

/** Resolve sistemas lineares Ax = b por Eliminacao de Gauss com pivotamento parcial. */
public final class EliminacaoGauss {
    private static final double EPSILON = 1e-12;

    private EliminacaoGauss() { }

    public record Resultado(double[] solucao, double[][] matrizEscalonada,
                            double[] vetorEscalonado, double residuoMaximo) {
        public Resultado {
            solucao = solucao.clone();
            matrizEscalonada = copiarMatriz(matrizEscalonada);
            vetorEscalonado = vetorEscalonado.clone();
        }

        @Override
        public double[] solucao() {
            return solucao.clone();
        }

        @Override
        public double[][] matrizEscalonada() {
            return copiarMatriz(matrizEscalonada);
        }

        @Override
        public double[] vetorEscalonado() {
            return vetorEscalonado.clone();
        }
    }

    public static Resultado resolver(double[][] a, double[] b) {
        if (a == null || b == null || a.length == 0 || b.length != a.length) {
            throw new IllegalArgumentException("Dimensoes inconsistentes do sistema.");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            if (a[i] == null || a[i].length != n || !Double.isFinite(b[i])) {
                throw new IllegalArgumentException("Matriz ou vetor com dimensoes ou valores invalidos.");
            }
            for (double coef : a[i]) {
                if (!Double.isFinite(coef)) {
                    throw new IllegalArgumentException("Coeficiente nao finito na matriz.");
                }
            }
        }

        double[][] u = copiarMatriz(a);
        double[] c = b.clone();

        // 1. Escalonamento com pivotamento parcial
        for (int k = 0; k < n - 1; k++) {
            int linhaPivo = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(u[i][k]) > Math.abs(u[linhaPivo][k])) {
                    linhaPivo = i;
                }
            }

            if (Math.abs(u[linhaPivo][k]) < EPSILON) {
                throw new IllegalArgumentException("Sistema singular ou indeterminado (pivo nulo).");
            }

            if (linhaPivo != k) {
                double[] tempLinha = u[k];
                u[k] = u[linhaPivo];
                u[linhaPivo] = tempLinha;

                double tempB = c[k];
                c[k] = c[linhaPivo];
                c[linhaPivo] = tempB;
            }

            for (int i = k + 1; i < n; i++) {
                double fator = u[i][k] / u[k][k];
                u[i][k] = 0.0;
                for (int j = k + 1; j < n; j++) {
                    u[i][j] -= fator * u[k][j];
                }
                c[i] -= fator * c[k];
            }
        }

        if (Math.abs(u[n - 1][n - 1]) < EPSILON) {
            throw new IllegalArgumentException("Sistema singular ou indeterminado (ultimo pivo nulo).");
        }

        // 2. Substituicao regressiva
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double soma = 0.0;
            for (int j = i + 1; j < n; j++) {
                soma += u[i][j] * x[j];
            }
            x[i] = (c[i] - soma) / u[i][i];
        }

        // 3. Calculo do residuo maximo ||Ax - b||_inf
        double residuoMaximo = 0.0;
        for (int i = 0; i < n; i++) {
            double soma = 0.0;
            for (int j = 0; j < n; j++) {
                soma += a[i][j] * x[j];
            }
            residuoMaximo = Math.max(residuoMaximo, Math.abs(soma - b[i]));
        }

        return new Resultado(x, u, c, residuoMaximo);
    }

    private static double[][] copiarMatriz(double[][] origem) {
        double[][] copia = new double[origem.length][];
        for (int i = 0; i < origem.length; i++) {
            copia[i] = origem[i].clone();
        }
        return copia;
    }
}