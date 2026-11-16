package br.edu.univasf.calculonumerico.metodos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AjusteLeiDeMooreTest {

    @Test
    void ajustaDadosOriginaisQuestao03_1EPreve2010E2020() {
        var resultado = AjusteLeiDeMoore.ajustarDadosPadrao();

        assertEquals(11, resultado.pontos().size());
        assertEquals(1971.0, resultado.anoMinimo(), 1e-12);
        assertEquals(2000.0, resultado.anoMaximo(), 1e-12);
        assertEquals(0.1371828980, resultado.inclinacao(), 1e-9);
        assertEquals(-266.9534012750, resultado.intercepto(), 1e-7);
        assertEquals(0.98952072, resultado.rQuadrado(), 1e-7);

        var prev2010 = resultado.prever(2010);
        assertEquals(8.784223623, prev2010.log10Estimado(), 1e-8);
        assertEquals(608_448_216.72, prev2010.transistoresEstimados(), 1.0);
        assertTrue(prev2010.extrapolacao());

        var prev2020 = resultado.prever(2020);
        assertEquals(10.156052602, prev2020.log10Estimado(), 1e-8);
        assertEquals(14_323_613_777.74, prev2020.transistoresEstimados(), 10.0);
        assertTrue(prev2020.extrapolacao());

        var prev1985 = resultado.prever(1985);
        assertFalse(prev1985.extrapolacao());
    }

    @Test
    void ajustaExatamenteCrescimentoExponencialPuro() {
        double[] anos = {2000, 2001, 2002, 2003};
        double[] transistores = {100, 200, 400, 800};

        var resultado = AjusteLeiDeMoore.ajustar(anos, transistores);

        assertEquals(1.0, resultado.rQuadrado(), 1e-12);
        assertEquals(0.0, resultado.somaQuadradosResiduosLog10(), 1e-12);
        assertEquals(1.0, resultado.tempoDuplicacaoAnos(), 1e-12);
        assertEquals(2.0, resultado.fatorCrescimentoAnual(), 1e-12);
        assertEquals(3200.0, resultado.prever(2005).transistoresEstimados(), 1e-9);
    }

    @Test
    void rejeitaEntradasInvalidas() {
        assertThrows(IllegalArgumentException.class,
                () -> AjusteLeiDeMoore.ajustar(null, new double[]{100, 200}));
        assertThrows(IllegalArgumentException.class,
                () -> AjusteLeiDeMoore.ajustar(new double[]{1971}, new double[]{2250}));
        assertThrows(IllegalArgumentException.class,
                () -> AjusteLeiDeMoore.ajustar(new double[]{1971, 1972}, new double[]{2250}));
        assertThrows(IllegalArgumentException.class,
                () -> AjusteLeiDeMoore.ajustar(new double[]{1971, 1972}, new double[]{0.0, 2250}));
        assertThrows(IllegalArgumentException.class,
                () -> AjusteLeiDeMoore.ajustar(new double[]{1971, 1972}, new double[]{-10.0, 2250}));
        assertThrows(IllegalArgumentException.class,
                () -> AjusteLeiDeMoore.ajustar(new double[]{1980, 1980}, new double[]{1000, 2000}));

        var resultado = AjusteLeiDeMoore.ajustarDadosPadrao();
        assertThrows(IllegalArgumentException.class, () -> resultado.prever(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> resultado.prever(5000));
    }
}
