# Questão 03.1 — Lei de Moore e Ajuste de Dados (Carlos)

## Dados do enunciado original (página 4)

A tabela abaixo reproduz integralmente os 11 microprocessadores da tabela do enunciado original (`descricao-2o-projeto-calc-num.pdf`, p. 4), com os respectivos anos de lançamento $a_i$, quantidade de transistores $N_i$ e transformação $y_i = \log_{10}(N_i)$:

| Chip | Ano ($a_i$) | $N_i$ observado | $y_i = \log_{10}(N_i)$ | $\hat{y}_i$ ajustado | $\hat{N}_i = 10^{\hat{y}_i}$ | Erro relativo em $N$ |
|---|---:|---:|---:|---:|---:|---:|
| 4004 | 1971 | 2.250 | 3,352183 | 3,434091 | 2.717,01 | 20,76% |
| 8008 | 1972 | 3.300 | 3,518514 | 3,571274 | 3.726,26 | 12,92% |
| 8080 | 1974 | 6.000 | 3,778151 | 3,845639 | 7.008,73 | 16,81% |
| 8086 | 1978 | 29.000 | 4,462398 | 4,394371 | 24.795,39 | 14,50% |
| 80286 | 1982 | 134.000 | 5,127105 | 4,943102 | 87.720,78 | 34,54% |
| 80386 | 1986 | 275.000 | 5,439333 | 5,491834 | 310.337,37 | 12,85% |
| 80486 | 1989 | 1.200.000 | 6,079181 | 5,903383 | 800.539,50 | 33,29% |
| Pentium | 1993 | 3.100.000 | 6,491362 | 6,452114 | 2.832.137,65 | 8,64% |
| Pentium II | 1997 | 7.500.000 | 6,875061 | 7,000846 | 10.019.497,68 | 33,59% |
| Pentium III | 1999 | 9.500.000 | 6,977724 | 7,275212 | 18.845.677,07 | 98,38% |
| Pentium 4 | 2000 | 42.000.000 | 7,623249 | 7,412395 | 25.846.077,55 | 38,46% |

## Modelagem matemática (Seção 2.4 do relatório)

### Por que transformar $N$ para $\log_{10}(N)$?
O número de transistores por chip cresce exponencialmente no tempo, variando de $2{.}250$ (1971) a $42{.}000{.}000$ (2000) — mais de quatro ordens de grandeza. Um ajuste linear direto sobre $N$ não representaria esse crescimento multiplicativo, e um ajuste não linear direto atribuiria peso desproporcional apenas aos anos finais (maiores em magnitude).

Considerando o modelo exponencial $N(a) = \alpha \cdot \beta^a$ (ou $N(a) = N_0 \cdot 10^{c_1(a - a_0)}$), a aplicação do logaritmo decimal lineariza a relação:

$$y = \log_{10}(N) = c_0 + c_1 \cdot a$$

em que $c_0 = \log_{10}(\alpha)$ e $c_1 = \log_{10}(\beta)$.

### Regressão linear por mínimos quadrados
Aplicou-se o método dos mínimos quadrados ordinários sobre os pares $(a_i, y_i)$, utilizando as somas centradas na média para maior estabilidade numérica em ponto flutuante (evitando cancelamento subtrativo entre $\sum a_i^2$ e $n\bar{a}^2$):

$$c_1 = \frac{\sum_{i=1}^{n} (a_i - \bar{a})(y_i - \bar{y})}{\sum_{i=1}^{n} (a_i - \bar{a})^2}, \qquad c_0 = \bar{y} - c_1 \bar{a}$$

Para avaliar previsões sem perda de precisão por subtração de termos grandes ($c_0 \approx -267$ e $c_1 a \approx +275$), o código também utiliza a forma centrada equivalente em torno do ano inicial $a_{\text{ref}} = 1971$:

$$\hat{y}(a) = \hat{y}(a_{\text{ref}}) + c_1 (a - a_{\text{ref}})$$

e reconstrói o número estimado de transistores pela transformação inversa:

$$N(a) = 10^{\hat{y}(a)} = 10^{c_0 + c_1 a} = \hat{N}(1971) \cdot 10^{c_1 (a - 1971)}$$

O módulo `AjusteLeiDeMoore.java` valida vetores nulos, menos de 2 pontos, anos não finitos ou todos idênticos ($S_{xx} \le 10^{-15}$) e valores de $N \le 0$ ou não finitos. Na interface `Questao03.java`, o usuário pode executar os dados originais (com opção de consultar anos extras), acrescentar novas observações à tabela padrão ou inserir uma nova tabela completa e novos anos de previsão.

## Resultados obtidos (Seção 3.3 do relatório)

Com os 11 pontos do enunciado (1971–2000), obtiveram-se os parâmetros:

- **Inclinação:** $c_1 = 0{,}137182898\ \text{ano}^{-1}$
- **Intercepto:** $c_0 = -266{,}953401$
- **Forma centrada ($a_{\text{ref}} = 1971$):** $\log_{10}(N(a)) = 3{,}434091 + 0{,}137182898\,(a - 1971)$
- **Função reconstruída:** $N(a) = 2717{,}01 \cdot 10^{0{,}137182898\,(a - 1971)} \approx 2717{,}01 \cdot (1{,}371459)^{a - 1971}$
- **Indicadores na escala $\log_{10}$:** $R^2 = 0{,}989521$, $S_r = 0{,}236514$ e $\text{RMSE}(\log_{10}) = 0{,}146633$
- **Tempo médio de duplicação estimado:** $T_d = \frac{\log_{10}(2)}{c_1} = 2{,}1944\text{ anos} \approx 26{,}33\text{ meses}$

### Previsões para 2010 e 2020

| Ano | $\log_{10}(N)$ estimado | $N$ estimado (transistores) | Observação |
|---|---:|---:|---|
| 2010 | 8,784224 | $608{.}448{.}217$ ($6{,}084482 \times 10^8$) | Extrapolação fora de $[1971, 2000]$ |
| 2020 | 10,156053 | $14{.}323{.}613{.}778$ ($1{,}432361 \times 10^{10}$) | Extrapolação fora de $[1971, 2000]$ |

Gráfico dos dados históricos e reta ajustada em escala $\log_{10}$: [figura3_lei_de_moore.png](figuras/figura3_lei_de_moore.png).

### Discussão sobre erro e limitações da extrapolação
1. **Qualidade do ajuste no domínio histórico:** $R^2 = 0{,}989521$ mostra excelente aderência linear em escala logarítmica entre 1971 e 2000, com tempo médio de duplicação de $2{,}19\text{ anos}$ ($26{,}33\text{ meses}$), próximo à formulação revisada da Lei de Moore (cerca de 2 anos) e um pouco acima dos 18 meses citados na formulação inicial de 1965.
2. **Amplificação exponencial dos desvios:** Embora o desvio em $\log_{10}(N)$ seja pequeno ($\text{RMSE} = 0{,}1466$), a transformação inversa $N = 10^y$ amplia erros lineares para desvios multiplicativos expressivos na escala original (por exemplo, `Pentium III` em 1999 apresenta erro relativo de $98{,}38\%$ em $N$ devido a um salto abaixo da reta antes do salto do `Pentium 4`).
3. **Limites da extrapolação:** Os valores para 2010 e 2020 são **extrapolações matemáticas** de 10 e 20 anos além da última observação (2000), não contagens históricas verificadas. O modelo pressupõe que o ritmo exponencial médio de 1971–2000 permaneça constante indefinidamente, ignorando restrições físicas (limites quânticos/atômicos da litografia, dissipação térmica), econômicas e arquiteturais (migração para múltiplos núcleos, chiplets e aceleradores especializados).

## Evidências de testes executados
- **Testes unitários (`AjusteLeiDeMooreTest`):** 3 testes JUnit 5 aprovados (caso original 1971–2000 com previsões 2010/2020, ajuste exato de série exponencial pura $N = 100 \cdot 2^{a-2000}$ e rejeição de entradas inválidas).
- **Caso original (CLI modo 1):** reproduziu os 11 pontos, $R^2 = 0{,}989521$ e previsões de $6{,}084482 \times 10^8$ (2010) e $1{,}432361 \times 10^{10}$ (2020).
- **Caso alternativo (CLI modo 2 e modo 3):** testado acrescentando novo ponto `(2004; 125.000.000)` à tabela padrão ($R^2 = 0{,}991410$) e com tabela personalizada de 3 pontos exponenciais exatos `(2000, 1000)`, `(2002, 4000)`, `(2004, 16000)` prevendo exatamente `64.000` (2006) e `256.000` (2008).
- **Entradas inválidas (CLI modo 3):** validada a rejeição de texto não numérico (`"abc"`), transistores $\le 0$ (`0` e `-5`) e anos repetidos (`1990` e `1990`) sem interromper o programa.
