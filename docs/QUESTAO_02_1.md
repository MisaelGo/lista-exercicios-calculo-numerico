# Questão 02.1 — Ponte de Wheatstone (Misael)

## Dados e convenções

Fonte E = 30 V; R1 = 20 ohms; R2 = R3 = R4 = R5 = 120 ohms. Nó D é a referência (VD=0) e nó A tem VA=E. Sentidos: I1 A→B, I2 A→C, I3 C→D, I4 B→D, I5 B→C e I6 D→A (fonte). Corrente negativa significa sentido real oposto ao adotado.

## Sistema obtido com Kirchhoff e Ohm

Se Gk = 1/Rk, as leis das correntes nos nós B e C fornecem:

    (G1 + G4 + G5)*VB - G5*VC = E*G1
    -G5*VB + (G2 + G3 + G5)*VC = E*G2

Matriz A com diagonal [G1+G4+G5, G2+G3+G5], termos fora da diagonal -G5 e vetor b = [E*G1, E*G2].

### Fórmulas de Gauss-Seidel

    VB(k+1) = (b1 - A12*VC(k))/A11
    VC(k+1) = (b2 - A21*VB(k+1))/A22

O chute inicial é VB=VC=0. O programa testa simultaneamente a variação máxima entre iterações e o máximo resíduo |Ax-b|, comparando ambos à tolerância. Padrão: 1e-10, até 1000 iterações.

Como os coeficientes diagonais excedem a soma do módulo do termo fora da diagonal para resistências positivas, o sistema é estritamente diagonalmente dominante e Gauss-Seidel converge.

## Correntes

    I1 = (E-VB)/R1; I2 = (E-VC)/R2
    I3 = VC/R3;    I4 = VB/R4
    I5 = (VB-VC)/R5
    I6 = I1+I2

Checagens numéricas: I1-I4-I5=0; I2+I5-I3=0; I3+I4-I6=0; R1*I1+R4*I4=E; R2*I2+R3*I3=E; R1*I1+R5*I5-R2*I2=0.

## Resultados esperados

| Grandeza | Valor |
|---|---:|
| VB | 24,782608696 V |
| VC | 18,260869565 V |
| I1 | 0,260869565 A |
| I2 | 0,097826087 A |
| I3 | 0,152173913 A |
| I4 | 0,206521739 A |
| I5 | 0,054347826 A |
| I6 | 0,358695652 A |

Com os valores-padrão e tolerância 1e-10, em teste local foram observadas 10 iterações e resíduo nodal da ordem de 1e-14 A.

## Observação de modelagem

Foi resolvido o sistema **nodal 2x2** por Gauss-Seidel e recuperadas as seis correntes pela lei de Ohm. Isso é equivalente à modelagem elétrica da figura, mas não significa que seis correntes foram tratadas como seis incógnitas independentes. A abordagem nodal tem convergência garantida pelas resistências positivas.

Para o relatório: apresentar a figura citando a lista do professor, modelo matemático, atualizações iterativas, critérios de parada, resultados e verificação de Kirchhoff. Registrar pelo menos um segundo teste com dados modificados.
