# Divisão detalhada — Equipe 12

**Exercícios sorteados:** 01.2, 02.1, 03.1 e 04.3. Cada pessoa é responsável também pela escrita da metodologia e dos resultados de suas questões. O relatório exige uma pequena descrição das contribuições individuais.

## Misael — 02.1 e integração do software (implementado)

1. Definir sentidos de I1 a I6 e potenciais dos nós da Ponte de Wheatstone conforme o circuito do professor.
2. Aplicar as leis de Kirchhoff e Ohm; montar sistema nodal 2x2 e resolvê-lo por **Gauss-Seidel**.
3. Determinar as seis correntes a partir dos potenciais, calcular resíduos de Kirchhoff e testar convergência.
4. Permitir tensões e resistências novas, tolerância e máximo de iterações editáveis; tratar entradas inválidas.
5. Implementar o menu com as quatro questões, repetição de cálculos e opção de saída.
6. Redigir as seções 2.3 e 3.2 do relatório, ajudar na introdução e organizar a revisão final.
7. Revisar os Pull Requests e rodar os testes integrados ao final.

**Arquivos:** metodos/GaussSeidel.java, circuitos/PonteWheatstone.java, questoes/Questao02.java, console/, Main.java e respectivos testes. Modelagem em QUESTAO_02_1.md.

## Allyson — 01.2: sistema de métodos diretos

**Branch:** feat/allyson-questao-01-2

1. Incógnitas x1,x2,x3: quantidades dos componentes 1,2,3.
2. Converter as quantidades totais para gramas: metal 3890 g, plástico 95 g, borracha 282 g.
3. Montar o sistema: 15x1+17x2+19x3=3890; 0,30x1+0,40x2+0,55x3=95; 1,0x1+1,2x2+1,5x3=282.
4. Resolver usando método direto, preferencialmente eliminação de Gauss com pivotamento parcial.
5. Receber dados alternativos; verificar dimensões, singularidade e resíduos da solução.
6. Substituir o aviso de Questao01.java por uma interface de entrada e saída; adicionar testes.
7. Escrever a metodologia e os resultados da questão 01.2.

## Carlos — 03.1: previsão pela Lei de Moore

**Branch:** feat/carlos-questao-03-1

1. Transcrever a tabela do enunciado (anos 1971 a 2000 e quantidade de transistores).
2. Transformar N em log10(N), como exige a questão.
3. Ajustar uma função de tendência — por exemplo, reta por mínimos quadrados para log10(N) em função do ano, seguida da transformação inversa.
4. Obter previsões de N para 2010 e 2020 usando somente os dados originais da questão.
5. Receber outras tabelas/anos; justificar a extrapolação e discutir suas limitações.
6. Substituir o aviso de Questao03.java, criar testes e redigir metodologia/resultados.

## Allyson — 04.3: Regra do Trapézio Repetida

**Branch:** feat/allyson-trapezio

1. Utilizar as larguras de seção: 3,00; 2,92; 2,75; 2,52; 2,30; 1,84; 0,92; 0,00 metros.
2. Adotar espaçamento h = 0,4 m entre medições (sete subintervalos).
3. Calcular área com h * (y0/2 + y1 + ... + y(n-1) + yn/2).
4. Implementar integracao/TrapezioRepetido.java para vetores e passos alternativos, com validações.
5. Criar testes e apresentar cálculo, resultado em m² e contribuição escrita.

## Carlos — 04.3: Regra de Simpson Repetida

**Branch:** feat/carlos-simpson

1. Usar as mesmas oito larguras e passo de 0,4 m.
2. Como são sete subintervalos, usar uma combinação coerente: Simpson 1/3 em quatro subintervalos e Simpson 3/8 em três subintervalos. Justificar por que 1/3 sozinho não serve para n ímpar.
3. Implementar integracao/SimpsonRepetido.java com validações e dados alternativos.
4. Criar testes, comparar a área com o Trapézio e redigir a seção correspondente.
5. Junto com Allyson, integrar os dois métodos na Questao04.java e permitir nova entrada.

## Condições de entrega e revisão

- Os enunciados e figuras devem ser conferidos na lista original do professor.
- Cada integrante desenvolve em sua branch e abre PR com explicação e resultados.
- Os autores das duas partes da questão 04.3 devem combinar a integração.
- Antes de entregar, todos verificam funções do menu, casos alternativos, resultados, JUnit e relatório.
- **Apresentação:** 17/11/2026 (comparecimento obrigatório dos três).
- **Entrega do relatório e código:** 20/11/2026.
