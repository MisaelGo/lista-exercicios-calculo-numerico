package br.edu.univasf.calculonumerico.integracao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpsonRepetidoTest {

    @Test
    void calculaAreaSecaoDoNavioQuestao04_3ComComposicao13E38() {
        double[] larguras = {3.00, 2.92, 2.75, 2.52, 2.30, 1.84, 0.92, 0.00};
        var resultado = SimpsonRepetido.calcularDetalhado(larguras, 0.4);

        assertEquals(4, resultado.subintervalosSimpson13());
        assertEquals(3, resultado.subintervalosSimpson38());
        assertEquals(13.024 / 3.0, resultado.areaSimpson13(), 1e-12);
        assertEquals(1.587, resultado.areaSimpson38(), 1e-12);
        assertEquals(5.928333333333333, resultado.areaTotal(), 1e-12);
        assertEquals(5.928333333333333, SimpsonRepetido.calcular(larguras, 0.4), 1e-12);
    }

    @Test
    void integraPolinomioCubicoExatamenteComSubintervalosPares() {
        // f(x) = x^3 - 2x^2 + 3x + 1 em [0, 4] com h = 1 (4 subintervalos -> Simpson 1/3)
        double[] y = {1.0, 3.0, 7.0, 19.0, 45.0};
        double exata = 64.0 - 128.0 / 3.0 + 24.0 + 4.0;
        var resultado = SimpsonRepetido.calcularDetalhado(y, 1.0);

        assertEquals(4, resultado.subintervalosSimpson13());
        assertEquals(0, resultado.subintervalosSimpson38());
        assertEquals(exata, resultado.areaTotal(), 1e-12);
    }

    @Test
    void integraPolinomioCubicoExatamenteComTresSubintervalos() {
        // f(x) = x^3 - 2x^2 + 3x + 1 em [0, 3] com h = 1 (3 subintervalos -> Simpson 3/8)
        double[] y = {1.0, 3.0, 7.0, 19.0};
        double exata = 20.25 - 18.0 + 13.5 + 3.0;
        var resultado = SimpsonRepetido.calcularDetalhado(y, 1.0);

        assertEquals(0, resultado.subintervalosSimpson13());
        assertEquals(3, resultado.subintervalosSimpson38());
        assertEquals(exata, resultado.areaTotal(), 1e-12);
    }

    @Test
    void integraPolinomioCubicoExatamenteComSeteSubintervalos() {
        // f(x) = x^3 - 2x^2 + 3x + 1 em [0, 7] com h = 1 (4 subintervalos 1/3 + 3 subintervalos 3/8)
        double[] y = new double[8];
        for (int x = 0; x < y.length; x++) {
            y[x] = Math.pow(x, 3) - 2.0 * x * x + 3.0 * x + 1.0;
        }
        double exata = 2401.0 / 4.0 - 686.0 / 3.0 + 147.0 / 2.0 + 7.0;

        assertEquals(exata, SimpsonRepetido.calcular(y, 1.0), 1e-11);
    }

    @Test
    void rejeitaEntradasInvalidas() {
        assertThrows(IllegalArgumentException.class,
                () -> SimpsonRepetido.calcular(null, 0.4));
        assertThrows(IllegalArgumentException.class,
                () -> SimpsonRepetido.calcular(new double[]{3.0, 2.0}, 0.4));
        assertThrows(IllegalArgumentException.class,
                () -> SimpsonRepetido.calcular(new double[]{3.0, 2.0, 1.0}, 0.0));
        assertThrows(IllegalArgumentException.class,
                () -> SimpsonRepetido.calcular(new double[]{3.0, 2.0, 1.0}, -0.4));
        assertThrows(IllegalArgumentException.class,
                () -> SimpsonRepetido.calcular(new double[]{3.0, Double.NaN, 1.0}, 0.4));
    }
}
