# :detective: :briefcase: Problema Caixeiro Viajante
Implementação do problema do Caixeiro Viajante (TSP) euclidiano, utilizando um algoritmo aproximativo e um algoritmo exato. Desenvolvido junto com @[Yasmin-Camargo](https://github.com/Yasmin-Camargo) para a disciplina de Algoritmos e Estruturas de Dados III

Análise do tempo de execução e custo da solução obtida para:
## :one: Algoritmo exato
O algoritmo consiste em realizar uma busca exaustiva por todas combinações possíveis de nodos (passando por todos os vértices exatamente uma vez e retornando à origem) até encontrar o menor caminho possível 

### --- Arquivo: tsp1_253.txt ---
- Custo Ótimo = 253
- Tempo: 1.646033 s  (0.027434 min)
- Vértices para encontrar a rota mínima: [0, 7, 4, 3, 9, 5, 2, 6, 1, 10, 0]


### --- Arquivo: tsp2_1248.txt ---
- Custo Ótimo = 1248
- Tempo: 0.000038 s  (0.000001 min)
- Vértices para encontrar a rota mínima: [0, 1, 2, 3, 4, 0]


### --- Arquivo: tsp3_1194.txt ---
- Custo Ótimo = 1194
- Tempo: 62327.937148 s  (1038.798952 min)
- Vértices para encontrar a rota mínima: [0, 1, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 0]


### --- Arquivos Restantes ---
- Estimava Tempo: quintilhões de anos


## :two: Algoritmo aproximado: RSL (Rosenkrantz, Stearns e Lewis)
O algoritmo consiste em construir uma árvore geradora mínima do grafo, duplicar as arestas da árvore, obter um ciclo euleriano e transformá-lo em um circuito hamiltoniano. Esse algoritmo é 2-aproximado, ou seja, a solução encontrada é no máximo 2 vezes pior que a solução exata.

OBS.: Para gerar a árvore geradora de custo mínimo utilizou-se o algoritmo de Kruskal e para encontrar o ciclo euleriano utilizou-se o Algoritmo de Fleury


### --- Arquivo: tsp1_253.txt ---
- Custo Ótimo = 270
- Tempo: 0,032 ms  
- Vértices para encontrar a rota: [0, 7, 2, 10, 1, 6, 4, 3, 5, 9, 8, 0]



### --- Arquivo: tsp2_1248.txt ---
- Custo Ótimo = 1272
- Tempo: 0,037 ms  
- Vértices para encontrar a rota: [0, 1, 5, 4, 3, 2, 0]



### --- Arquivo: tsp3_1194.txt ---
- Custo Ótimo = 1529
- Tempo: 0,028 ms  
- Vértices para encontrar a rota: [0, 1, 2, 3, 4, 8, 7, 5, 6, 14, 13, 12, 11, 10, 9, 0]



### --- Arquivo: tsp4_7013.txt ---
- Custo Ótimo = 9038
- Tempo: 1,450 ms  
- Vértices para encontrar a rota: [0, 7, 21, 3, 17, 39, 25, 16, 1, 2, 24, 23, 38, 43, 29, 15, 12, 11, 6, 5, 27, 28, 19, 18, 9, 8, 10, 32, 30, 31, 40, 20, 42, 41, 33, 13, 14, 4, 26, 36, 35, 34, 37, 22, 0]



### --- Arquivo: tsp5_27603.txt ---
- Custo Ótimo = 35019
- Tempo: 0,151 s
- Vértices para encontrar a rota mínima: [0, 1, 5, 4, 3, 6, 2, 8, 7, 11, 9, 10, 12, 13, 16, 17, 18, 14, 21, 22, 20, 28, 27, 25, 19, 15, 24, 26, 23, 0]
