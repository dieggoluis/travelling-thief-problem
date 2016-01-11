package ttp.Heuristics;

import java.util.Comparator;

public class ArcComparator implements Comparator<Arc> {
    @Override
    public int compare(Arc a0, Arc a1) {
        double d0 = a0.length; 
        double d1 = a1.length;
        if( d0 < d1 )
            return -1;
        else if( d0 > d1 )
            return 1;
        else
            return 0;
    }
}
