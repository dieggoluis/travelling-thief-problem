package ttp.Heuristics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class for representing a solution of TTP
 *
 * @author wagner
 */
public class TTPSolution {
    
    public int[] tspTour;
    public int[] packingPlan;
    
/* "fp" final profit gained form the picked items,
   "ft" the time takes to finish the tour (including changes of the speed),
   "ob" objective value,
   "wend" weight of the knapsack at the end of the tour   
*/
    public double fp   = Double.NEGATIVE_INFINITY;
    public double ft   = Double.POSITIVE_INFINITY;
    public long ftraw   = Long.MAX_VALUE;
    public double ob   = Double.NEGATIVE_INFINITY;
    public double wend = Double.POSITIVE_INFINITY;
    public double wendUsed = Double.POSITIVE_INFINITY;
    public long computationTime = Long.MAX_VALUE;
    
    public void reset() {
        fp = Double.NEGATIVE_INFINITY;
        ft = Double.POSITIVE_INFINITY;
        ftraw = Long.MAX_VALUE;
        ob = Double.NEGATIVE_INFINITY;
        wend = Double.POSITIVE_INFINITY;
        wendUsed = Double.POSITIVE_INFINITY;
        computationTime = Long.MAX_VALUE;
    }
    
    public void print() {
        System.out.print(wend+" "+wendUsed+" "+fp+" "+ftraw+" "+ft+" "+ob +" "+computationTime);
    }
    public void println() {
        this.print();
        System.out.println();
    }
    
    public void printFull() {
        this.println();
        System.out.println("tspTour "+Arrays.toString(tspTour));
        System.out.println("packingPlan "+Arrays.toString(packingPlan));
    }
    
    public double getObjective() {
        return this.ob;
    }
    
    public TTPSolution(int[] tspTour, int[] packingPlan) {
        this.tspTour = tspTour;
        this.packingPlan = packingPlan;
    }
    
    
    
    /** 
     * 
     * answer() and writeResult(String file) are necessary for the evaluation.
     * 
     * - The output file needs to contain the following information as comma-separated values in square brackets: 
     *           the permutation of the cities in the first line (note: the first city is "1", do not print the "1" at the end), and 
     *           the list of packed items in the second line (the numbering of the items starts with "1"). 
     *           For example:
     *                  [1,5,4,2,3]
     *                  [20,113]
     *           which means that only the items with the numbers 20 and 113 are picked, and the sequence of visited cities is <1,5,4,2,3,1>. 
     *           Note that this format can easily be achieved in Java with the function Arrays.toString(...). 
     **/
    private String answer() {
    	int[] tourOut = new int[tspTour.length - 1];
    	for (int i = 0; i < tspTour.length - 1; i++){
    		tourOut[i] = tspTour[i] + 1;
    	}
    	
    	int itemsPerCity = packingPlan.length / (tspTour.length  -2);
    	
    	List<Integer> packingPlanList = new ArrayList<Integer>();
    	int packingPlanIndex = 0;
    	for (int i = 1; i < tspTour.length - 1; i++){
    		int city = tspTour[i];
    		for (int j = 0; j < itemsPerCity; j++){
    			if (packingPlan[packingPlanIndex] == 1){
    				int itemIndex = j * (tspTour.length  -2) + city - 1;
    				int itemIndexFrom1 = itemIndex + 1;
    				packingPlanList.add(itemIndexFrom1 );
    			}
    			packingPlanIndex++;
    		}	    	
    	}   	
    	Collections.sort(packingPlanList);
    	
    	int[] packingOut = new int[packingPlanList.size()];
    	for (int i = 0; i < packingPlanList.size(); i++){
    		packingOut[i] = packingPlanList.get(i);
    	}
        return Arrays.toString(tourOut) + "\n" + Arrays.toString(packingOut) +  "\n" + fp;
    }
    
    public void writeResult(String title){
            BufferedWriter writer = null;
            try {
                    writer = new BufferedWriter(new FileWriter(title, false));
                    writer.write(this.answer());
                    writer.flush();
                    writer.close();
            } catch (IOException e) {
                    e.printStackTrace();

            }
    }

}
