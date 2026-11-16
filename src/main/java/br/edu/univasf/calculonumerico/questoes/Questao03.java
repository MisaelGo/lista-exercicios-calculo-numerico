package br.edu.univasf.calculonumerico.questoes;

import br.edu.univasf.calculonumerico.console.ConsoleInput;
import br.edu.univasf.calculonumerico.metodos.AjusteLeiDeMoore;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Questao 03.1, implementada por Carlos: previsao pela Lei de Moore em escala log10(N). */
public final class Questao03 {
    private static final double[] ANOS_PREVISAO_PADRAO = {2010.0, 2020.0};

    private Questao03() { }

    public static void executar(Scanner entrada, PrintStream saida) {
        saida.println("\n--- Questao 03.1: Lei de Moore e Ajuste em Escala log10(N) ---");
        saida.println("1 - Dados originais do enunciado (1971 a 2000) e previsoes para 2010 e 2020");
        saida.println("2 - Acrescentar novas observacoes aos dados originais");
        saida.println("3 - Informar nova tabela completa (anos e transistores)");
        int modo = ConsoleInput.inteiroPadrao(entrada, saida, "Modo de execucao (1 a 3)", 1, 1, 3);

        if (modo == 1) {
            executarDadosOriginais(entrada, saida);
        } else if (modo == 2) {
            executarComPontosAdicionais(entrada, saida);
        } else {
            executarTabelaPersonalizada(entrada, saida);
        }
    }

    private static void executarDadosOriginais(Scanner entrada, PrintStream saida) {
        double[] anos = AjusteLeiDeMoore.anosPadrao();
        double[] transistores = AjusteLeiDeMoore.transistoresPadrao();
        String[] chips = AjusteLeiDeMoore.chipsPadrao();

        int extras = ConsoleInput.inteiroPadrao(entrada, saida,
                "Quantidade de anos adicionais alem de 2010 e 2020 (0 a 10)", 0, 0, 10);
        double[] anosPrevisao = lerAnosPrevisao(entrada, saida, true, extras);

        calcularEImprimir(saida, chips, anos, transistores, anosPrevisao);
    }

    private static void executarComPontosAdicionais(Scanner entrada, PrintStream saida) {
        double[] anosBase = AjusteLeiDeMoore.anosPadrao();
        double[] transBase = AjusteLeiDeMoore.transistoresPadrao();
        String[] chipsBase = AjusteLeiDeMoore.chipsPadrao();

        int novos = ConsoleInput.inteiroPadrao(entrada, saida,
                "Quantidade de novos pontos a adicionar (1 a 20)", 1, 1, 20);

        int total = anosBase.length + novos;
        String[] chips = new String[total];
        double[] anos = new double[total];
        double[] transistores = new double[total];

        System.arraycopy(chipsBase, 0, chips, 0, chipsBase.length);
        System.arraycopy(anosBase, 0, anos, 0, anosBase.length);
        System.arraycopy(transBase, 0, transistores, 0, transBase.length);

        saida.println("ENTER aceita o valor sugerido; virgula ou ponto sao validos.");
        for (int i = anosBase.length; i < total; i++) {
            int ordem = i - anosBase.length + 1;
            chips[i] = "Novo " + ordem;
            anos[i] = ConsoleInput.decimalPadrao(entrada, saida,
                    String.format("Ano da nova observacao %d", ordem), 2000.0 + ordem * 2, false);
            transistores[i] = ConsoleInput.decimalPadrao(entrada, saida,
                    String.format("Numero de transistores N (> 0) da observacao %d", ordem),
                    1.0e8 * ordem, true);
        }

        int qtdPrevisoes = ConsoleInput.inteiroPadrao(entrada, saida,
                "Quantidade de anos para previsao alem de 2010 e 2020 (0 a 10)", 0, 0, 10);
        double[] anosPrevisao = lerAnosPrevisao(entrada, saida, true, qtdPrevisoes);

        calcularEImprimir(saida, chips, anos, transistores, anosPrevisao);
    }

    private static void executarTabelaPersonalizada(Scanner entrada, PrintStream saida) {
        int n = ConsoleInput.inteiroPadrao(entrada, saida, "Quantidade de pontos da tabela (2 a 100)", 4, 2, 100);
        double[] anos = new double[n];
        double[] transistores = new double[n];
        String[] chips = new String[n];

        saida.println("Informe os pares (ano, numero de transistores N > 0):");
        for (int i = 0; i < n; i++) {
            chips[i] = "Ponto " + (i + 1);
            anos[i] = ConsoleInput.decimalPadrao(entrada, saida,
                    String.format("Ano[%d]", i + 1), 1970.0 + i * 5.0, false);
            transistores[i] = ConsoleInput.decimalPadrao(entrada, saida,
                    String.format("Transistores N[%d] (> 0)", i + 1), 1000.0 * Math.pow(2.0, i), true);
        }

        int qtdPrevisoes = ConsoleInput.inteiroPadrao(entrada, saida,
                "Quantidade de anos para previsao (1 a 20)", 2, 1, 20);
        double[] anosPrevisao = lerAnosPrevisao(entrada, saida, false, qtdPrevisoes);

        calcularEImprimir(saida, chips, anos, transistores, anosPrevisao);
    }

    private static double[] lerAnosPrevisao(Scanner entrada, PrintStream saida,
                                            boolean incluirPadroes2010E2020, int quantidadeExtra) {
        List<Double> lista = new ArrayList<>();
        if (incluirPadroes2010E2020) {
            for (double anoPadrao : ANOS_PREVISAO_PADRAO) {
                lista.add(anoPadrao);
            }
        }
        for (int i = 0; i < quantidadeExtra; i++) {
            double sugestao = (i < ANOS_PREVISAO_PADRAO.length) ? ANOS_PREVISAO_PADRAO[i] : 2020.0 + (i - 1) * 5.0;
            String rotulo = incluirPadroes2010E2020
                    ? String.format("Ano adicional %d para previsao", i + 1)
                    : String.format("Ano %d para previsao", i + 1);
            lista.add(ConsoleInput.decimalPadrao(entrada, saida, rotulo, sugestao, false));
        }
        return lista.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private static void calcularEImprimir(PrintStream saida, String[] chips, double[] anos,
                                          double[] transistores, double[] anosPrevisao) {
        try {
            AjusteLeiDeMoore.Resultado resultado = AjusteLeiDeMoore.ajustar(anos, transistores);

            saida.println("\nTabela de dados e transformacao y = log10(N):");
            saida.printf("%-12s %8s %15s %11s %11s %16s %10s%n",
                    "Chip/Ponto", "Ano", "N observado", "log10(N)", "y ajust.", "N ajustado", "Erro rel.");
            List<AjusteLeiDeMoore.PontoAjustado> pontos = resultado.pontos();
            for (int i = 0; i < pontos.size(); i++) {
                AjusteLeiDeMoore.PontoAjustado p = pontos.get(i);
                String nome = (chips != null && i < chips.length) ? chips[i] : ("Ponto " + (i + 1));
                saida.printf("%-12s %8.1f %15.0f %11.6f %11.6f %16.2f %9.2f%%%n",
                        nome, p.ano(), p.transistoresObservados(),
                        p.log10Observado(), p.log10Ajustado(), p.transistoresAjustados(),
                        p.erroRelativoPercentual());
            }

            saida.println("\nModelo ajustado por minimos quadrados (escala log10):");
            saida.printf("  y(a) = log10(N(a)) = %.6f + %.9f * a%n",
                    resultado.intercepto(), resultado.inclinacao());
            saida.printf("  Forma centrada (a_ref = %.1f): log10(N(a)) = %.6f + %.9f * (a - %.1f)%n",
                    resultado.anoReferencia(), resultado.log10NoAnoReferencia(),
                    resultado.inclinacao(), resultado.anoReferencia());
            saida.printf("  Reconstrucao inversa N = 10^y: N(a) = %.2f * 10^(%.9f * (a - %.1f))%n",
                    Math.pow(10.0, resultado.log10NoAnoReferencia()),
                    resultado.inclinacao(), resultado.anoReferencia());
            saida.printf("  Fator de crescimento anual: %.6f/ano | R^2 (log10): %.6f | Sr: %.6f | RMSE(log10): %.6f%n",
                    resultado.fatorCrescimentoAnual(), resultado.rQuadrado(),
                    resultado.somaQuadradosResiduosLog10(), resultado.rmseLog10());
            if (Double.isFinite(resultado.tempoDuplicacaoAnos())) {
                saida.printf("  Tempo medio de duplicacao estimado: %.4f anos (%.2f meses)%n",
                        resultado.tempoDuplicacaoAnos(), resultado.tempoDuplicacaoAnos() * 12.0);
            }

            saida.println("\nPrevisoes de numero de transistores (N = 10^y):");
            boolean houveExtrapolacao = false;
            for (double anoAlvo : anosPrevisao) {
                AjusteLeiDeMoore.Previsao prev = resultado.prever(anoAlvo);
                houveExtrapolacao |= prev.extrapolacao();
                String tipo = prev.extrapolacao() ? "EXTRAPOLACAO" : "INTERPOLACAO";
                saida.printf("  Ano %7.1f -> log10(N) = %9.6f | N estimado = %18.2f (%.6e) [%s]%n",
                        prev.ano(), prev.log10Estimado(),
                        prev.transistoresEstimados(), prev.transistoresEstimados(), tipo);
            }

            if (houveExtrapolacao) {
                saida.printf("%nRessalva sobre extrapolacao: o ajuste foi calibrado no intervalo [%.1f, %.1f].%n",
                        resultado.anoMinimo(), resultado.anoMaximo());
                saida.println("As projecoes fora dessa faixa sao estimativas matematicas que pressupoem a manutencao");
                saida.println("da taxa exponencial historica, nao configurando contagens fisicas verificadas.");
            }
        } catch (IllegalArgumentException ex) {
            saida.println("Nao foi possivel ajustar os dados: " + ex.getMessage());
        }
    }
}
