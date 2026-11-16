package br.edu.univasf.calculonumerico.integracao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrapezioRepetidoTest {

    @Test
    void calculaAreaSecaoDoNavioQuestao04_3() {
        double[] larguras = {3.00, 2.92, 2.75, 2.52, 2.30, 1.84, 0.92, 0.00};
        double area = TrapezioRepetido.calcular(larguras, 0.4);
        assertEquals(5.90, area, 1e-9);
    }

    @Test
    void integraFuncaoLinearExatamente() {
        // f(x) = 2x em [0, 4] com h = 1 -> integral exata = 16
        double[] y = {0.0, 2.0, 4.0, 6.0, 8.0};
        assertEquals(16.0, TrapezioRepetido.calcular(y, 1.0), 1e-12);
    }

    @Test
    void rejeitaMenosDeDoisPontosOuPassoInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> TrapezioRepetido.calcular(new double[]{3.0}, 0.4));
        assertThrows(IllegalArgumentException.class,
                () -> TrapezioRepetido.calcular(new double[]{3.0, 2.0}, 0.0));
        assertThrows(IllegalArgumentException.class,
                () -> TrapezioRepetido.calcular(new double[]{3.0, -1.0}, -0.4));
    }
}