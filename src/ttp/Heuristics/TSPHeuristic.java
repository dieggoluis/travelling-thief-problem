
package ttp.Heuristics;
import java.util.*;

public class TSPHeuristic {
   int[] tspTour;
   TTPInstance instance;

   public TSPHeuristic(TTPInstance instance)
   {
      this.instance = instance;
   }

   public int[] getTspTour()
   {
      int number_nodes = this.instance.nodes.length;
      tspTour = new int[number_nodes+1];
      for(int i=0; i<number_nodes; i++) tspTour[i] = i;
      tspTour[number_nodes] = 0;
      return tspTour;
   }
}

