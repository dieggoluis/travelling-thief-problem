
package ttp.Heuristics;

public class Element {
   int item;
   int indexOfPackingPlan;
   double u;
   double score;
  


   public Element(int item, int indexOfPackingPlan, double u, double score)
   {
      this.item = item;
      this.u = u;
      this.score = score;
      this.indexOfPackingPlan = indexOfPackingPlan;
   }

   public int getIndexOfPackingPlan()
   {
      return this.indexOfPackingPlan;
   }

   public int getItem()
   {
      return this.item;
   }

   public double getU()
   {
      return u;
   }

   public double getScore()
   {
      return this.score;
   }
}
