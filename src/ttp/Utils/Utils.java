/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ttp.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wagner
 */
public class Utils {
    
    public static long startTime = 0;
    public static long startTiming() {
        startTime = System.currentTimeMillis();
        return startTime;
    }
    public static long stopTiming() {
        long stopTime = System.currentTimeMillis(); 
        long timeUsed = stopTime-startTime;
//        long timeUsed = (stopTime-startTime)/1000;
//        System.out.println("[time used: "+timeUsed+"s]") ;
        return timeUsed;
    }
    public static long stopTimingPrint() {
        long stopTime = System.currentTimeMillis(); 
        long timeUsed = (stopTime-startTime);
        System.out.println("[time used: "+timeUsed+"ms]") ;
//        long timeUsed = (stopTime-startTime)/1000;
//        System.out.println("[time used: "+timeUsed+"s]") ;
        return timeUsed;
    }
    
    public static File[] getFileList(final String[] args) {
        
        boolean debugPrint = false;
        
        File f = new File(args[0]);
        try {
            if (debugPrint) System.out.println(f.getCanonicalPath());
        } catch (IOException ex) {
        }
        
        File[] fa = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                boolean result = false;
                if (name.contains(".ttp") 
                        && name.contains(args[1])
                        ) result = true;
                return result;
            }});
        
        if (debugPrint)
            for (File temp:fa) {
                System.out.println(temp.getAbsolutePath());
            }
        
        return fa;
    }
    
    public static int[] permutation(int N) {
        int[] a = new int[N];
        for (int i = 0; i < N; i++) 
            a[i] = i;
        // shuffle
        for (int i = 0; i < N; i++) {
            int r = (int) (Math.random() * (i+1));     // int between 0 and i
            int swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
        return a;
    }
    
    
    public static void createClusterArray() {
        String[] tspNames = new String[]{"a280",
"berlin52",
"bier127",
"brd14051",
"ch130",
"ch150",
"d198",
"d493",
"d657",
"d1291",
"d1655",
"d2103",
"d15112",
"d18512",
"dsj1000",
"eil51",
"eil76",
"eil101",
"fl417",
"fl1400",
"fl1577",
"fl3795",
"fnl4461",
"gil262",
"kroA100",
"kroA150",
"kroA200",
"kroB100",
"kroB150",
"kroB200",
"kroC100",
"kroD100",
"kroE100",
"lin105",
"lin318",
"nrw1379",
"p654",
"pcb442",
"pcb1173",
"pcb3038",
"pla7397",
"pla33810",
"pla85900",
"pr76",
"pr107",
"pr124",
"pr136",
"pr144",
"pr152",
"pr226",
"pr264",
"pr299",
"pr439",
"pr1002",
"pr2392",
"rat99",
"rat195",
"rat575",
"rat783",
"rd100",
"rd400",
"rl1304",
"rl1323",
"rl1889",
"rl5915",
"rl5934",
"rl11849",
"st70",
"ts225",
"tsp225",
"u159",
"u574",
"u724",
"u1060",
"u1432",
"u1817",
"u2152",
"u2319",
"usa13509",
"vm1084",
"vm1748"};
        String[] knapsackTypes = new String[] {"bounded-strongly-corr","uncorr-similar-weights","uncorr"};
        int[] knapsackCapacities = new int[]{1,3,5,10};
        
        int instancesCount = 10;
        String[] instances = new String[instancesCount];
        for (int i=1; i<=instancesCount; i++) {
            
            String value = "";
            if (i<10) value += "0";
            value += i;
            
            instances[i-1] = value;
        }
        
        
        
        String scriptLocation = "/home/mwagner/scratch/ttp/driver.sh";
        
        String subfolder = "instances";
        
        
        
        
        int overallSanity = 0;
        
        for (String tsp:tspNames) {
            
            int sanity = 0;

            Matcher matcher = Pattern.compile("[0-9]+").matcher(tsp);
            matcher.find();
            
            int numberOfCities = Integer.parseInt(matcher.group());
//            String substring = tsp.substring(indexFirst);
            
//            System.out.println(tsp+" "+matcher.group());
            
//            matcher = Pattern.compile("\\s").matcher(tsp);
//            matcher.find();
//            int indexSecond = Integer.valueOf(matcher.group());
            
//            int numberOfCities = Integer.parseInt(substring.substring(0, indexSecond));
            
//            System.out.println(numberOfCities);
            
            
            for (int cap:knapsackCapacities) {
            
                for (String type:knapsackTypes) {
                    
                    for (String i:instances) {

                        String result = "";

                        result += scriptLocation + " " + subfolder + " " 
                                + tsp +"_n"+cap*(numberOfCities-1)+"_"
                                + type + "_" + i+".ttp "
                                ;
                        
                        result += "1 10000 600000"; // approach, max iterations without impr., time in ms.

                        System.out.println(result);
                        sanity++;
                        
                    }
                
                }
            }
            overallSanity += sanity;
            System.out.println("sanity="+sanity);
        }
        System.out.println("overallSanity="+overallSanity);
        
        
        for (String s:tspNames) {
            System.out.println("wget -c http://cs.adelaide.edu.au/~optlog/CEC2014COMP_Instances/"
                    +s+"-ttp.rar ");
        }
        
    }
    
    public static void main(String[] args) {
        createClusterArray();
    }
}
