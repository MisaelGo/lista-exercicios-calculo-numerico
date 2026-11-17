# Atividade Complementar de Cálculo Numérico — Equipe 12

Projeto desenvolvido em **Java 17 e Maven** para resolver quatro problemas de Cálculo Numérico da UNIVASF. As quatro questões estão implementadas na branch `main`, com interface de terminal, validação de entradas e testes automatizados.

## Integrantes e responsabilidades

| Integrante | Responsabilidade |
|---|---|
| Misael Gomes Bonfim Rodrigues | Questão 02.1 (Gauss-Seidel/Ponte de Wheatstone), integração, menu e validação de entradas |
| Allyson André Almeida de Castro Paes Landi | Questão 01.2 (Eliminação de Gauss) e Regra do Trapézio na questão 04.3 |
| Carlos André Alves Torres Filho | Questão 03.1 (Lei de Moore) e Regra de Simpson na questão 04.3 |

## Questões implementadas

| Opção | Questão | Método e recursos | Resultado com dados do enunciado |
|---|---|---|---|
| 1 | **01.2 — Produção de componentes elétricos** | Eliminação de Gauss com pivotamento parcial; informa estoques alternativos ou sistema linear genérico | 90, 60 e 80 componentes dos tipos 1, 2 e 3 |
| 2 | **02.1 — Ponte de Wheatstone** | Sistema nodal, Gauss-Seidel, correntes I1 a I6 e verificações das leis de Kirchhoff; permite alterar E, resistências e tolerância | VB ≈ 24,782609 V; VC ≈ 18,260870 V; convergência em 10 iterações com a tolerância padrão |
| 3 | **03.1 — Lei de Moore** | Regressão linear por mínimos quadrados em `log10(N)`; tabela padrão, novos pontos e previsões | Estimativa de ~6,08448 × 10^8 transistores em 2010 e ~1,43236 × 10^10 em 2020 |
| 4 | **04.3 — Seção transversal de navio** | Trapézio Repetido e Simpson (1/3 composto + 3/8 nos sete subintervalos); aceita outras medidas | Trapézio: 5,900000 m²; Simpson: 5,928333 m² |

Os resultados são **aproximações numéricas** para os dados especificados no enunciado. As previsões da Lei de Moore são extrapolações do conjunto histórico, não contagens verificadas de processadores desses anos.

## Requisitos

- Java Development Kit (JDK) 17 ou superior.
- Apache Maven 3.8 ou superior.

## Como compilar, testar e executar

Na pasta raiz do repositório:

```bash
mvn clean test
mvn package
java -jar target/lista-exercicios-calculo-numerico-1.0.0-SNAPSHOT.jar
```

No menu, selecione `1`, `2`, `3` ou `4` para executar uma questão, ou `0` para sair. Pressionar **ENTER** nos campos com valor padrão mantém os dados do enunciado; há opções para informar outros valores. Depois de um cálculo, o programa retorna ao menu.

## Organização do código

```text
src/
  main/java/br/edu/univasf/calculonumerico/
    Main.java                 # inicialização
    console/                 # menu e leitura validada
    questoes/                # entrada, saída e integração das quatro questões
    metodos/                 # EliminacaoGauss, GaussSeidel, AjusteLeiDeMoore
    circuitos/               # PonteWheatstone
    integracao/              # TrapezioRepetido e SimpsonRepetido
  test/java/br/edu/univasf/calculonumerico/
    ...                      # testes unitários JUnit 5
```

## Testes

O projeto utiliza **JUnit 5** e um fluxo do GitHub Actions em `.github/workflows/java-ci.yml`. A última execução verificada antes desta atualização aprovou **21 testes, sem falhas**. Para executar a suíte localmente, use `mvn clean test`.

Os testes cobrem soluções de referência, casos alternativos e rejeição de entradas inválidas. A aprovação dos testes automatizados não substitui a revisão manual das quatro opções do menu.

## Relatório e entrega

O **relatório é preparado colaborativamente no Google Docs**, fora deste repositório. Deve apresentar introdução, modelagem matemática, metodologia, resultados e discussão, conclusão, referências e uma breve descrição da contribuição individual de cada integrante.

- **Apresentação:** 17/11/2026, conforme organização da equipe; presença dos três integrantes obrigatória.
- **Entrega do relatório e código:** até 20/11/2026, pela plataforma indicada pelo professor.

Os materiais de apoio temporários da antiga pasta `docs/` foram retirados da versão atual do repositório. Caso algum conteúdo seja necessário para a redação, ele pode ser consultado no **histórico de commits**.

## Colaboração

Cada integrante desenvolveu suas funcionalidades em branches específicas, integradas à `main` por Pull Requests. Consulte `CONTRIBUTING.md` para o fluxo de contribuição.
