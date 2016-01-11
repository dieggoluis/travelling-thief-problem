Linux users can use the file makefile (src/makefile) to compile \*.java files that belong to the project using the following command
   make

Also we can remove \*.class files using the following command
   make clean

To run an instance, in the folder src/ttp we do the following
   java ttp.Heuristics.TTPInstance ttp/Instances/<folder_instance>/<name_instance>

If you want to modify the TSP heuristic or the parameters of the heuristics it's necessary to modify the TSPHeuristic.java and TTPHeuristic.java, respectively

