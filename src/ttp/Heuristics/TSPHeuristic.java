
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
      return tspTour;
   }
}

