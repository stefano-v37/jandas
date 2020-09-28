package Jandas;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    public static <T> List<T> cloneList(List<T> reference){
        List<T> cloned = new ArrayList<T>(reference.size());
        for (int i = 0; i < reference.size(); i++) {
            cloned.add(reference.get(i));
        }
        return cloned;
    }
}
