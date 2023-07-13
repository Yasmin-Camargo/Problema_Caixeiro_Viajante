/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problemacaixeiroviajante;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Caroline e Yasmin
 * 
 */

public class ProblemaCaixeiroViajante {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        long tempoInicial = System.currentTimeMillis();
        int op;
        
        ProblemaCaixeiroViajante problemaCaixeiroViajante = new ProblemaCaixeiroViajante();
        String caminho_arquivo = "..\\arquivos_teste\\tsp1_253.txt"; 
        
        Grafo grafo = problemaCaixeiroViajante.carregaMatrizDeAdjacencia(caminho_arquivo);
        System.out.println(grafo);
        do {   
            op = 1;
            //op = menu();
            switch (op) {
                case 0: //Encerra programa
                    System.out.println("\n\n Saindo ...\n");
                    break;
                    
                case 1: //Encontra o menor caminho, Algoritmo Aproximativo utilizado:  RSL(Rosenkrantz, Stearns e Lewis )
                    //ETAPA 1
                    //Gera um subconjunto de arestas que forma uma árvore que inclui todos os vértices onde o peso total é minimo (Kruskal)
                    Kruskal kruskal = new Kruskal(grafo);
                    Grafo arvoreGeradoraMinima = kruskal.kruskal();
                    //System.out.println("\n" + arvoreGeradoraMinima);
                    
                    //ETAPA 2
                    //Pega ávore geradora minima e duplica as arestas
                    int [][]matrizDuplicada = problemaCaixeiroViajante.duplicaArestas((kruskal.getMatrizFinalKruskal()), kruskal.getQuantArestasFinal());
                    
                    //ETAPA 3
                    //Encontrar um ciclo euleriano (caminho em um grafo que visita toda aresta exatamente uma vez)
                    CicloEuleriano cicloEuleriano = new CicloEuleriano(matrizDuplicada, arvoreGeradoraMinima);
                    ArrayList<Integer> caminhoEuleriano = cicloEuleriano.encontraCicloEuleriano();
                    
                    //ETAPA 4
                    //Construir um circuito hamiltoniano, com os vértices na ordem do ciclo euleriano, sem repetições
                    problemaCaixeiroViajante.encontraCircuitoHamiltoniano(caminhoEuleriano, grafo.getMatrizAdjacencia());
                    break;
                    
                case 2: //Print da matriz
                    System.out.print("\n\n--- Matriz de Adjacencia Grafo ---\n\n");
                    System.out.println(grafo.toString());
                    break;
              
                default:
                    System.out.println("\n\n DIGITE UM NUMERO VALIDO!!!\n");
                    break;
            }
        } while (op != 1);
        
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("\nTempo: %.3f ms\n", (tempoFinal - tempoInicial) / 1000d);
    }
    
    public static int menu(){
        Scanner entrada = new Scanner(System.in);
        int opMenu;
        System.out.println("\n\n\t===== Implementacao de Grafos =====");
        System.out.println("\t 1) Executar codigo");
        System.out.println("\t 2) Mostrar matriz de Adjacencia");
        System.out.println("\t 0) sair");
        opMenu = entrada.nextInt();
        if (opMenu >= 0 && opMenu <= 2){
            return opMenu;
        }
        return -1;
    }
    
    public Grafo carregaMatrizDeAdjacencia(String caminho_arquivo) throws IOException{
        // Abre o arquivo
        BufferedReader arquivo = null;
        try {
            arquivo = new BufferedReader(new FileReader(caminho_arquivo));   
        } catch (IOException e) {
            System.out.println("Erro ao abrir arquivo");
            System.exit(0);
        }
        
        //Faz leitura da primeira linha para saber o número de vértices
        String linha = arquivo.readLine();
        String[] textoSeparado = linha.split("\\s+"); //Separa o conteúdo da linha (indepedente do tamanho do espaço) 
    
        //Incializa o grafo de acordo com a quantidade de elementos encontrados na primeira linha
        Grafo grafo = new Grafo(textoSeparado.length);
        grafo.setArestas(grafo.getVertices() * grafo.getVertices() - grafo.getVertices());
        
        int [][]matriz = new int[grafo.getVertices()][grafo.getVertices()];
        
        for (int i = 0; linha != null; i++) {
            for (int j = 0; j < grafo.getVertices(); j++) { //Preenche a matriz de adjacencia
                if(i == j){
                    matriz[i][j] = -1;
                } else {
                    matriz[i][j] = Integer.parseInt(textoSeparado[j]);
                }
            }
            linha = arquivo.readLine();
            if (linha != null) {
                textoSeparado = linha.split("\\s+");
            }
        }
        
        //Adiciona matriz de adjacencia no grafo
        grafo.addMatrizAdjacencia(matriz);        
        return grafo;
    }
    
    public int[][] duplicaArestas(int matriz[][], int tamanho){
        int matrizDuplicada[][] = new int[tamanho * 2][3];
        int indiceDuplicada = -1;
        
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][2] == 1) {
                indiceDuplicada++;
                matrizDuplicada[indiceDuplicada][0] = matriz[i][0];
                matrizDuplicada[indiceDuplicada][1] = matriz[i][1];
                indiceDuplicada++;
                matrizDuplicada[indiceDuplicada][0] = matriz[i][1];
                matrizDuplicada[indiceDuplicada][1] = matriz[i][0];
            }
        }
        return matrizDuplicada;
    }
    
    public void encontraCircuitoHamiltoniano(ArrayList<Integer> caminhoEuleriano, int matrizAdjacencia[][]){ //Encontra circuito haltominiano tirando os vértices repetidos do caminho euleriano
        ArrayList<Integer> circuitoHamiltoniano = new ArrayList();
        int custoTotal = 0, cont = 0;
      
        circuitoHamiltoniano.add(caminhoEuleriano.get(0)); //primeiro veritice
        for (int i = 0; i < caminhoEuleriano.size(); i++) {
            if (verificaVisita(caminhoEuleriano.get(i), circuitoHamiltoniano)) {
                circuitoHamiltoniano.add(caminhoEuleriano.get(i));
                if ((cont+1) < circuitoHamiltoniano.size()) {
                    custoTotal += matrizAdjacencia[circuitoHamiltoniano.get(cont)][circuitoHamiltoniano.get(cont+1)];
                }
                cont++;
            }
        }

        circuitoHamiltoniano.add(circuitoHamiltoniano.get(0));
        custoTotal += matrizAdjacencia[circuitoHamiltoniano.get(cont)][circuitoHamiltoniano.get(cont+1)];
        System.out.println("\n\nCIRCUITO HAMILTONIANO: " + circuitoHamiltoniano);
        System.out.println("\nCUSTO TOTAL CAIXEIRO VIAJANTE: " + custoTotal);
        
        
    }
    
    private boolean verificaVisita(int vertice, ArrayList<Integer> circuitoHamiltoniano){ //Verifica se o vertice já foi percorrido 
        for (int i = 0; i < circuitoHamiltoniano.size(); i++) {
            if (circuitoHamiltoniano.get(i) == vertice) { 
                return false;
            }
        }
        return true;
    }
}
