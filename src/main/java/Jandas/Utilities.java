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

    // fromCsv
    public static String[] split(String line, String qt, String sep){
        if (qt != "") {
            line = line.substring(1, line.length() - 1);
        }
        return line.split(qt + sep + qt);
    }

    public static Columns recognizeColumnType(Columns col) {
        List<Object> newValues = new ArrayList<>();

        String type = "Long";
        if (type == "Long") {
            try {
                newValues = new ArrayList<>();
                for (Object val : col.get_values()) {
                    Long value = Long.valueOf((String) val);
                    newValues.add(value);
                }
            } catch (Exception e1) {
                type = "Double";
            }
        }

        if (type == "Double") {
            try {
                newValues = new ArrayList<>();
                for (Object val : col.get_values()) {
                    Double value = Double.valueOf((String) val);
                    newValues.add(value);
                }
            } catch (Exception e1) {
                type = "String";
            }
        }

        if (type == "String") {
            int boolscore = 0;
            for (Object val : col.get_values()) {
                if ((val == "true") || (val == "false")){
                    boolscore ++;
                }
            }
            if (boolscore == col.get_values().size()){
                type = "Boolean";
            }
        }

        if (type == "Boolean") {
            try {
                for (Object val : col.get_values()) {
                    Boolean value = Boolean.valueOf((String) val);
                    newValues.add(value);
                }
            } catch (Exception e2) {
                type = "String";
            }
        }

        if (type == "String") {
            return col;
        }
        return new Columns(newValues, col.get_name());
    }

//    public static Columns oldrecognizeColumnType(Columns col) {
//        // takes string-column as input
//        // TODO: change the way arrays are populated due to partial population
//        List<Double> floatValue = new ArrayList<>();
//        List<Long> longValue = new ArrayList<>();
//        List<Boolean> boolValue = new ArrayList<>();
//        int floatValuescore = 0;
//        int longValuescore = 0;
//        int boolValuescore = 0;
//        boolean flag1 = true;
//        boolean flag2 = true;
//        for (Object val : col.get_values()) {
//            try {
//                Long value = Long.valueOf((String) val);
//                longValue.add(value);
//                longValuescore++;
//            } catch (Exception e1) {
//                try {
//                    if (flag1) {
//                        for (Long value : longValue) {
//                            Double valueConverted = Double.valueOf(value);
//                            floatValue.add(valueConverted);
//                        }
//                        flag1 = false;
//                    }
//                    Double value = Double.valueOf((String) val);
//                    floatValue.add(value);
//                    floatValuescore++;
//                } catch (Exception e2) {
//                    try {
//                        if (flag2) {
//                            for (Double value : floatValue) {
//                                Boolean valueConverted = Boolean.valueOf(String.valueOf(value));
//                                boolValue.add(valueConverted);
//                            }
//                            flag2 = false;
//                        }
//                        Boolean value = Boolean.valueOf((String) val);
//                        boolValue.add(value);
//                        boolValuescore++;
//                    } catch (Exception e3) {
//                        System.out.println(e3);
//                    }
//                }
//            }
//        }
//        if (longValuescore == col.get_values().size()) {
//            return new Columns(longValue, col.get_name());
//        } else if (floatValuescore + longValuescore == col.get_values().size()) {
//            return new Columns(floatValue, col.get_name());
//        } else if (boolValuescore == col.get_values().size()) {
//            return new Columns(boolValue, col.get_name());
//        } else {
//            return col;
//        }
//    }
}
