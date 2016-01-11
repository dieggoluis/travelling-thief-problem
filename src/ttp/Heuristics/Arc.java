package ttp.Heuristics;


public class Arc {
  int source, target;
  double length;

  public Arc(int o, int d, double c){
	  source = o;
	  target = d;
	  length = c;
  }
}
