#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void carregaMatrizAdjacencia(char nomeArquivo[], int numVertices, int matriz[numVertices][numVertices]);
int contVertices(char nomeArquivo[]);
void caixeiroViajanteExato(int numVertices, int matriz[numVertices][numVertices]);
void todasCombinacoes(int numVertices, int matriz[numVertices][numVertices], int todosVertices[numVertices], int verticeInicial);
void trocaPosicaoElementos(int * elemento1, int * elemento2);
int calculaCusto(int numVertices, int todosVertices[numVertices], int matriz[numVertices][numVertices]);

int rotaMinima = 9999999;

int main() {
    char nomeArquivo[] = "tsp1_253.txt";
    int numVertices = contVertices(nomeArquivo);
    int matrizAdjacencia[numVertices][numVertices];

    clock_t inicio = clock();  // Tempo inicial
    carregaMatrizAdjacencia(nomeArquivo, numVertices, matrizAdjacencia);
    
    caixeiroViajanteExato(numVertices, matrizAdjacencia);
    
    clock_t fim = clock();  // Tempo final
    double tempoGasto = (double)(fim - inicio) / CLOCKS_PER_SEC;
    printf("Tempo: %f s  (%f min)\n", tempoGasto, tempoGasto / 60);

    /*
    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            printf("\t%d ", matrizAdjacencia[i][j]);
        }
        printf("\n");
    }*/

    return 0;
}

void carregaMatrizAdjacencia(char nomeArquivo[], int numVertices, int matrizAdjacencia[numVertices][numVertices]) {
    FILE *arquivo = NULL;
    arquivo = fopen(nomeArquivo, "r");
    
    printf("\nNumero de vertices: %d", numVertices);
    printf("\nNumero de arestas: %d \n\n",  (numVertices * numVertices) - numVertices);
    printf("----------------------------------------\n\n");

    // Verifica se o arquivo foi aberto corretamente
    if (arquivo == NULL) {
        printf("\nErro ao abrir o arquivo.\n");
        return;
    }

    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            char c;
            do {
                c = fgetc(arquivo); //Ignora os espaços
            } while (c == ' ');

            if (c != ' ' && c != '\n') {
                ungetc(c, arquivo); //Devolve o último caractere
            }
            fscanf(arquivo, "%d", &matrizAdjacencia[i][j]);
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

void caixeiroViajanteExato(int numVertices, int matriz[numVertices][numVertices]){
    int todosVertices[numVertices];

    // coloca todos vértices no vetor de vértices
    for (int i=0; i < numVertices; i++){
        todosVertices[i] = i;
    }

    todasCombinacoes(numVertices, matriz, todosVertices, 0);

    printf("Custo Otimo = %d\n\n", rotaMinima);
}


// Função para gerar todas combinações do vetor vértices
void todasCombinacoes(int numVertices, int matriz[numVertices][numVertices], int todosVertices[numVertices], int verticeInicial){
    if (verticeInicial == numVertices - 1){
        
        /*
        for (int i = 0; i < numVertices; i++) {
            printf("%d ", todosVertices[i]);
        }
        printf("\n"); */

        int custoCaminhoAtual = calculaCusto(numVertices, todosVertices, matriz);
        if (custoCaminhoAtual < rotaMinima){
            rotaMinima = custoCaminhoAtual;
        }
        
    } else{
        for (int i = verticeInicial; i < numVertices; i++){
            trocaPosicaoElementos(&todosVertices[i], &todosVertices[verticeInicial]);
            todasCombinacoes(numVertices, matriz, todosVertices, verticeInicial + 1);
            trocaPosicaoElementos(&todosVertices[i], &todosVertices[verticeInicial]);
        }
    }
}

void trocaPosicaoElementos(int * elemento1, int * elemento2){
    int temp = *elemento1;
    *elemento1 = *elemento2;
    *elemento2 = temp;
}

int calculaCusto(int numVertices, int todosVertices[numVertices], int matriz[numVertices][numVertices]){
    int custoCaminho = 0;
    for (int i = 0; i < numVertices - 1 ; i++){
        custoCaminho = custoCaminho + matriz[todosVertices[i]][todosVertices[i + 1]];
    }
    custoCaminho = custoCaminho + matriz[todosVertices[0]][todosVertices[numVertices - 1]];

    //printf("-> %d\n", custoCaminho);
    
    return custoCaminho;
}