package br.edu.univasf.calculonumerico.console;

import java.io.PrintStream;
import java.util.Scanner;

/** Le linhas completas, aceitando ponto ou virgula como separador decimal. */
public final class ConsoleInput {
    private ConsoleInput() { }

    public static int inteiro(Scanner entrada, PrintStream saida, String mensagem, int minimo, int maximo) {
        while (true) {
            saida.print(mensagem);
            if (!entrada.hasNextLine()) throw new IllegalStateException("Fim da entrada.");
            String texto = entrada.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) return valor;
            } catch (NumberFormatException ignored) { }
            saida.printf("Informe um numero inteiro entre %d e %d.%n", minimo, maximo);
        }
    }
    public static int inteiroPadrao(Scanner entrada, PrintStream saida, String mensagem,
                                    int padrao, int minimo, int maximo) {
        while (true) {
            saida.printf("%s [padrao: %d]: ", mensagem, padrao);
            if (!entrada.hasNextLine()) throw new IllegalStateException("Fim da entrada.");
            String texto = entrada.nextLine().trim();
            if (texto.isEmpty()) return padrao;
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) return valor;
            } catch (NumberFormatException ignored) { }
            saida.printf("Informe um numero inteiro entre %d e %d.%n", minimo, maximo);
        }
    }
    public static double decimalPadrao(Scanner entrada, PrintStream saida, String rotulo,
                                      double padrao, boolean estritamentePositivo) {
        while (true) {
            saida.printf("%s [padrao: %.8g]: ", rotulo, padrao);
            if (!entrada.hasNextLine()) throw new IllegalStateException("Fim da entrada.");
            String texto = entrada.nextLine().trim();
            if (texto.isEmpty()) return padrao;
            try {
                double valor = Double.parseDouble(texto.replace(',', '.'));
                if (Double.isFinite(valor) && (!estritamentePositivo || valor > 0.0)) return valor;
            } catch (NumberFormatException ignored) { }
            saida.println(estritamentePositivo
                    ? "Informe um numero finito maior que zero." : "Informe um numero real finito.");
        }
    }
}
