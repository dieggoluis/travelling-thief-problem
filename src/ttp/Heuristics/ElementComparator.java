package ttp.Heuristics;

import java.util.Comparator;

public class ElementComparator implements Comparator<Element> {

    @Override
    public int compare(Element e0, Element e1) {
        if(e0.score < e1.score)
            return 1;
        else if( e0.score > e1.score )
            return -1;
        else
            return 0;
    }
}

