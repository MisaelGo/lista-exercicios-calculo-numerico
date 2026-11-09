# Atividade Complementar de Cálculo Numérico — Equipe 12

Projeto Java 17 + Maven para quatro exercícios sorteados: **01.2 (métodos diretos), 02.1 (Gauss-Seidel), 03.1 (Lei de Moore) e 04.3 (integração numérica)**.

## Integrantes

| Integrante | Questões | Estado inicial |
|---|---|---|
| Misael Gomes Bonfim Rodrigues | 02.1 e integração | **Implementado e testado localmente** |
| Allyson André Almeida de Castro Paes Landi | 01.2 e Trapézio (04.3) | Pendente |
| Carlos André Alves Torres Filho | 03.1 e Simpson (04.3) | Pendente |

**Aviso:** somente a questão 02.1 tem solução implementada; as demais aparecem no menu como "em desenvolvimento", com arquivos e orientações para os autores. Não considerar o trabalho todo concluído.

## Execução

Instalar JDK 17+ e Apache Maven 3.8+. Na pasta do repositório, executar:

    mvn clean test
    mvn package
    java -jar target/lista-exercicios-calculo-numerico-1.0.0-SNAPSHOT.jar

A opção **2** executa a Ponte de Wheatstone. Pressionar ENTER em cada campo aceita o valor do enunciado. Podem-se inserir novos valores de tensão e resistências, repetir cálculos pelo menu ou selecionar **0** para sair.

## Organização

- src/main/java/.../Main.java — entrada da aplicação.
- console/ — menu e validação de entradas.
- metodos/GaussSeidel.java — algoritmo iterativo geral.
- circuitos/PonteWheatstone.java — modelagem elétrica e cálculo de correntes.
- questoes/ — telas das quatro questões; 01, 03 e 04 aguardam implementação.
- integracao/ — classes previstas para Trapézio e Simpson.
- src/test/ — testes JUnit 5 da questão 02.1.
- docs/TAREFAS_EQUIPE.md — divisão detalhada das entregas.
- docs/QUESTAO_02_1.md — modelagem e resultados de Misael.
- docs/RELATORIO_MODELO.md — orientações de Introdução, Metodologia, Resultados, Conclusão e Referências.
- CONTRIBUTING.md — como colaborar e abrir Pull Requests.

## Fluxo com branches

| Branch | Responsável | Entrega |
|---|---|---|
| feat/allyson-questao-01-2 | Allyson | Sistema 3x3 por método direto |
| feat/allyson-trapezio | Allyson | Trapézio repetido na questão 04.3 |
| feat/carlos-questao-03-1 | Carlos | Regressão da Lei de Moore |
| feat/carlos-simpson | Carlos | Simpson 1/3 e 3/8 na questão 04.3 |

Trabalhar nas branches; abrir PRs para main e executar os testes antes de integrar.

**Datas informadas na lista:** apresentação da equipe em 17/11/2026; envio do relatório e código em 20/11/2026. Todos devem comparecer à apresentação. Confirmar instruções e horário na plataforma do professor.
