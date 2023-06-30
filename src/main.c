#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void carregamatrizAdjacencia(char nomeArquivo[], int numVertices, int matriz[numVertices][numVertices]);
int contVertices(char nomeArquivo[]);

int main(int argc, char const *argv[]) {
    char nomeArquivo[] = "tsp1_253.txt";
    int numVertices = contVertices(nomeArquivo);
    int matrizAdjacencia[numVertices][numVertices];

    clock_t inicio = clock();  // Tempo inicial
    carregamatrizAdjacencia(nomeArquivo, numVertices, matrizAdjacencia);
    
    //Inserir código aqui
    
    clock_t fim = clock();  // Tempo final
    double tempoGasto = (double)(fim - inicio) / CLOCKS_PER_SEC;
    printf("%f\n", tempoGasto);

    for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
            printf("\t%d ", matrizAdjacencia[i][j]);
        }
        printf("\n");
    } 
    return 0;
}

void carregamatrizAdjacencia(char nomeArquivo[], int numVertices, int matrizAdjacencia[numVertices][numVertices]) {
    // Abre o arquivo em modo de leitura
    FILE *arquivo = NULL;
    arquivo = fopen(nomeArquivo, "r");
    
    // Verifica se o arquivo foi aberto corretamente
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo.\n");
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

    printf("%d", numVertices);

    // Fecha o arquivo
    fclose(arquivo);
}

// Conta quantos vértices existem na matriz de adjacência
int contVertices(char nomeArquivo[]) { 
    FILE *arquivo = NULL;
    int numVertices = 1;

    // Abre o arquivo em modo de leitura
    arquivo = fopen(nomeArquivo, "r");
    char temp;
    while ((temp = fgetc(arquivo)) != '\n') {
        if (temp == ' ') {
            numVertices++;
             while ((temp = fgetc(arquivo)) == ' '){
                //Lê próximos espacos e não contabiliza
             }
        }
    }
    return numVertices;
}