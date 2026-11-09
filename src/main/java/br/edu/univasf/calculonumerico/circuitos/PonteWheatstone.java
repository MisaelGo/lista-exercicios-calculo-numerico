package br.edu.univasf.calculonumerico.circuitos;

import br.edu.univasf.calculonumerico.metodos.GaussSeidel;

/**
 * Ponte com A=E e D=0. Correntes: I1 A->B; I2 A->C; I3 C->D;
 * I4 B->D; I5 B->C; I6 D->A (fonte).
 */
public final class PonteWheatstone {
    private PonteWheatstone() { }

    public record Resultado(double potencialB, double potencialC, double i1, double i2,
                            double i3, double i4, double i5, double i6,
                            int iteracoes, double residuoNodal,
                            double residuoKirchhoffCorrentes, double residuoKirchhoffTensoes) { }

    public static Resultado calcular(double e, double r1, double r2, double r3, double r4,
                                     double r5, double tolerancia, int maxIteracoes) {
        if (!Double.isFinite(e)) throw new IllegalArgumentException("Tensao da fonte invalida.");
        for (double resistencia : new double[]{r1, r2, r3, r4, r5}) {
            if (!Double.isFinite(resistencia) || resistencia <= 0.0) {
                throw new IllegalArgumentException("Resistencias devem ser finitas e positivas.");
            }
        }
        double g1 = 1.0 / r1, g2 = 1.0 / r2, g3 = 1.0 / r3,
               g4 = 1.0 / r4, g5 = 1.0 / r5;
        double[][] a = {{g1 + g4 + g5, -g5}, {-g5, g2 + g3 + g5}};
        double[] b = {e * g1, e * g2};
        GaussSeidel.Resultado sol = GaussSeidel.resolver(a, b, new double[]{0.0, 0.0},
                tolerancia, maxIteracoes);
        double vb = sol.solucao()[0];
        double vc = sol.solucao()[1];
        double i1 = (e - vb) / r1;
        double i2 = (e - vc) / r2;
        double i3 = vc / r3;
        double i4 = vb / r4;
        double i5 = (vb - vc) / r5;
        double i6 = i1 + i2;
        double erroKcl = Math.max(Math.abs(i1 - i4 - i5),
                Math.max(Math.abs(i2 + i5 - i3), Math.abs(i3 + i4 - i6)));
        double erroKvl = Math.max(Math.abs(r1 * i1 + r4 * i4 - e),
                Math.max(Math.abs(r2 * i2 + r3 * i3 - e),
                        Math.abs(r1 * i1 + r5 * i5 - r2 * i2)));
        return new Resultado(vb, vc, i1, i2, i3, i4, i5, i6,
                sol.iteracoes(), sol.residuoMaximo(), erroKcl, erroKvl);
    }
}
