package br.edu.univasf.calculonumerico.circuitos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PonteWheatstoneTest {
    @Test
    void exemploDoEnunciado() {
        var r = PonteWheatstone.calcular(30, 20, 120, 120, 120, 120, 1e-12, 1000);
        assertEquals(24.782608695652, r.potencialB(), 1e-9);
        assertEquals(18.260869565217, r.potencialC(), 1e-9);
        assertEquals(0.260869565217, r.i1(), 1e-9);
        assertEquals(0.097826086957, r.i2(), 1e-9);
        assertEquals(0.152173913043, r.i3(), 1e-9);
        assertEquals(0.206521739130, r.i4(), 1e-9);
        assertEquals(0.054347826087, r.i5(), 1e-9);
        assertEquals(0.358695652174, r.i6(), 1e-9);
        assertTrue(r.residuoKirchhoffCorrentes() < 1e-9);
        assertTrue(r.residuoKirchhoffTensoes() < 1e-9);
    }
    @Test
    void ponteEquilibradaNaoTemCorrenteNoResistorCentral() {
        var r = PonteWheatstone.calcular(12, 100, 100, 100, 100, 35, 1e-12, 1000);
        assertEquals(0, r.i5(), 1e-9);
    }
    @Test
    void rejeitaResistenciaNula() {
        assertThrows(IllegalArgumentException.class,
                () -> PonteWheatstone.calcular(30, 0, 120, 120, 120, 120, 1e-10, 1000));
    }
}
