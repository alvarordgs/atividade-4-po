/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author hugo
 */
public class Grafo {

    private ArrayList<Vertice> vertices;

    public Grafo(int numVertices) {
        vertices = new ArrayList<Vertice>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            vertices.add(new Vertice("v" + Integer.toString(i)));
        }
    }

    public void addAresta(int origem, int dest, int peso) {
        Vertice s = vertices.get(origem);
        Aresta new_edge = new Aresta(vertices.get(dest), peso);
        s.adjacentes.add(new_edge);
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public Vertice getVertice(int vert) {
        return vertices.get(vert);
    }
    
    public String toString() {//
        String texto = "";//

        for (Vertice v : getVertices()) {//
            texto += v.nome + " ->";//
            for (Aresta a : v.adjacentes) {//
                texto += a.fim.nome + "|" + a.peso + ", ";//
            }
            texto += "\n";///
        }

        return texto;//

    }
    
    public String gerarStringDijkstra(){
        String texto = "";
         
        for (Vertice v : getVertices()) {
            texto += "Vertice - " + v + " , Dist - " + v.menDistancia + " , Caminho - ";
            for (Vertice caminhoVertice : v.caminho) {
                texto += caminhoVertice + " ";
            }
            
            texto += "" + v;
            texto += "\n";
        }
        return texto;
    }
    
    public void calcular(Vertice origem) {
        // Algo:
        // 1. Take the unvisited node with minimum peso.
        // 2. Visit all its adjacentes.
        // 3. Update the distances for all the adjacentes (In the Priority Queue).
        // Repeat the process till all the connected nodes are visited.

        origem.menDistancia = 0;
        PriorityQueue<Vertice> fila = new PriorityQueue<Vertice>();
        fila.add(origem);

        while (!fila.isEmpty()) {

            Vertice u = fila.poll();

            for (Aresta adjacente : u.adjacentes) {
                Double novaDist = u.menDistancia + adjacente.peso;

                if (adjacente.fim.menDistancia > novaDist) {
                    // Remove the node from the fila to update the distance value.
                    fila.remove(adjacente.fim);
                    adjacente.fim.menDistancia = novaDist;

                    // Take the caminho visited till now and add the new node.
                    adjacente.fim.caminho = new ArrayList<Vertice>(u.caminho);
                    adjacente.fim.caminho.add(u);

                    //Reenter the node with new distance.
                    fila.add(adjacente.fim);
                }
            }
        }
    }

    public double calcSomaPeso(int numVertice) {

        Vertice vertice = searchVertice(numVertice);

        double somaPeso = 0;

        if(vertice == null) {
            System.out.println("Não foi possivel encontrar o vertice.");
        } else {
            for(Aresta adjacente : vertice.adjacentes) {
                if(adjacente != null && adjacente.fim.equals(vertice)) {
                    somaPeso += adjacente.peso;
                }
            }
         }

        return somaPeso;
    }

    public Vertice searchVertice(int numVertice) {
        if(this.vertices.isEmpty()) {
            return null;
        } else {
            for(Vertice vertice : this.vertices) {
                if(vertice != null && vertice.nome.equals("v" + numVertice)) {
                    return vertice;
                }
            }
        }

        return null;
    }

}
