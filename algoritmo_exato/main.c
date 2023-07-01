// Desenvolvido por: Caroline Souza Camargo e Yasmin Souza Camargo

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <time.h>

// Escopo das funções
void carregaMatrizAdjacencia(char nomeArquivo[], int numVertices, int matriz[numVertices][numVertices]);
int contVertices(char nomeArquivo[]);
void todasCombinacoes(int numVertices, int matriz[numVertices][numVertices], int todosVertices[numVertices], int verticeInicial);
void trocaPosicaoElementos(int * elemento1, int * elemento2);
int calculaCusto(int numVertices, int todosVertices[numVertices], int matriz[numVertices][numVertices]);

int rotaMinima = INT_MAX;  // Valor máximo de int
int vetorCustoMinimo[100];

int main() {
    char nomeArquivo[] = "../arquivos_teste/tsp1_253.txt"; // Caminho para o arquivo com os dados da matriz de adjacência de um grafo
    printf("\n\n\n--- Arquivo: %s ---\n", nomeArquivo);
    int numVertices = contVertices(nomeArquivo); // Chama função para contar os número de vértices do grafo
    int matrizAdjacencia[numVertices][numVertices]; 

    clock_t inicio = clock(); // Tempo inicial
    carregaMatrizAdjacencia(nomeArquivo, numVertices, matrizAdjacencia); // Chama função para criar a matriz
    
    int todosVertices[numVertices];
    // Coloca todos vértices no vetor de vértices
    for (int i=0; i < numVertices; i++){
        todosVertices[i] = i;
    }

    todasCombinacoes(numVertices, matrizAdjacencia, todosVertices, 0);
    printf("Custo Otimo = %d\n", rotaMinima);

    printf("Vertices para encontra rota minima:  ");
    for (int i = 0; i < numVertices - 1 ; i++){
        printf("(%d), ", vetorCustoMinimo[i]);
    }
    printf("(%d) \n\n", vetorCustoMinimo[numVertices]);
    
    clock_t fim = clock(); // Tempo final
    double tempoGasto = (double)(fim - inicio) / CLOCKS_PER_SEC;
    printf("Tempo: %f s  (%f min)\n", tempoGasto, tempoGasto / 60);

    /* 
    // Mostra matriz para o usuário
    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            printf("\t%d ", matrizAdjacencia[i][j]);
        }
        printf("\n");
    }*/

    printf("----------------------------------------\n\n");

    return 0;
}

//  Funções para o Grafo:
// ------------------------------------------------------------------------------------------------------

void carregaMatrizAdjacencia(char nomeArquivo[], int numVertices, int matrizAdjacencia[numVertices][numVertices]) {
    FILE *arquivo = NULL;
    arquivo = fopen(nomeArquivo, "r"); // Abertura do arquivo
    
    printf("\nNumero de vertices: %d", numVertices);
    printf("\nNumero de arestas: %d \n\n",  (numVertices * numVertices) - numVertices);

    // Verifica se o arquivo foi aberto corretamente
    if (arquivo == NULL) {
        printf("\nErro ao abrir o arquivo.\n");
        return;
    }
    
    // Percorre arquivo colocando dados na matriz
    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            char c;
            do {
                c = fgetc(arquivo);         // Ignora os espaços
            } while (c == ' ');

            if (c != ' ' && c != '\n') {    // Se não é um espaço em branco e não é uma quebra de linha:
                ungetc(c, arquivo);         // devolve o último caractere (permite que ele seja lido novamente)
            }
            fscanf(arquivo, "%d", &matrizAdjacencia[i][j]); // Lê um valor inteiro do arquivo e armazena na matriz
        }
    }
    fclose(arquivo);
}

// Conta quantos vértices existem na matriz de adjacência
int contVertices(char nomeArquivo[]) { 
    FILE *arquivo = NULL;
    int numVertices = 0;

    // Abre o arquivo e conta o número de linhas
    arquivo = fopen(nomeArquivo, "r");
    char temp[10000];
    while (fgets (temp, 10000, arquivo) != NULL) {
        numVertices++;  
    }
    fclose(arquivo);
    return numVertices;
}


// ----- Funções para o problema do Caixeiro Viajante ------------------------------------------------

// Função recursiva para gerar todas combinações possíveis do vetor vértices
void todasCombinacoes(int numVertices, int matriz[numVertices][numVertices], int todosVertices[numVertices], int verticeInicial){
    
    if (verticeInicial == numVertices - 1){
        /*
        // Mostra para o usuário todas as combinações testadas pelo algoritmo
        for (int i = 0; i < numVertices; i++) {
            printf("%d ", todosVertices[i]);
        }
        printf("\n"); */

        int custoCaminhoAtual = calculaCusto(numVertices, todosVertices, matriz); // Chama função para calcular a distância da rota que foi gerada
        if (custoCaminhoAtual < rotaMinima){ // Verifica se a distância atual não foi menor que a distância total
            rotaMinima = custoCaminhoAtual;
            
            for (int i = 0; i < numVertices; i++) {
                vetorCustoMinimo[i] = todosVertices[i];
            }
            vetorCustoMinimo[numVertices] = todosVertices[0];
            
        }     
    } 
    else {
        for (int i = verticeInicial; i < numVertices; i++){ // Troca elementos para ter todas combinações possíveis
            trocaPosicaoElementos(&todosVertices[i], &todosVertices[verticeInicial]);
            todasCombinacoes(numVertices, matriz, todosVertices, verticeInicial + 1); // Recursão
            trocaPosicaoElementos(&todosVertices[i], &todosVertices[verticeInicial]);
        }
    }
}

// Função auxiliar para trocar elementos (swap)
void trocaPosicaoElementos(int * elemento1, int * elemento2){   
    int temp = *elemento1;
    *elemento1 = *elemento2;
    *elemento2 = temp;
}

// Calcula o custo do caminho passando pelos vértices dados
int calculaCusto(int numVertices, int todosVertices[numVertices], int matriz[numVertices][numVertices]){
    int custoCaminho = 0;
    for (int i = 0; i < numVertices - 1 ; i++){
        custoCaminho = custoCaminho + matriz[todosVertices[i]][todosVertices[i + 1]];
    }
    custoCaminho = custoCaminho + matriz[todosVertices[0]][todosVertices[numVertices - 1]]; // Custo para voltar para o vértica inicial
    //printf("-> %d\n", custoCaminho);
    
    return custoCaminho;
}