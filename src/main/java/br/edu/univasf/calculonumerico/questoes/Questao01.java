package br.edu.univasf.calculonumerico.questoes;

import br.edu.univasf.calculonumerico.console.ConsoleInput;
import br.edu.univasf.calculonumerico.metodos.EliminacaoGauss;

import java.io.PrintStream;
import java.util.Scanner;

/** Questao 01.2, implementada por Allyson: producao de componentes por Eliminacao de Gauss. */
public final class Questao01 {
    private static final double[][] CONSUMO_PADRAO = {
        {15.0, 17.0, 19.0},
        {0.30, 0.40, 0.55},
        {1.00, 1.20, 1.50}
    };

    private Questao01() { }

    public static void executar(Scanner entrada, PrintStream saida) {
        saida.println("\n--- Questao 01.2: Producao de Componentes Eletricos (Eliminacao de Gauss) ---");
        saida.println("1 - Problema dos componentes eletricos (ENTER aceita os estoques padrao)");
        saida.println("2 - Sistema linear generico A*x = b (Ordem N x N)");
        int modo = ConsoleInput.inteiroPadrao(entrada, saida, "Modo de execucao (1 ou 2)", 1, 1, 2);

        if (modo == 1) {
            executarProblemaComponentes(entrada, saida);
        } else {
            executarSistemaGenerico(entrada, saida);
        }
    }

    private static void executarProblemaComponentes(Scanner entrada, PrintStream saida) {
        saida.println("\nEstoques padrao convertidos (1 kg = 1000 g): Metal=3890 g, Plastico=95 g, Borracha=282 g.");
        saida.println("ENTER aceita o valor padrao; virgula ou ponto sao validos.");

        double metalG = ConsoleInput.decimalPadrao(entrada, saida, "Estoque de Metal (g)", 3890.0, true);
        double plasticoG = ConsoleInput.decimalPadrao(entrada, saida, "Estoque de Plastico (g)", 95.0, true);
        double borrachaG = ConsoleInput.decimalPadrao(entrada, saida, "Estoque de Borracha (g)", 282.0, true);

        double[] b = {metalG, plasticoG, borrachaG};
        resolverEImprimir(saida, CONSUMO_PADRAO, b, true);
    }

    private static void executarSistemaGenerico(Scanner entrada, PrintStream saida) {
        int n = ConsoleInput.inteiroPadrao(entrada, saida, "Ordem N do sistema (1 a 20)", 3, 1, 20);
        double[][] a = new double[n][n];
        double[] b = new double[n];

        saida.println("Informe os coeficientes da matriz A e os termos de b:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double padrao = (i == j) ? 1.0 : 0.0;
                a[i][j] = ConsoleInput.decimalPadrao(entrada, saida,
                        String.format("A[%d][%d]", i + 1, j + 1), padrao, false);
            }
            b[i] = ConsoleInput.decimalPadrao(entrada, saida,
                    String.format("b[%d]", i + 1), 1.0, false);
        }

        resolverEImprimir(saida, a, b, false);
    }

    private static void resolverEImprimir(PrintStream saida, double[][] a, double[] b, boolean problemaComponentes) {
        try {
            EliminacaoGauss.Resultado r = EliminacaoGauss.resolver(a, b);
            double[] x = r.solucao();

            saida.println("\nSistema escalonado [U | c]:");
            double[][] u = r.matrizEscalonada();
            double[] c = r.vetorEscalonado();
            for (int i = 0; i < u.length; i++) {
                saida.print("  | ");
                for (int j = 0; j < u[i].length; j++) {
                    saida.printf("%10.4f ", u[i][j]);
                }
                saida.printf("| %10.4f |%n", c[i]);
            }

            saida.println("\nSolucao encontrada:");
            boolean viavel = true;
            for (int i = 0; i < x.length; i++) {
                if (problemaComponentes) {
                    saida.printf("Componente %d (x%d) = %.6f unidades/dia%n", i + 1, i + 1, x[i]);
                    if (x[i] < -1e-9) {
                        viavel = false;
                    }
                } else {
                    saida.printf("x%d = %.6f%n", i + 1, x[i]);
                }
            }
            saida.printf("Residuo maximo ||Ax - b||_inf: %.3e%n", r.residuoMaximo());

            if (problemaComponentes) {
                saida.println(viavel
                        ? "Viabilidade fisica: producao viavel (quantidades nao-negativas)."
                        : "Viabilidade fisica: solucao inviavel na pratica (possui quantidades negativas).");
            }
        } catch (IllegalArgumentException ex) {
            saida.println("Nao foi possivel calcular: " + ex.getMessage());
        }
    }
}