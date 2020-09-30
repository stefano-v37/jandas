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

    public static List<Boolean> isLower(Columns column, double value){
        List<Boolean> rule = new ArrayList<Boolean>();
        for (int i = 0; i < column.get_values().size(); i++){
            if ((float)column.get_values().get(i) < value){
                rule.add(true);
            }
            else{
                rule.add(false);
            }
        }
        return rule;
    }

    public static List<Boolean> isEqual(Columns column, Object value){
        List<Boolean> rule = new ArrayList<Boolean>();
        for (int i = 0; i < column.get_values().size(); i++){
            if (column.get_values().get(i) == value){
                rule.add(true);
            }
            else{
                rule.add(false);
            }
        }
        return rule;
    }

    public static Columns isTrue(Columns column, List<Boolean> rule){
        List<Object> newCol = new ArrayList<Object>();
        for (int i = 0; i < column.get_values().size(); i++) {
            if (rule.get(i) == true) {
                newCol.add(column.get_values().get(i));
            }
        }
        Columns newColumn = new Columns(newCol, column.get_name());
        return newColumn;
    }
}
