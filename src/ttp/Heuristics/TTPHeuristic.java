
package ttp.Heuristics;

import java.util.*;
import java.lang.Object;


public class TTPHeuristic {
   TTPInstance instance;

   public TTPHeuristic(TTPInstance instance)
   {
      this.instance = instance;
   }
   
   //Constructive Heuristic
   public TTPSolution SimpleHeuristic()
   {
      //getting tsp tour 
      TSPHeuristic tsp = new TSPHeuristic(this.instance);
      int[] tour = tsp.getTspTour();
      
      // Fill D: D[xi] is the total traveling distance from the city xi to xn
      // and from xn to the first city 
      double[] D = new double[this.instance.numberOfNodes];
      D[tour[this.instance.numberOfNodes-1]] = 
         instance.distances(tour[this.instance.numberOfNodes-1], 0);
      for(int i=this.instance.numberOfNodes-2; i>=0; i--)
         D[tour[i]] = D[tour[i+1]] + instance.distances(tour[i], tour[i+1]);
      
      //total traveling time when no items are picked at all
      double _t = D[0]/instance.maxSpeed;
      
      //list of objects Element with constants (score and u) used after
      List<Element> elements = new LinkedList<Element>();

      //constant used for calculations
      double v = (instance.maxSpeed - instance.minSpeed)/instance.capacityOfKnapsack;

      int itemsPerCity = this.instance.numberOfItems / (tour.length-2);
      double rentingRatio = this.instance.rentingRatio;
      for(int i=1; i<tour.length-1; i++)
      {
         for(int k=0; k<itemsPerCity; k++)
         {
            // Calculate the index of the item in the items-array
            int itemIndex = (tour[i]-1)+k*(this.instance.numberOfNodes-1); 

            int xi = instance.items[itemIndex][3]; //city xi
            int weight_xi_k = instance.items[itemIndex][2]; //weight of the item k of the city xi
            int profit_xi_k = instance.items[itemIndex][1]; //profit of the item k of the city xi

            //auxiliary variables
            double txi_k = D[xi]/(instance.maxSpeed - v*weight_xi_k);
            double _txi_k = _t - D[xi] + txi_k;

            //score and u are used for the next step of the heuristic
            double score_xi_k = profit_xi_k - rentingRatio * txi_k;
            double uxi_k = rentingRatio * _t + (profit_xi_k - rentingRatio * _txi_k);

            // Calculate the index of the array given by the KP part of the solution
            int indexOfPackingPlan = (i-1)*itemsPerCity+k;
            elements.add(new Element(itemIndex, indexOfPackingPlan, uxi_k, score_xi_k));
         }
      }

      //sort elements in descending order score values
      Collections.sort(elements, new ElementComparator());
      
      //current weight
      long Wc = 0;

      //total capacity of the Knapsack
      long W = instance.capacityOfKnapsack;

      //packingPlan[i] = 1 means the item is picked, 0 other wise
      int[] packingPlan = new int[instance.numberOfItems];
      for(Element e : elements)
      {
         int weight = instance.items[e.getItem()][2];
         //if the item still fit in the knapsack and if it is a "good" item (see article)
         //we get it
         if((Wc+weight) < W && e.getU() > 0)
         {
            packingPlan[e.getIndexOfPackingPlan()]=1;
            Wc += weight;
         }
         if(Wc == W) break;
      }
      
      //build the solution
      TTPSolution solution = new TTPSolution(tour, packingPlan);
      
      return solution;
   }
   
   //Random Local Search Heuristic
   public TTPSolution RLS(int numberOfIterations){
	   TSPHeuristic tsp = new TSPHeuristic(this.instance); // Modify to the wanted heuristic
	   int[] tour = tsp.getTspTour();
	   // Initialize such that no items are packed
	   TTPSolution output = new TTPSolution(tour, new int[this.instance.numberOfItems]);

      this.instance.evaluate(output, false);
	   
	   // Counter for the quantity of iterations that represents no change to the objective value
	   int i = 0;
	   // Random number generator
	   Random randomGenerator = new Random();
	   while(i < numberOfIterations){
		   // Copy the original solution
		   int[] auxPackingPlan = new int[this.instance.numberOfItems];
		   for(int j = 0; j < this.instance.numberOfItems; j++)
			   auxPackingPlan[j] = output.packingPlan[j];

		   int randomInt = randomGenerator.nextInt(this.instance.numberOfItems);
		   // Change the state of one random item to 1 (packed) if it's 0 (not packed) and the other way around
		   auxPackingPlan[randomInt] = 1 - auxPackingPlan[randomInt];
		   TTPSolution neighbor = new TTPSolution(tour, auxPackingPlan);
		   // If the modified solution is worse than the original one or not feasible
         this.instance.evaluate(neighbor, false);
		   if(neighbor.ob < output.ob || neighbor.wendUsed > this.instance.capacityOfKnapsack)
			   // Increase counter
			   i++;
		   // If it's better
		   else{
			   // Change the current solution
			   output = neighbor;
			   // Restart counter
			   i = 0;
		   }
	   }
	   return output;
   }
   
   //Evolutionary Heuristic
   public TTPSolution EA(int numberOfIterations){
	   TSPHeuristic tsp = new TSPHeuristic(this.instance); // Modify to the wanted heuristic
	   int[] tour = tsp.getTspTour();
	   // Initialize such that no items are packed
	   TTPSolution output = new TTPSolution(tour, new int[this.instance.numberOfItems]);
      this.instance.evaluate(output, false);
	   
	   // Counter for the quantity of iterations that represents no change to the objective value
	   int i = 0;
	   // Random number generator
	   Random randomGenerator = new Random();
	   double cutoff = 1.0/this.instance.numberOfItems;
	   while(i < numberOfIterations){
		   // Change the state of an item with probability 1/m
		   int[] auxPackingPlan = new int[this.instance.numberOfItems];
		   for(int j = 0; j < this.instance.numberOfItems; j++){
			   if(randomGenerator.nextDouble() > cutoff)
				   auxPackingPlan[j] = output.packingPlan[j];
			   else
				   auxPackingPlan[j] = 1 - output.packingPlan[j];
		   }
		   TTPSolution auxSolution = new TTPSolution(tour, auxPackingPlan);
		   // If the modified solution is worse than the original one or not feasible
         this.instance.evaluate(auxSolution, false);
		   if(auxSolution.ob < output.ob || auxSolution.wendUsed > this.instance.capacityOfKnapsack)
			   // Increase counter
			   i++;
		   // If it's better
		   else{
			   // Change the current solution
			   output = auxSolution;
			   // Restart counter
			   i = 0;
		   }
	   }
	   return output;   
   }

   //"Our" heuristic: RLS with Metropolis heuristic
   public TTPSolution RLSMetropolis(int numberOfIterations, double T){ 
      //T is a constant value used for probability
	   TSPHeuristic tsp = new TSPHeuristic(this.instance); // Modify to the wanted heuristic
	   int[] tour = tsp.getTspTour();
	   // Initialize such that no items are packed
	   TTPSolution output = new TTPSolution(tour, new int[this.instance.numberOfItems]);

	   TTPSolution auxSolution = new TTPSolution(tour, new int[this.instance.numberOfItems]);

      this.instance.evaluate(output, false);
      this.instance.evaluate(auxSolution, false);
	   
	   // Counter for the quantity of iterations that represents no change to the objective value
	   int i = 0;
	   // Random number generator
	   Random randomGenerator = new Random();
	   while(i < numberOfIterations)
      {
		   // Copy the original solution
		   int[] auxPackingPlan = new int[this.instance.numberOfItems];
		   for(int j = 0; j < this.instance.numberOfItems; j++)
			   auxPackingPlan[j] = auxSolution.packingPlan[j];

		   int randomInt = randomGenerator.nextInt(this.instance.numberOfItems);
		   // Change the state of one random item to 1 (packed) if it's 0 (not packed) and the other way around
		   auxPackingPlan[randomInt] = 1 - auxPackingPlan[randomInt];
		   TTPSolution neighbor = new TTPSolution(tour, auxPackingPlan);
		   // If the modified solution is worse than the original one or not feasible
         this.instance.evaluate(neighbor, false);

         // If the item still fit in the knapsack
         if(neighbor.wendUsed < this.instance.capacityOfKnapsack){
            if(neighbor.ob >= auxSolution.ob){
               auxSolution = neighbor;
               if(auxSolution.ob > output.ob) 
               {
                  //if auxSolution is better than output we update output
                  output = auxSolution;
                  i = 0;
               }
               //if it is not better we avance the iteration counter
               else
                  i++;
            }
            else 
            {
               //if neighbor solution is worse than auxSolution, we accept update
               //auxSolution to neighbor with probability exp((neighbor.ob - auxSolution.ob)/T)
               //we do a "relaxation" compared to the previous algorithm
               double randomDouble = randomGenerator.nextDouble();
               if(randomDouble < Math.exp((neighbor.ob - auxSolution.ob)/T))
                  auxSolution = neighbor;
               i++;
            }
         }
         else
            i++;
	   }
	   return output;
   }
}

