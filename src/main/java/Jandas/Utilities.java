package Jandas;

import java.math.BigDecimal;
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

    public static List<Boolean> isEqual(Columns column, Object value) {
        List<Boolean> rule = new ArrayList<Boolean>();
        for (int i = 0; i < column.get_values().size(); i++) {
            if (column.get_type() == BigDecimal.class) {
                rule.add(_bdEqual((BigDecimal) column.get_values().get(i), new BigDecimal(String.valueOf(value))));
            } else {
                if (column.get_values().get(i) == value) {
                    rule.add(true);
                } else {
                    rule.add(false);
                }
            }
        }
        return rule;

    }

    public static boolean _bdEqual(BigDecimal row_i, BigDecimal value){
        if (row_i.compareTo(value) == 0){
            return true;
        }
        else{
            return false;
        }
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
