
package ttp.Heuristics;

import java.util.*;
import java.lang.Object;

public class TTPHeuristic {
   TTPInstance instance;

   public TTPHeuristic(TTPInstance instance)
   {
      this.instance = instance;
   }
   
   private double 2D_distance(int x1, int y1, int x2, int y2)
   {
      return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
   }

   public TTPSolution SimpleHeuristic()
   {

   }
}

