# Como contribuir

## Preparar o computador
- Instale Java JDK 17+ e Maven 3.8+.
- Clone o repositório e abra a pasta no IntelliJ IDEA, VS Code ou outra IDE.
- Execute: mvn clean test

## Branches individuais
Cada integrante já tem uma branch nomeada no README. Para iniciar uma tarefa:

    git fetch origin
    git switch feat/allyson-questao-01-2
    git pull origin feat/allyson-questao-01-2

Substitua o nome da branch pela tarefa escolhida.

## Antes de fazer o Pull Request

1. Confirme a formulação matemática e unidades do enunciado.
2. Faça a implementação na classe da sua questão e classes auxiliares.
3. Permita novos valores de entrada sempre que pertinente.
4. Teste o exemplo do enunciado e pelo menos um caso alternativo.
5. Execute mvn clean test e registre resultados e limitações.
6. Documente as equações usadas, métodos, valores e a sua contribuição escrita.
7. Faça commit, push e abra PR para main. Não altere a tarefa de outra pessoa sem combinar.

Se a tarefa depender de alterações de outro colega (questão 04.3), atualize a branch com main depois que a primeira parte for integrada. Para evitar conflitos, dividam classes diferentes e revisem juntos o menu da questão 04.

**Não afirme no relatório que um resultado foi obtido antes de implementá-lo e testá-lo.**
