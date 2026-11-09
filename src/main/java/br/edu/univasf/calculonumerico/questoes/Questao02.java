package br.edu.univasf.calculonumerico.questoes;

import br.edu.univasf.calculonumerico.circuitos.PonteWheatstone;
import br.edu.univasf.calculonumerico.console.ConsoleInput;
import java.io.PrintStream;
import java.util.Scanner;

/** Questao 02.1, implementada por Misael: Ponte de Wheatstone. */
public final class Questao02 {
    private Questao02() { }

    public static void executar(Scanner entrada, PrintStream saida) {
        saida.println("\n--- Questao 02.1: Ponte de Wheatstone ---");
        saida.println("Nos A=E e D=0; sentidos: I1 A->B, I2 A->C, I3 C->D,");
        saida.println("I4 B->D, I5 B->C, I6 D->A.");
        saida.println("ENTER aceita o valor padrao; virgula ou ponto sao validos.");
        double e = ConsoleInput.decimalPadrao(entrada, saida, "Tensao E (V)", 30, false);
        double r1 = ConsoleInput.decimalPadrao(entrada, saida, "R1 (ohms)", 20, true);
        double r2 = ConsoleInput.decimalPadrao(entrada, saida, "R2 (ohms)", 120, true);
        double r3 = ConsoleInput.decimalPadrao(entrada, saida, "R3 (ohms)", 120, true);
        double r4 = ConsoleInput.decimalPadrao(entrada, saida, "R4 (ohms)", 120, true);
        double r5 = ConsoleInput.decimalPadrao(entrada, saida, "R5 (ohms)", 120, true);
        double tolerancia = ConsoleInput.decimalPadrao(entrada, saida, "Tolerancia numerica", 1e-10, true);
        int maxIteracoes = ConsoleInput.inteiroPadrao(entrada, saida,
                "Maximo de iteracoes (1 a 100000)", 1000, 1, 100000);
        try {
            PonteWheatstone.Resultado r = PonteWheatstone.calcular(e, r1, r2, r3, r4, r5,
                    tolerancia, maxIteracoes);
            saida.printf("%nPotenciais: VB = %.9f V; VC = %.9f V%n", r.potencialB(), r.potencialC());
            saida.printf("I1 = %.9f A%nI2 = %.9f A%nI3 = %.9f A%n", r.i1(), r.i2(), r.i3());
            saida.printf("I4 = %.9f A%nI5 = %.9f A%nI6 = %.9f A%n", r.i4(), r.i5(), r.i6());
            saida.printf("Iteracoes: %d | Residuo nodal: %.3e A%n", r.iteracoes(), r.residuoNodal());
            saida.printf("Verificacao KCL: %.3e A | KVL: %.3e V%n",
                    r.residuoKirchhoffCorrentes(), r.residuoKirchhoffTensoes());
            saida.println("Corrente negativa indica fluxo oposto ao sentido adotado.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            saida.println("Nao foi possivel calcular: " + ex.getMessage());
        }
    }
}
