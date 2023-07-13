/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package problemacaixeiroviajante;

import java.util.ArrayList;

/**
 *
 * @author @author Caroline e yasmin
 */
public class CicloEuleriano {
    private int [][]parArestas;
    private Grafo arvoreGeradoraMinima; 
    private ArrayList<Integer> listaVerticesVisitados; //Vai gravar o caminho necessário para percorrer o ciclo euleriano

    public CicloEuleriano(int [][]parArestas, Grafo arvoreGeradoraMinima) {
        this.parArestas = parArestas;
        this.arvoreGeradoraMinima = arvoreGeradoraMinima;
        listaVerticesVisitados =  new ArrayList();
    }
    
    public ArrayList encontraCicloEuleriano(){
        //Faz uma cópia da arvore geradora minima 
        Grafo grafoAux = arvoreGeradoraMinima.copiaGrafo();
        
        int vertAtual = 0;
        while (!verificaTodasArestasVisitadas()) { //Todas arestas precisam ter sido visitadas
            listaVerticesVisitados.add(vertAtual);
            
            int proxVertice = proxVertice(vertAtual, grafoAux);
            if (proxVertice == -1) {
                System.out.println("ERRO: VERTICE NAO POSSUI VIZINHOS");
                System.exit(1);
            }
            
            if (!verificaVisita(vertAtual, proxVertice) && !verificaVisita(proxVertice, vertAtual)) { //Se já foi visitada as duas arestas pode remover
                grafoAux.removeAresta(vertAtual, proxVertice); 
            }
            marcaVisita(vertAtual, proxVertice);
            vertAtual = proxVertice;
        }
        listaVerticesVisitados.add(vertAtual);
        
        System.out.println("\n\nCICLO EULERIANO: " + listaVerticesVisitados);
        
        return listaVerticesVisitados;
    }
    
    private boolean verificaTodasArestasVisitadas(){ //Verifica se todas as arestas do grafo já foram visitadas
        for (int i = 0; i < parArestas.length; i++) {
            if (parArestas[i][2] == 0) { 
                return false;
            }
        }
        return true;
    }
    
    private int proxVertice(int vertAtual, Grafo g){  //Encontra o próximo vértice adjacente que não foi visitado e que não é ponte
        if (vertAtual < 0) {
            System.out.println("ERRO: Vertice negativo");
        }
        
        Grafo grafoTemp = g.copiaGrafo();
        
        for (int i = 0; i < grafoTemp.getVertices(); i++) {
            if(grafoTemp.getMatrizAdjacencia()[vertAtual][i] != -1 && verificaVisita(vertAtual, i)){
                marcaVisita(vertAtual, i);
                //A partir daqui é verificado se a remoção da aresta que faz conexão com esses 2 vértices deixa o grafo desconexo
                if (!verificaVisita(vertAtual, i) && !verificaVisita(i, vertAtual)) { //Lembrando: o grafo possui arestas duplicadas e se já foi visitada as duas arestas pode remover da matriz de Adjacencia
                    grafoTemp.removeAresta(vertAtual, i); 
                    grafoTemp.removeAresta(i, vertAtual);
                }
                
                if (!verificaPonte(grafoTemp, vertAtual)) {  //Se a aresta não é uma ponte pode retornar o vértice
                    return i;
                } 
                
                grafoTemp.addAresta(vertAtual, i, 10); //coloca aresta removida devolta no grafo
                RetiraVisita(vertAtual, i); //desmarca visita
            }
        }
        
        for (int i = 0; i < grafoTemp.getVertices(); i++) { //Só deve ser escolhida a aresta que é uma ponte caso não haja outra opção
            if(grafoTemp.getMatrizAdjacencia()[vertAtual][i] != -1 && verificaVisita(vertAtual, i)){
                return i;
            }
        }
        return -1;
    }
    
    private boolean verificaVisita(int a, int b){ //Verifica se uma aresta especifica do grafo não foi visitada ainda
        for (int i = 0; i < parArestas.length; i++) {
            if (parArestas[i][0] == a && parArestas[i][1] == b && parArestas[i][2] == 1) {
                return false;
            } else if (parArestas[i][0] == a && parArestas[i][1] == b && parArestas[i][2] == 0) {
                return true;
            }
        }
        return false;
    }
    
    private void marcaVisita(int a, int b){ //Marca como visitado uma aresta especifica do grafo 
        for (int i = 0; i < parArestas.length; i++) {
            if (parArestas[i][0] == a && parArestas[i][1] == b) {
                parArestas[i][2] = 1;
                break;
            }
        }
        
    }
    
    private void RetiraVisita(int a, int b){ //Retira como visitado uma aresta especifica do grafo 
        for (int i = 0; i < parArestas.length; i++) {
            if (parArestas[i][0] == a && parArestas[i][1] == b) {
                parArestas[i][2] = 0;
                break;
            }
        }
    }
    
    private boolean verificaPonte(Grafo g, int vertice){ //Verifica se a aresta era uma ponte (o grafo ficou desconexo)
        if (vertice < 0 || vertice >= g.getVertices()) {
            System.out.println("ERRO: O vertice passou os limites");
        }
        
        boolean[] verticesVisitados = g.buscaEmProfundidade(vertice);
        if (verticesVisitados == null) {
            System.out.println("ERRO: Erro vetor retornado eh nulo");
            return true;
        }
        
        for (int i = 0; i < verticesVisitados.length; i++) {
            if (!verticesVisitados[i]) { //A aresta é uma ponte
                return true;
            }
        }
        return false; //A aresta não é uma ponte
    }
}
