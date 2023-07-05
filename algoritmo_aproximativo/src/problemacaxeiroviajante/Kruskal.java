package problemacaxeiroviajante;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author Caroline e Yasmin
 */

public class Kruskal {
    private Grafo grafo;
    private int [][]tabelaDeControleKruskal;
    private static final int MAX = 2147483647;
    
    public Kruskal(Grafo grafo){
        this.grafo = grafo;
    }
    
    public boolean kruskal(){
        tabelaDeControleKruskal = new int[grafo.getArestas()][3];
        
        //Inicializa tabela de controle 
        for (int i = 0; i < grafo.getArestas(); i++){ //[0]origem [1]destino [2]flag Vértice Seguro
            tabelaDeControleKruskal[i][0] = 0;
            tabelaDeControleKruskal[i][1] = 0;
            tabelaDeControleKruskal[i][2] = 0;
        }
       
        ordena(grafo.getArestas(), tabelaDeControleKruskal);
        
        
        //busca os melhores caminhos 
        //----------------------------------------------------------
        
        ArrayList<TreeSet<Integer>> grupoNumeros = new ArrayList<TreeSet<Integer>>(); //permite fazer operações de conjuntos
        
        int []areaSegura = new int[grafo.getArestas() + 1];
        
        //Inicializa vetor 
        for (int i = 0; i < grafo.getArestas() + 1; i++){ //[0]origem [1]destino [2]flag Vértice Seguro
            areaSegura[i] = 0;
        }
        
        // Para cada par de vértice que foi ordenado pelo seu peso, coloca ou não na área segura
        for (int i = 0; i < grafo.getArestas(); i++){
            int indiceVertA = -1, indiceVertB = -1; //código -1 para saber que nenhum dos vértices está em um conjunto ainda
            int flag = 0;   // flag para saber quando não entra na área segura
            
            for (int j = 0; j < grupoNumeros.size(); j++){
               if (grupoNumeros.get(j).contains(tabelaDeControleKruskal[i][0]) == true && grupoNumeros.get(j).contains(tabelaDeControleKruskal[i][1]) == true){
                   flag = 1;            //dois vértices já estão no conjunto
                   break;
               } else if (grupoNumeros.get(j).contains(tabelaDeControleKruskal[i][0]) == true){ //
                   indiceVertA = j;     //vértice origem estava no conjuto mas destino não
               } else if (grupoNumeros.get(j).contains(tabelaDeControleKruskal[i][1]) == true){
                   indiceVertB = j;     //vértice destino estava no conjuto mas origem não
               } 
            }
            
            if(flag != 1) { 
                if (indiceVertA == -1 && indiceVertB == -1){ 
                    grupoNumeros.add(new TreeSet<Integer>());   //cria um novo conjunto
                    grupoNumeros.get(grupoNumeros.size() - 1).add(tabelaDeControleKruskal[i][0]); //adiciona novos vértices nos conjuntos
                    grupoNumeros.get(grupoNumeros.size() - 1).add(tabelaDeControleKruskal[i][1]);
                } else if(indiceVertA == -1){
                    grupoNumeros.get(indiceVertB).add(tabelaDeControleKruskal[i][0]); 
                    grupoNumeros.get(indiceVertB).add(tabelaDeControleKruskal[i][1]);
                } else if(indiceVertB == -1){
                    grupoNumeros.get(indiceVertA).add(tabelaDeControleKruskal[i][0]);
                    grupoNumeros.get(indiceVertA).add(tabelaDeControleKruskal[i][1]);  
                } else {
                    grupoNumeros.get(indiceVertB).addAll(grupoNumeros.get(indiceVertA));    // união dos dois conjuntos
                    grupoNumeros.get(indiceVertA).removeAll(grupoNumeros.get(indiceVertA)); // exclui conjunto anterior
                }
                System.out.println(grupoNumeros); 
                tabelaDeControleKruskal[i][2] = 1;      // ativa o caminho
            }
        }

        mostraKruskal();
        return true;
    }
    
    
    private void ordena(int totalCaminhos, int [][]tabelaDeControleKruskal){
        int ValorMenor = MAX, verticeMenorA = 0, verticeMenorB = 0, cont = 0;
        //Ordena vértices
        while(cont < totalCaminhos){
            for (int i = 0; i < this.grafo.getVertices(); i++){ 
                for (int j = 0; j < this.grafo.getVertices(); j++){
                   if (grafo.getMatrizAdjacencia()[i][j] < ValorMenor && verificaLista(grafo.getMatrizAdjacencia()[i][j], i,j,tabelaDeControleKruskal, totalCaminhos) == false && grafo.getMatrizAdjacencia()[i][j] != -1){
                       ValorMenor = grafo.getMatrizAdjacencia()[i][j];
                       verticeMenorA = i;
                       verticeMenorB = j;
                   }
                }
            }
            tabelaDeControleKruskal[cont][0] = verticeMenorA;
            tabelaDeControleKruskal[cont][1] = verticeMenorB;
            cont++;
            //System.out.println("Valor Menor: " + ValorMenor);
            ValorMenor = MAX;
        }
    }
    
    private boolean verificaLista(int valor, int linha, int coluna, int [][] lista, int totalCaminhos){
        for (int i = 0; i < totalCaminhos; i++){
            if (valor == this.grafo.getMatrizAdjacencia()[lista[i][0]][lista[i][1]] && ((linha == lista[i][0] && coluna == lista[i][1]) || (linha == lista[i][1] && coluna == lista[i][0]))){
                return true;
            }
        }
        return false;
    }
    
    private boolean possuiElemento(int []areaSegura, int tam, int valor){
        for (int i = 0; i < tam; i++){
            if (valor == areaSegura[i]){
                return true;
            }
        }
        return false;
    }
    
    private void mostraKruskal(){  //[0]origem [1]destino [2]flag Vértice Seguro
        int custo =0;
        System.out.println("\n--- Arvore Geradora Minima resultante ---");
        System.out.println("\nAresta \tPeso");
        for (int i = 0; i < grafo.getArestas(); i++) {
            if (tabelaDeControleKruskal[i][2] == 1) {
                int vertA = tabelaDeControleKruskal[i][0] + 1, vertB = tabelaDeControleKruskal[i][1] + 1;
                custo = custo + grafo.getMatrizAdjacencia()[vertA-1][vertB-1];
                System.out.println("(" +vertA+ "," +vertB+ ") \t" +grafo.getMatrizAdjacencia()[vertA-1][vertB-1]);
            }
        }
        System.out.println("\nCuto minimo para passar em todas arestas:" + custo);
    }
}
