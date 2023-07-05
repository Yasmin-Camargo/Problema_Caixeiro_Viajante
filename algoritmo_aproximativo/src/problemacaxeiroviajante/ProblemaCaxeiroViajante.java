/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package problemacaxeiroviajante;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Caroline e Yasmin
 * 
 */

public class ProblemaCaxeiroViajante {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        int op;
        String caminho_arquivo = "..\\ProblemaCacheiroViajante\\src\\problemacaxeiroviajante\\tsp5_27603.txt"; 
        
        Grafo grafo = carregaMatrizDeAdjacencia(caminho_arquivo);
        
        do {                
            op = menu();
            switch (op) {
                case 0: //Encerra programa
                    System.out.println("\n\n Saindo ...\n");
                    break;
                    
                case 1: //Encontra o menor caminho
                    Kruskal funcaoKruskal = new Kruskal(grafo);
                    funcaoKruskal.kruskal();
                    break;
                    
                case 2: //Print da matriz
                    System.out.print("\n\n--- Matriz de Adjacencia Grafo ---\n\n");
                    System.out.println(grafo.toString());
                    break;
              
                default:
                    System.out.println("\n\n DIGITE UM NUMERO VALIDO!!!\n");
                    break;
            }
        } while (op != 0);
    }
    
    public static int menu(){
        Scanner entrada = new Scanner(System.in);
        int opMenu;
        System.out.println("\n\n\t===== Implementacao de Grafos =====");
        System.out.println("\t 1) Executar código");
        System.out.println("\t 2) Mostrar matriz de Adjacencia");
        System.out.println("\t 0) sair");
        opMenu = entrada.nextInt();
        if (opMenu >= 0 && opMenu <= 2){
            return opMenu;
        }
        return -1;
    }
    
    public static Grafo carregaMatrizDeAdjacencia(String caminho_arquivo) throws IOException{
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
        String[] textoSeparado = linha.split("\\p{Zs}+"); //Separa o conteúdo da linha (indepedente do tamanho do espaço) 
        
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
                textoSeparado = linha.split("\\p{Zs}+");
            }
        }
        
        //Adiciona matriz de adjacencia no grafo
        grafo.addMatrizAdjacencia(matriz);
        //System.out.println(grafo);
        
        return grafo;
    }
}
