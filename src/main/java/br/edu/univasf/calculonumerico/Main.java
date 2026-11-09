package br.edu.univasf.calculonumerico;

import br.edu.univasf.calculonumerico.console.ConsoleApp;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        new ConsoleApp(System.in, System.out).iniciar();
    }
}
