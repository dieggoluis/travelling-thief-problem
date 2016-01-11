
package ttp.Heuristics;
import java.util.*;

public class TSPHeuristic {
   int[] tspTour;
   TTPInstance instance;

   public TSPHeuristic(TTPInstance instance)
   {
      this.instance = instance;
      // nearest neighbor heuristic

      //this.trivialTour();
      this.nearestNeighbor();
      this.opt2();

   }

   public int[] getTspTour()
   {
      return tspTour;
   }

   // nearest neighbor heuristic for TSP 
   public void nearestNeighbor(){
      this.tspTour = new int[instance.numberOfNodes+1];
      // HashSet that saves the label all non visited cities
      HashSet<Integer> nonVisited = new HashSet<Integer>();
      tspTour[0] = 0;
      tspTour[tspTour.length-1] = 0;
      // Distance matrix
      double[][] distance = new double[instance.numberOfNodes][instance.numberOfNodes];
      // Fill the matrix (no need to fill distance[i][i] with 0 as default value is already 0)
      for(int i = 0; i < instance.numberOfNodes; i++){
         nonVisited.add(i);
         for(int j = i+1; j < instance.numberOfNodes; j++){
            distance[i][j] = instance.distances(i, j);
            distance[j][i] = distance[i][j];
         }
      }
      int currentCity = 0;
      // Removes 0 from the non visited cities
      nonVisited.remove(0);
      int closestCity = 0;
      // Variable to save the position of the city in the tour
      int tourIndex = 1;
      double minDistance = Double.POSITIVE_INFINITY;
      // While there's a non visited city
      while(!nonVisited.isEmpty()){
         // Search for the closest city that has not been visited
         for(int j = 0; j < instance.numberOfNodes; j++){
            if(nonVisited.contains(j) && distance[currentCity][j] < minDistance){
               closestCity = j;
               minDistance = distance[currentCity][j];
            }
         }
         // Put the closest city in the tour
         tspTour[tourIndex] = closestCity;
         tourIndex++;
         // Remove it from the HashSet
         nonVisited.remove(closestCity);
         // Prepare variables for next iteration
         currentCity = closestCity;
         minDistance = Double.POSITIVE_INFINITY;
      }
   }

   public LinkedList<Integer>[] primTree(double[][] distance){
      // Representantion of the tree (a linked list of the sons for every node)
      LinkedList<Integer>[] output = new LinkedList[this.instance.numberOfNodes];

      for(int i=0; i<this.instance.numberOfNodes; i++)
         output[i] = new LinkedList<Integer>();


      PriorityQueue<Arc> q = new PriorityQueue<Arc>(this.instance.numberOfNodes-1,new ArcComparator());
      HashSet<Integer> nonVisited = new HashSet<Integer>();
      // Put all the non visited nodes in the hash set (0 is the starting node)
      for(int i = 1; i < this.instance.numberOfNodes; i++)
         nonVisited.add(i);
      // Add all edges of the start node to the priority queue
      for(int i = 1; i < this.instance.numberOfNodes; i++)
         q.add(new Arc(0, i, distance[0][i]));
      // While the priority queue is not empty, pop the head of it
      while(!q.isEmpty()){
         Arc aux = q.poll();
         // If the target has not been visited yet, add the edge to the output and
         // add all the edges of the target to the priority queue and removes the target from the nonVisited nodes
         if(nonVisited.contains(aux.target)){
            nonVisited.remove(aux.target);	   
            for(int i = 0; i < this.instance.numberOfNodes; i++)
               q.add(new Arc(aux.target, i, distance[aux.target][i]));
            output[aux.source].addLast(aux.target);
         }
      }
      return output;
   }

   public void opt2(){
      this.tspTour = new int[this.instance.numberOfNodes+1];
      LinkedList<Integer> aux = new LinkedList<Integer>();
      // Distance matrix
      double[][] distance = new double[instance.numberOfNodes][instance.numberOfNodes];
      // Fill the matrix (no need to fill distance[i][i] with 0 as default value is already 0)
      for(int i = 0; i < instance.numberOfNodes; i++){
         for(int j = i+1; j < instance.numberOfNodes; j++){
            distance[i][j] = instance.distances(i, j);
            distance[j][i] = distance[i][j];
         }
      }
      // Get the minimun spanning tree
      LinkedList<Integer>[] prim = primTree(distance);

      // Create an eulerian cycle from this tree (doubling every edge)
      Aux2opt(aux, prim, 0);
      // Builds a tour from the eulerian cycle (shortcutting when possible, that is,
      // when both nodes of one edge has already been visited, this edge can be removed).
      // Initialize a non visited hash set
      HashSet<Integer> nonVisited = new HashSet<Integer>();
      for(int i = 0; i < instance.numberOfNodes; i++)
         nonVisited.add(i);
      // position of the node in the tour
      int j = 0;
      for(int i = 0; i < aux.size(); i++){
         // if the node has not been visited yet, remove it from the set and add it to the tour
         int node = aux.get(i);
         if(nonVisited.contains(node)){
            nonVisited.remove(node);
            this.tspTour[j] = node;
            j++;
         }
      }
      // Add 0 as the last node of the tour
      this.tspTour[this.instance.numberOfNodes] = 0;
   }

   public void Aux2opt(LinkedList<Integer> aux, LinkedList<Integer>[] prim, int node){
      while(prim[node].size() > 0){
         aux.addLast(node);
         int q = prim[node].poll();
         Aux2opt(aux, prim, q);
      }
      aux.addLast(node);
   }


   //trivial heuristic: 0, 1, 2, ..., n, 0
   public void trivialTour()
   {
      int number_nodes = this.instance.nodes.length;
      tspTour = new int[number_nodes+1];
      for(int i=0; i<number_nodes; i++) tspTour[i] = i;
      tspTour[number_nodes] = 0;
   }
}

