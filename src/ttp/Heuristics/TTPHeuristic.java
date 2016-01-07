
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
      TSPHeuristic tsp = new TSPHeuristic(this.instance);
      int[] tour = tsp.getTspTour();
      
      double[] D = new double[tour.length];

      Point2D p1 = new Point2D.Double(
            instance.nodes[tour.length-1][1], instance.nodes[tour.length-1][2]);
      Point2D p2 = new Point2D.Double(
            instance.nodes[0][1], instance.nodes[0][2]);

      D[tour.length-1] = p1.distance(p2);
      for(int i=tour.length-2; i>=0; i++)
      {
         p2 = (Point2D) p1.clone();
         p1.setLocation(instance.nodes[i][1], instance.nodes[i][2]);
         D[i] = D[i+1] + p1.distance(p2);
      }
      
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

         double txi_k = D[xi]/(instance.maxSpeed - v*weight_xi_k); //tratar casos com try, xi muito grande ou tempo nagativo

         double _txi_k = _t - D[xi] + txi_k;
         double score_xi_k = profit_xi_k - rentingRatio * txi_k;
         double uxi_k = rentingRatio * _t + (profit_xi_k - rentingRatio * _txi_k);
         
         elements.add(new Element(k, uxi_k, score_xi_k));
      }

      //sort
      Collections.sort(elements, new ElementComparator());
      
      long Wc = 0;
      long W = instance.capacityOfKnapsack;
      List<Integer> temp_items = new LinkedList<Integer>();
      for(Element e : elements)
      {
         int weight = instance.items[e.getItem()][2];
         if(Wc+weight < W && e.getU() > 0)
         {
            temp_items.add(e.getItem());
            Wc += weight;
         }
         if(Wc == W) break;
      }
      
      int i=0;
      int[] packingPlan = new int[temp_items.size()];
      for(int item: temp_items)
         packingPlan[i++] = item;  
      
      TTPSolution solution = new TTPSolution(tour, packingPlan);
      
      //Calular profit ??

      return solution;
   }
}

