package br.edu.univasf.calculonumerico.metodos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GaussSeidelTest {
    @Test
    void resolveSistemaDiagonalDominante() {
        var resultado = GaussSeidel.resolver(
                new double[][]{{4, 1}, {2, 3}}, new double[]{1, 2},
                new double[]{0, 0}, 1e-12, 100);
        assertArrayEquals(new double[]{0.1, 0.6}, resultado.solucao(), 1e-10);
        assertTrue(resultado.residuoMaximo() < 1e-10);
        assertTrue(resultado.iteracoes() > 0);
    }
    @Test
    void rejeitaDiagonalNula() {
        assertThrows(IllegalArgumentException.class,
                () -> GaussSeidel.resolver(new double[][]{{0, 1}, {1, 2}},
                        new double[]{1, 2}, new double[]{0, 0}, 1e-8, 100));
    }
    @Test
    void rejeitaLimiteDeIteracoesInsuficiente() {
        assertThrows(IllegalStateException.class,
                () -> GaussSeidel.resolver(new double[][]{{4, 1}, {2, 3}},
                        new double[]{1, 2}, new double[]{0, 0}, 1e-14, 1));
    }
}
