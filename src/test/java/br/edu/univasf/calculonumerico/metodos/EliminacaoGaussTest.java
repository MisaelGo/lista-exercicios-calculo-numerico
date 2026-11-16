package br.edu.univasf.calculonumerico.metodos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EliminacaoGaussTest {

    @Test
    void resolveCasoOriginalQuestao01_2() {
        double[][] a = {
            {15.0, 17.0, 19.0},
            {0.30, 0.40, 0.55},
            {1.00, 1.20, 1.50}
        };
        double[] b = {3890.0, 95.0, 282.0};

        var resultado = EliminacaoGauss.resolver(a, b);

        assertArrayEquals(new double[]{90.0, 60.0, 80.0}, resultado.solucao(), 1e-9);
        assertTrue(resultado.residuoMaximo() < 1e-10);
    }

    @Test
    void realizaPivotamentoParcialQuandoDiagonalZero() {
        double[][] a = {
            {0.0, 2.0},
            {3.0, 1.0}
        };
        double[] b = {4.0, 5.0};

        var resultado = EliminacaoGauss.resolver(a, b);

        assertArrayEquals(new double[]{1.0, 2.0}, resultado.solucao(), 1e-9);
        assertTrue(resultado.residuoMaximo() < 1e-12);
    }

    @Test
    void rejeitaSistemaSingular() {
        double[][] a = {
            {1.0, 2.0, 3.0},
            {2.0, 4.0, 6.0},
            {1.0, 0.0, 1.0}
        };
        double[] b = {6.0, 12.0, 2.0};

        assertThrows(IllegalArgumentException.class, () -> EliminacaoGauss.resolver(a, b));
    }

    @Test
    void rejeitaDimensoesInconsistentes() {
        assertThrows(IllegalArgumentException.class,
                () -> EliminacaoGauss.resolver(new double[][]{{1.0, 2.0}}, new double[]{1.0}));
    }
}