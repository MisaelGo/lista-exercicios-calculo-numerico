package br.edu.univasf.calculonumerico.console;

import br.edu.univasf.calculonumerico.questoes.Questao01;
import br.edu.univasf.calculonumerico.questoes.Questao02;
import br.edu.univasf.calculonumerico.questoes.Questao03;
import br.edu.univasf.calculonumerico.questoes.Questao04;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/** Menu unico para as quatro questoes com retorno automatico ao menu. */
public final class ConsoleApp {
    private final Scanner entrada;
    private final PrintStream saida;

    public ConsoleApp(InputStream entrada, PrintStream saida) {
        this.entrada = new Scanner(entrada);
        this.saida = saida;
    }

    public void iniciar() {
        while (true) {
            saida.println("\n=== ATIVIDADE COMPLEMENTAR - CALCULO NUMERICO ===");
            saida.println("1 - Questao 01.2: metodos diretos (Allyson)");
            saida.println("2 - Questao 02.1: Ponte de Wheatstone / Gauss-Seidel (Misael)");
            saida.println("3 - Questao 03.1: Lei de Moore (Carlos)");
            saida.println("4 - Questao 04.3: secao do navio (Allyson e Carlos)");
            saida.println("0 - Sair");
            final int opcao;
            try {
                opcao = ConsoleInput.inteiro(entrada, saida, "Escolha: ", 0, 4);
            } catch (IllegalStateException ex) {
                saida.println("Fim da entrada. Programa encerrado.");
                return;
            }
            switch (opcao) {
                case 1 -> Questao01.executar(saida);
                case 2 -> Questao02.executar(entrada, saida);
                case 3 -> Questao03.executar(saida);
                case 4 -> Questao04.executar(saida);
                case 0 -> { saida.println("Programa encerrado."); return; }
                default -> throw new IllegalStateException("Opcao nao prevista: " + opcao);
            }
        }
    }
}
