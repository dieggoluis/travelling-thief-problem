
package ttp.Heuristics;

public class Element {
   int item;
   double u;
   double score;


   public Element(int item, double u, double score)
   {
      this.item = item;
      this.u = u;
      this.score = score;
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
