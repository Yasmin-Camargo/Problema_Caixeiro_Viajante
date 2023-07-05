package problemacaxeiroviajante;

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
        }
        else{
            this.vertices = vertices;
            matriz = new int[vertices][vertices];
        }
        
        for (int i = 0; i < this.vertices; i++){ //-1 indica que não possui aresta conectando os vértices
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
        if (verticeA > 0 && verticeA <= vertices && verticeB > 0 && verticeB <= vertices){
            matriz[verticeA - 1][verticeB - 1] = peso;
            matriz[verticeB - 1][verticeA - 1] = peso;
            
            arestas++;
            return true;
        }
        return false;
    }
    
    public int getVertices(){
        return vertices;
    }
    
    public int getArestas(){
        return arestas;
    }
    
    public void setArestas(int arestas){
        this.arestas = arestas;
    }
    
    public int [][]getMatrizAdjacencia(){
        return matriz;
    }
    
    @Override
    public String toString(){
        String mostraGrafo = new String();
        
        for (int i = 0; i < vertices; i++) {
            mostraGrafo += "\t";
            mostraGrafo += i + 1;
        }
        mostraGrafo += "\n";
        for (int i = 0; i < vertices; i++) {
            mostraGrafo += "\n";
            mostraGrafo += i + 1;
            for (int j = 0; j < vertices; j++) {
                mostraGrafo += "\t";
                if (matriz[i][j] == -1){
                    mostraGrafo += ".";
                } else{
                    mostraGrafo += matriz[i][j];
                }
            }
        }
        return mostraGrafo;
    }
}
