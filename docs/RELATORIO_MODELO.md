# Modelo de relatório acadêmico — Equipe 12

**Título sugerido:** Aplicação de métodos numéricos na resolução de problemas de engenharia

**Autores:** Misael Gomes Bonfim Rodrigues; Allyson André Almeida de Castro Paes Landi; Carlos André Alves Torres Filho.
**Disciplina:** Cálculo Numérico — UNIVASF.
**Questões:** 01.2, 02.1, 03.1 e 04.3.

Use esta estrutura para produzir um relatório final em PDF. As instruções abaixo são **orientações de escrita**, não texto que deva permanecer como resultado final.

## 1. Introdução — Misael (revisão de Allyson e Carlos)

- Contextualizar a necessidade de métodos numéricos em situações de engenharia.
- Introduzir sistemas lineares diretos e iterativos, mínimos quadrados e integração numérica.
- Explicar, em um parágrafo, como esses métodos aparecem nos quatro problemas sorteados.
- Declarar objetivo geral: modelar, implementar em Java e avaliar soluções dos quatro problemas, incluindo entradas alternativas.
- Encerrar com a organização do relatório. Citar fontes realmente consultadas.

## 2. Metodologia — elaboração por assunto

### 2.1. Desenvolvimento do software — Misael
Descrever JDK, Maven, organização de pacotes, entrada com Scanner, menu, possibilidade de novos cálculos, tratamento de erros e estratégia de testes. Incluir figura ou captura de tela quando o programa final estiver funcionando.

### 2.2. Métodos diretos (01.2) — Allyson
Identificar as incógnitas, conversão de kg para g, sistema 3x3, matriz de coeficientes, escolha do método direto, pivotamento e condições de singularidade. Mostrar o tratamento de novos valores e o critério de conferência de resíduos.

### 2.3. Gauss-Seidel para Ponte de Wheatstone (02.1) — Misael
Apresentar a figura (com fonte), nós, sentidos das correntes, duas equações de Kirchhoff, forma matricial, iteração Gauss-Seidel, chute inicial, tolerância e limite de iterações. Explicar como são calculadas I1 a I6, com referência a QUESTAO_02_1.md.

### 2.4. Ajuste de dados pela Lei de Moore (03.1) — Carlos
Transcrever a tabela de ano/número de transistores, transformação log10(N), procedimento para estimar parâmetros da função e obtenção das previsões para 2010 e 2020. Explicar como serão incluídos dados/anos alternativos.

### 2.5. Integração numérica na seção do navio (04.3) — Allyson e Carlos
Allyson: pontos, espaçamento h=0,4 m, fórmula do Trapézio Repetido e validação de entradas. Carlos: fórmula de Simpson 1/3 e 3/8, motivo da combinação para sete subintervalos e comparação com Trapézio.

## 3. Resultados e discussão — cada autor por questão

### 3.1. Questão 01.2 — Allyson
Inserir as três quantidades calculadas, verificar substituição nas equações e discutir coerência física. Mostrar um exemplo de dados novos e teste do método.

### 3.2. Questão 02.1 — Misael
Inserir tabela de VB, VC e I1 a I6. Valores esperados para dados do enunciado:
- VB = 24,782608696 V e VC = 18,260869565 V.
- I1 = 0,260869565 A; I2 = 0,097826087 A; I3 = 0,152173913 A.
- I4 = 0,206521739 A; I5 = 0,054347826 A; I6 = 0,358695652 A.
Informar número de iterações, resíduo, conferência KCL/KVL e teste com outros parâmetros. Explicar possíveis correntes negativas.

### 3.3. Questão 03.1 — Carlos
Inserir a função ajustada, parâmetros, tabela/gráfico com os valores originais e previsões para 2010 e 2020. Discutir o erro e os limites da extrapolação.

### 3.4. Questão 04.3 — Allyson e Carlos
Allyson: área pelo Trapézio com unidade m² e cálculos. Carlos: área por Simpson, divisão dos subintervalos e passos. Comparar diferença absoluta e relativa entre os métodos e interpretação.

### 3.5. Testes e interface — Misael (com informação dos demais)
Apresentar imagens reais do menu, novos dados, validação, resultados e testes automatizados. Não inventar testes ainda não executados.

## 4. Conclusão — texto conjunto, revisão final de Misael

Retomar os objetivos e sintetizar quais soluções foram de fato implementadas e verificadas. Comparar vantagens e limitações dos métodos e relatar dificuldades e melhorias futuras. Evitar inserir novos resultados nessa seção.

## 5. Referências — fontes reais, normalização de Misael

Relacionar a lista do professor, livros e documentação técnica **efetivamente consultados**, com dados completos e citações no corpo do texto, no padrão solicitado pela disciplina (por exemplo, ABNT). Não inventar edições, páginas ou URLs.

## Contribuição dos integrantes (exigida pelo professor)

- **Misael:** registrar sua participação efetiva na questão 02.1, arquitetura, integração e revisão.
- **Allyson:** preencher depois de concluir o sistema por método direto, Trapézio, testes e redação.
- **Carlos:** preencher depois de concluir Lei de Moore, Simpson, testes e redação.

**Importante:** este documento é um roteiro. Substitua todas as instruções por texto acadêmico, tabelas, fórmulas e resultados verificados antes da submissão.
