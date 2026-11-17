package br.edu.univasf.calculonumerico.metodos;

import java.util.List;

/**
 * Ajuste exponencial para a Lei de Moore via regressao linear por minimos quadrados
 * apos transformacao logaritmica y = log10(N) em funcao do ano.
 */
public final class AjusteLeiDeMoore {
    // Dados historicos originais da Questao 03.1 (pagina 4 do enunciado, 1971 a 2000).
    private static final String[] CHIPS_PADRAO = {
        "4004", "8008", "8080", "8086", "80286", "80386",
        "80486", "Pentium", "Pentium II", "Pentium III", "Pentium 4"
    };

    private static final double[] ANOS_PADRAO = {
        1971, 1972, 1974, 1978, 1982, 1986, 1989, 1993, 1997, 1999, 2000
    };

    private static final double[] TRANSISTORES_PADRAO = {
        2_250, 3_300, 6_000, 29_000, 134_000, 275_000,
        1_200_000, 3_100_000, 7_500_000, 9_500_000, 42_000_000
    };

    private AjusteLeiDeMoore() { }

    /** Guarda os valores observados, transformados (log10) e ajustados de cada ponto da tabela. */
    public record PontoAjustado(
            double ano,
            double transistoresObservados,
            double log10Observado,
            double log10Ajustado,
            double transistoresAjustados,
            double residuoLog10,
            double erroRelativoPercentual) { }

    /** Guarda a previsao para um ano alvo, indicando se esta fora da faixa historica (extrapolacao). */
    public record Previsao(
            double ano,
            double log10Estimado,
            double transistoresEstimados,
            boolean extrapolacao) { }

    /** Reune os coeficientes da reta ajustada, os indicadores de qualidade e a lista de pontos. */
    public record Resultado(
            double intercepto,
            double inclinacao,
            double anoReferencia,
            double log10NoAnoReferencia,
            double fatorCrescimentoAnual,
            double tempoDuplicacaoAnos,
            double somaQuadradosResiduosLog10,
            double rQuadrado,
            double rmseLog10,
            double anoMinimo,
            double anoMaximo,
            List<PontoAjustado> pontos) {

        public Resultado {
            pontos = List.copyOf(pontos);
        }

        /**
         * Estima log10(N) e N = 10^y para um ano alvo usando a forma centrada
         * (evita perda de precisao ao subtrair termos grandes de c0 e c1*a).
         */
        public Previsao prever(double ano) {
            if (!Double.isFinite(ano)) {
                throw new IllegalArgumentException("O ano de previsao deve ser um numero finito.");
            }
            double yEstimado = log10NoAnoReferencia + inclinacao * (ano - anoReferencia);
            // Limite de expoente (~10^308) para evitar overflow para Infinity em double
            if (!Double.isFinite(yEstimado) || yEstimado > 308.0) {
                throw new IllegalArgumentException("Previsao fora da faixa representavel em ponto flutuante.");
            }
            double nEstimado = Math.pow(10.0, yEstimado);
            boolean extrapolacao = ano < anoMinimo || ano > anoMaximo;
            return new Previsao(ano, yEstimado, nEstimado, extrapolacao);
        }
    }

    public static String[] chipsPadrao() {
        return CHIPS_PADRAO.clone();
    }

    public static double[] anosPadrao() {
        return ANOS_PADRAO.clone();
    }

    public static double[] transistoresPadrao() {
        return TRANSISTORES_PADRAO.clone();
    }

    public static Resultado ajustarDadosPadrao() {
        return ajustar(ANOS_PADRAO, TRANSISTORES_PADRAO);
    }

    public static Resultado ajustar(double[] anos, double[] transistores) {
        if (anos == null || transistores == null || anos.length != transistores.length || anos.length < 2) {
            throw new IllegalArgumentException(
                    "Sao necessarios pelo menos 2 pontos (ano, transistores) com vetores de mesmo tamanho.");
        }

        // 1. Validacao dos dados e transformacao logaritmica y = log10(N)
        int n = anos.length;
        double[] logs = new double[n];
        double somaAnos = 0.0;
        double somaLogs = 0.0;
        double anoMin = Double.POSITIVE_INFINITY;
        double anoMax = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < n; i++) {
            double ano = anos[i];
            double qtd = transistores[i];
            if (!Double.isFinite(ano)) {
                throw new IllegalArgumentException("Todos os anos devem ser numeros finitos.");
            }
            if (!Double.isFinite(qtd) || qtd <= 0.0) {
                throw new IllegalArgumentException(
                        "Todos os numeros de transistores devem ser finitos e maiores que zero para log10(N).");
            }
            logs[i] = Math.log10(qtd);
            somaAnos += ano;
            somaLogs += logs[i];
            anoMin = Math.min(anoMin, ano);
            anoMax = Math.max(anoMax, ano);
        }

        // 2. Somas centradas na media para evitar cancelamento subtrativo na regressao
        //    Sxx = soma((x - x_bar)^2), Sxy = soma((x - x_bar)*(y - y_bar)), Syy = soma((y - y_bar)^2)
        double mediaAnos = somaAnos / n;
        double mediaLogs = somaLogs / n;

        double sxx = 0.0;
        double sxy = 0.0;
        double syy = 0.0;
        for (int i = 0; i < n; i++) {
            double dx = anos[i] - mediaAnos;
            double dy = logs[i] - mediaLogs;
            sxx += dx * dx;
            sxy += dx * dy;
            syy += dy * dy;
        }

        // Se todos os anos forem iguais, Sxx = 0 e a inclinacao c1 fica indefinida
        if (sxx <= 1e-15) {
            throw new IllegalArgumentException(
                    "Os anos informados devem conter pelo menos dois valores distintos.");
        }

        // 3. Coeficientes da reta y = c0 + c1*a e forma centrada em anoReferencia
        double inclinacao = sxy / sxx;
        double intercepto = mediaLogs - inclinacao * mediaAnos;
        double anoReferencia = anoMin;
        double log10NoAnoReferencia = mediaLogs + inclinacao * (anoReferencia - mediaAnos);

        // 4. Reconstrucao N = 10^y em cada ponto e calculo dos residuos (log10 e relativo em N)
        PontoAjustado[] pontos = new PontoAjustado[n];
        double somaQuadradosResiduos = 0.0;
        for (int i = 0; i < n; i++) {
            double yAjustado = log10NoAnoReferencia + inclinacao * (anos[i] - anoReferencia);
            double nAjustado = Math.pow(10.0, yAjustado);
            double residuo = logs[i] - yAjustado;
            double erroRelativo = Math.abs(transistores[i] - nAjustado) / transistores[i] * 100.0;
            somaQuadradosResiduos += residuo * residuo;
            pontos[i] = new PontoAjustado(
                    anos[i], transistores[i], logs[i], yAjustado, nAjustado, residuo, erroRelativo);
        }

        // 5. Indicadores de qualidade do ajuste (R^2 = 1 - Sr/Syy, RMSE) e tempo de duplicacao
        double rQuadrado = (syy > 1e-15)
                ? Math.max(0.0, 1.0 - somaQuadradosResiduos / syy)
                : (somaQuadradosResiduos <= 1e-15 ? 1.0 : 0.0);
        double rmseLog10 = Math.sqrt(somaQuadradosResiduos / n);
        // Fator anual = 10^c1; tempo de duplicacao resolve 10^(c1 * Td) = 2 -> Td = log10(2) / c1
        double fatorCrescimentoAnual = Math.pow(10.0, inclinacao);
        double tempoDuplicacaoAnos = (inclinacao > 1e-15) ? Math.log10(2.0) / inclinacao : Double.NaN;

        return new Resultado(
                intercepto,
                inclinacao,
                anoReferencia,
                log10NoAnoReferencia,
                fatorCrescimentoAnual,
                tempoDuplicacaoAnos,
                somaQuadradosResiduos,
                rQuadrado,
                rmseLog10,
                anoMin,
                anoMax,
                List.of(pontos));
    }
}
