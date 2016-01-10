package ttp.Heuristics;

import java.util.*;

public class TSPInstance {
   int[] tspTour;
   TTPInstance instance;

   public TSPInstance(TTPInstance instance){
      this.instance = instance;
      nearestNeighbor();
   }

   public int[] getTspTour(){
      //debug
      int size_tsp = tspTour.length;
      System.out.println("\n$$$$$$$$$$$$$$$$$$" + size_tsp);
      for(int i=0; i<size_tsp; i++)
         System.out.print(tspTour[i] + ",");
    
      return tspTour;
   }
   
   public void nearestNeighbor(){
	   this.tspTour = new int[instance.numberOfNodes+1];
	   // HashSet that saves the label all non visited cities
	   HashSet<Integer> nonVisited = new HashSet<Integer>();
	   tspTour[0] = 0;
	   tspTour[tspTour.length-1] = 0;
	   // Distance matrix
	   double[][] distance = new double[instance.numberOfNodes][instance.numberOfNodes];
	   // Fill the matrix (no need to fill distance[i][i] with 0 as default value is already 0)
	   for(int i = 0; i < instance.numberOfNodes-1; i++){
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
}
