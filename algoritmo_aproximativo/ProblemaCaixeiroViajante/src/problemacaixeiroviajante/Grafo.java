package problemacaixeiroviajante;

import java.util.Stack;

/**
 *
 * @author Caroline e yasmin
 */

public class Grafo {
    private final int vertices;
    private int arestas;
    private int [][]matriz;    

    //Inicialização das variáveis 
    public Grafo(int vertices) {
        arestas = 0;
        if (vertices < 0){ //Quantidade de vértices deve ser no mininimo 1
            this.vertices = 1;
            matriz = new int[1][1];
            System.out.println("ERRO: Deve existir pelo meno 1 vertice");
        }
        else{
            this.vertices = vertices;
            matriz = new int[vertices][vertices];
        }
        
        for (int i = 0; i < this.vertices; i++){ // -1 indica que não possui aresta conectando os vértices
            for (int j = 0; j < this.vertices; j++){
                this.matriz[i][j] = -1;
            }
        }
    }
    
    public void addMatrizAdjacencia(int [][]matriz){
        this.matriz = matriz;
    }
    
    //Adiciona arestas no grafo
    public boolean addAresta(int verticeA, int verticeB, int peso){
        if (verticeA >= 0 && verticeA < vertices && verticeB >= 0 && verticeB < vertices){
            matriz[verticeA][verticeB] = peso;
            matriz[verticeB][verticeA] = peso;
            
            arestas++;
            return true;
        }
        System.out.println("ERRO: Aresta fora dos limites");
        return false;
    }
    
    //Remove arestas no grafo
    public boolean removeAresta(int verticeA, int verticeB){
        if (verticeA >= 0 && verticeA < vertices && verticeB >= 0 && verticeB < vertices){
            matriz[verticeA][verticeB] = -1;
            matriz[verticeB][verticeA] = -1;

            arestas--;
            return true;
        }
        System.out.println("ERRO: Aresta fora dos limites");
        return false;
    }
        
    public boolean[] buscaEmProfundidade(int verticeInicial) {
        boolean[] verticesVisitados = null; //marca os vértices já visitados
        
        if (verticeInicial >= 0) {
            verticesVisitados = new boolean[vertices];
            Stack<Integer> pilha = new Stack<>(); //busca em profundidade usando uma pilha
            pilha.push(verticeInicial); //Insere o primeiro nó na pilha e marca como visitado.
            verticesVisitados[verticeInicial] = true; 

            while (!pilha.isEmpty()) { //enquanto houver nós na pilha, desempilha o nó e empilha todos os seus vizinhos que não tiverem sido visitados, marcando-os como visitados.
                int verticeTopo = pilha.pop(); //desempilha o nó             
                for (int i = 0; i < vertices; i++) {  //Acha os vizinhos 
                    if (!verticesVisitados[i] && matriz[verticeTopo][i] != -1) { //Possui uma aresta válida e o vértice não foi visitado
                        int proximoVizinho = i;
                        verticesVisitados[proximoVizinho] = true; //marca como visitado
                        pilha.push(proximoVizinho);
                    }
                }
            }
        } else {
            System.out.println("ERRO: vertice negativo");
        }
        return verticesVisitados;
    }
    
    public Grafo copiaGrafo(){ //Retorna um grafo identico ao atual, mas sem referência
        Grafo g = new Grafo(vertices);
        g.arestas = getArestas();
        
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                g.matriz[i][j] = matriz[i][j];
            }
        }
        return g;
    }
    
    public int getVertices(){
        return vertices;
    }
    
    public int getArestas(){
        return arestas;
    }
    
    public void setArestas(int arestas){
        if (arestas >= 0) {
            this.arestas = arestas;
        } else {
            System.out.println("ERRO: Nao eh permitido arestas negativas");
        }
        
    }
    
    public int [][]getMatrizAdjacencia(){
        return matriz;
    }
    
    @Override
    public String toString(){
        String mostraGrafo = new String();
        mostraGrafo = "\n";
        
        for (int i = 0; i < vertices; i++) {
            mostraGrafo += "\t";
            mostraGrafo += i;
        }
        mostraGrafo += "\n";
        for (int i = 0; i < vertices; i++) {
            mostraGrafo += "\n";
            mostraGrafo += i;
            for (int j = 0; j < vertices; j++) {
                mostraGrafo += "\t";
                if (matriz[i][j] == -1){
                    mostraGrafo += ".";
                } else {
                    mostraGrafo += matriz[i][j];
                }
            }
        }
        return mostraGrafo;
    }
}
