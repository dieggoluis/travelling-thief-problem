
package ttp.Heuristics;
import java.util.*;

public class TSPHeuristic {
   int[] tspTour;
   TTPInstance instance;

   public TSPHeuristic(TTPInstance instance)
   {
      this.instance = instance;
      // nearest neighbor heuristic
      this.nearestNeighbor();
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

   //trivial heuristic: 0, 1, 2, ..., n, 0
   public void trivialTour()
   {
      int number_nodes = this.instance.nodes.length;
      tspTour = new int[number_nodes+1];
      for(int i=0; i<number_nodes; i++) tspTour[i] = i;
      tspTour[number_nodes] = 0;
   }
}

