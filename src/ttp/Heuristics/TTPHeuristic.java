
package ttp.Heuristics;

import java.util.*;
import java.lang.Object;
import java.awt.geom.Point2D;


public class TTPHeuristic {
   TTPInstance instance;

   public TTPHeuristic(TTPInstance instance)
   {
      this.instance = instance;
   }
   
   public TTPSolution SimpleHeuristic()
   {
      TSPInstance tspI = new TSPInstance(this.instance);
      tspI.getTspTour();

      TSPHeuristic tsp = new TSPHeuristic(this.instance);
      int[] tour = tsp.getTspTour();
      

      int number_nodes = this.instance.nodes.length;
      double[] D = new double[number_nodes];

      Point2D p1 = new Point2D.Double(
            instance.nodes[number_nodes-1][1], instance.nodes[number_nodes-1][2]);
      Point2D p2 = new Point2D.Double(
            instance.nodes[0][1], instance.nodes[0][2]);

      D[number_nodes-1] = instance.distances(number_nodes-1, 0);
      for(int i=number_nodes-2; i>=0; i--)
         D[i] = D[i+1] + instance.distances(i, i+1);
      
      double _t = D[0]/instance.maxSpeed;
      
      List<Element> elements = new LinkedList<Element>();
      
      double v = (instance.maxSpeed - instance.minSpeed)/instance.capacityOfKnapsack;
      int size_items = instance.items.length;
      double rentingRatio = instance.rentingRatio;
      for(int k=0; k<size_items; k++)
      {
         int xi = instance.items[k][3];
         int weight_xi_k = instance.items[k][2];
         int profit_xi_k = instance.items[k][1];

         double txi_k = D[xi]/(instance.maxSpeed - v*weight_xi_k);

         double _txi_k = _t - D[xi] + txi_k;
         double score_xi_k = profit_xi_k - rentingRatio * txi_k;
         double uxi_k = rentingRatio * _t + (profit_xi_k - rentingRatio * _txi_k);
         
         elements.add(new Element(k, uxi_k, score_xi_k));
      }

      //sort
      Collections.sort(elements, new ElementComparator());
      
      long Wc = 0;
      long W = instance.capacityOfKnapsack;

      int[] packingPlan = new int[instance.numberOfItems];

      for(Element e : elements)
      {
         int weight = instance.items[e.getItem()][2];
         if(Wc+weight < W && e.getU() > 0)
         {
            packingPlan[e.getItem()]=1;
            Wc += weight;
         }
         if(Wc == W) break;
      }
      
      TTPSolution solution = new TTPSolution(tour, packingPlan);
      
      return solution;
   }
   
   public TTPSolution RLS(int numberOfIterations){
	   TSPHeuristic tsp = new TSPHeuristic(this.instance); // Modify to the wanted heuristic
	   int[] tour = tsp.getTspTour();
	   // Initialize such that no items are packed
	   TTPSolution output = new TTPSolution(tour, new int[this.instance.numberOfItems]);
         System.out.println("\n\n ------------------------------------------------------------ \n\n");
	   
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
		   TTPSolution auxSolution = new TTPSolution(tour, auxPackingPlan);
		   // If the modified solution is worse than the original one or not feasible
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
   
   public TTPSolution EA(int numberOfIterations){
	   TSPHeuristic tsp = new TSPHeuristic(this.instance); // Modify to the wanted heuristic
	   int[] tour = tsp.getTspTour();
	   // Initialize such that no items are packed
	   TTPSolution output = new TTPSolution(tour, new int[this.instance.numberOfItems]);
	   
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
}

