package br.edu.univasf.calculonumerico.questoes;

import br.edu.univasf.calculonumerico.console.ConsoleInput;
import br.edu.univasf.calculonumerico.integracao.SimpsonRepetido;
import br.edu.univasf.calculonumerico.integracao.TrapezioRepetido;

import java.io.PrintStream;
import java.util.Scanner;

/** Questao 04.3. Responsaveis: Allyson (Trapezio) e Carlos (Simpson). */
public final class Questao04 {
    private static final double PASSO_PADRAO = 0.4;
    private static final double[] LARGURAS_PADRAO = {
        3.00, 2.92, 2.75, 2.52, 2.30, 1.84, 0.92, 0.00
    };

    private Questao04() { }

    public static void executar(Scanner entrada, PrintStream saida) {
        saida.println("\n--- Questao 04.3: Area da Secao Transversal do Navio ---");
        saida.println("1 - Dados padrao da figura do navio (8 larguras, h = 0,4 m)");
        saida.println("2 - Informar novos pontos igualmente espacados");
        int modo = ConsoleInput.inteiroPadrao(entrada, saida, "Modo de execucao (1 ou 2)", 1, 1, 2);

        double passo;
        double[] valores;

        if (modo == 1) {
            saida.println("ENTER aceita o passo padrao; virgula ou ponto sao validos.");
            passo = ConsoleInput.decimalPadrao(entrada, saida, "Espacamento vertical h (m)", PASSO_PADRAO, true);
            valores = LARGURAS_PADRAO.clone();
            for (int i = 0; i < valores.length; i++) {
                valores[i] = ConsoleInput.decimalPadrao(entrada, saida,
                        String.format("Largura y[%d] (profundidade %.2f m)", i, i * passo),
                        valores[i], false);
            }
        } else {
            int n = ConsoleInput.inteiroPadrao(entrada, saida, "Quantidade de pontos (2 a 100)", 8, 2, 100);
            passo = ConsoleInput.decimalPadrao(entrada, saida, "Espacamento constante h (> 0)", PASSO_PADRAO, true);
            valores = new double[n];
            for (int i = 0; i < n; i++) {
                double padrao = (i < LARGURAS_PADRAO.length) ? LARGURAS_PADRAO[i] : 0.0;
                valores[i] = ConsoleInput.decimalPadrao(entrada, saida,
                        String.format("y[%d] (x = %.2f)", i, i * passo), padrao, false);
            }
        }

        calcularEImprimir(saida, valores, passo);
    }

    private static void calcularEImprimir(PrintStream saida, double[] valores, double passo) {
        try {
            double areaTrapezio = TrapezioRepetido.calcular(valores, passo);

            saida.printf("%nSubintervalos (m): %d | Espacamento (h): %.4f m%n", valores.length - 1, passo);
            saida.printf("Area pela Regra do Trapezio Repetida: %.6f m^2%n", areaTrapezio);

            try {
                double areaSimpson = SimpsonRepetido.calcular(valores, passo);
                double diferenca = Math.abs(areaSimpson - areaTrapezio);
                saida.printf("Area pela Regra de Simpson Repetida:  %.6f m^2%n", areaSimpson);
                saida.printf("Diferenca absoluta |Simpson - Trapezio|: %.6f m^2%n", diferenca);
            } catch (UnsupportedOperationException ignored) {
                saida.println("Regra de Simpson: aguardando integracao do modulo de Carlos.");
            }
        } catch (IllegalArgumentException ex) {
            saida.println("Nao foi possivel calcular: " + ex.getMessage());
        }
    }
}