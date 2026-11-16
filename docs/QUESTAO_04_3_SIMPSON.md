# Questão 04.3 — Regra de Simpson Repetida e Comparação (Carlos)

## Dados do problema
A seção transversal do trecho mais largo do navio possui $n = 8$ ordenadas de largura igualmente espaçadas por passo constante $h = 0{,}4\text{ m}$ ($m = 7$ subintervalos no intervalo $[0{,}0\text{ m}; 2{,}8\text{ m}]$):

$$y = [3{,}00;\ 2{,}92;\ 2{,}75;\ 2{,}52;\ 2{,}30;\ 1{,}84;\ 0{,}92;\ 0{,}00]\ \text{m}$$

## Modelagem matemática (Seção 2.5.2 do relatório)

### Por que a Regra de Simpson 1/3 não pode ser aplicada diretamente aos 7 subintervalos?
A Regra de Simpson 1/3 utiliza interpolação quadrática a cada par de subintervalos consecutivos (três pontos por bloco) e, portanto, exige obrigatoriamente um **número par de subintervalos** ($m$ par, $m \ge 2$). Como a figura do navio fornece $n = 8$ ordenadas, tem-se $m = 7$ subintervalos (**ímpar**), impossibilitando a aplicação isolada da regra 1/3 a todo o domínio sem descartar dados ou comprometer a ordem do método.

### Composição adotada: Simpson 1/3 + Simpson 3/8
Para preservar todas as medições mantendo aproximação polinomial de alta ordem (exata para polinômios de até 3º grau em todo o intervalo), adotou-se a composição padrão em integração numérica:

1. **Regra de Simpson 1/3 Composta** nos primeiros $m - 3 = 4$ subintervalos ($x \in [0{,}0;\ 1{,}6\text{ m}]$, ordenadas $y_0$ a $y_4$):
   $$I_{1/3} = \frac{h}{3}\left[y_0 + 4 y_1 + 2 y_2 + 4 y_3 + y_4\right]$$
2. **Regra de Simpson 3/8 Simples** nos últimos $3$ subintervalos ($x \in [1{,}6;\ 2{,}8\text{ m}]$, ordenadas $y_4$ a $y_7$):
   $$I_{3/8} = \frac{3h}{8}\left[y_4 + 3 y_5 + 3 y_6 + y_7\right]$$

De forma geral, a classe `SimpsonRepetido.java` trata:
- $m$ par ($m \ge 2$): Simpson 1/3 composta em todos os $m$ subintervalos;
- $m = 3$: Simpson 3/8 simples nos 3 subintervalos;
- $m$ ímpar ($m \ge 5$): Simpson 1/3 composta nos primeiros $m - 3$ subintervalos e Simpson 3/8 nos 3 últimos;
- $m < 2$ (menos de 3 pontos), $h \le 0$ ou valores não finitos: rejeita com `IllegalArgumentException`.

## Cálculo intermediário e comparação (Seção 3.4 do relatório)

### Parcelas de Simpson para $h = 0{,}4\text{ m}$
- **Parcela Simpson 1/3 ($y_0$ a $y_4$, 4 subintervalos):**
  $$I_{1/3} = \frac{0{,}4}{3}\left[3{,}00 + 4(2{,}92) + 2(2{,}75) + 4(2{,}52) + 2{,}30\right] = \frac{0{,}4}{3} \times 32{,}56 = \frac{13{,}024}{3} \approx 4{,}341333\text{ m}^2$$

- **Parcela Simpson 3/8 ($y_4$ a $y_7$, 3 subintervalos):**
  $$I_{3/8} = \frac{3 \times 0{,}4}{8}\left[2{,}30 + 3(1{,}84) + 3(0{,}92) + 0{,}00\right] = 0{,}15 \times 10{,}58 = 1{,}587000\text{ m}^2$$

- **Área total estimada por Simpson:**
  $$I_S = I_{1/3} + I_{3/8} = 4{,}341333\text{ m}^2 + 1{,}587000\text{ m}^2 = 5{,}928333\text{ m}^2$$

### Tabela 5 — Comparação das estimativas de área (Questão 04.3)

| Método | Área estimada ($\text{m}^2$) | Responsável |
|---|---:|---|
| Trapézio Repetido | 5,9000 | Allyson |
| Simpson (1/3 em 4 subintervalos + 3/8 em 3 subintervalos) | 5,9283 | Carlos |
| Diferença absoluta $\|I_S - I_T\|$ | 0,0283 ($0{,}4779\%$ na base Simpson / $0{,}4802\%$ na base Trapézio) | Allyson e Carlos |

### Discussão da diferença entre Trapézio e Simpson
A estimativa por Simpson ($5{,}9283\text{ m}^2$) é $0{,}0283\text{ m}^2$ maior que a do Trapézio Repetido ($5{,}9000\text{ m}^2$). Isso ocorre porque o perfil da seção transversal do casco apresenta concavidade voltada para baixo ao longo da maior parte da profundidade ($y$ decresce de forma suave no topo e mais acentuada próximo à quilha): as retas secantes da Regra do Trapézio passam ligeiramente *abaixo* da curva do casco, subestimando a área, enquanto os arcos parabólicos (1/3) e cúbicos (3/8) de Simpson acompanham melhor a curvatura entre as medições.

## Evidências de testes executados
- **Testes unitários (`SimpsonRepetidoTest`):** 5 testes JUnit 5 aprovados (caso do navio 04.3 com composição 1/3 + 3/8, integração exata de polinômio cúbico para $m = 4$, $m = 3$ e $m = 7$, e rejeição de entradas inválidas).
- **Execução no console (`Questao04`):** validados o caso padrão do navio ($5{,}900000\text{ m}^2$ vs $5{,}928333\text{ m}^2$), caso alternativo de 5 pontos ($h = 0{,}5\text{ m}$), tratamento específico para apenas 2 pontos ($m = 1$ subintervalo, onde apenas o Trapézio se aplica) e rejeição de entradas inválidas no menu.
