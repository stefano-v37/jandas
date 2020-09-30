package Jandas;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


@Getter
@Setter
public class DataFrame{
    private Map<String, Columns> data = new HashMap<>();
    private Index index;
    private int _size;

    // constructor 1: initialize void database
    public DataFrame(){
    }

    // constructor 2: initialize db with single column
    public DataFrame(Columns col){
        addColumn(col);
        calculateSize();
        makeIndex();
    }

    // constructor 3: initialize db with array of columns
    // TODO: add in Utilities a convert Type[] to List<Type>
    public DataFrame(Columns[] cols){
        addColumns(cols);
        calculateSize();
        makeIndex();
    }

//     constructor 4: list of columns and list of names
    public DataFrame(Collection cols){
        addColumn(cols);
        calculateSize();
        makeIndex();
    }

    // constructor 5: map of names : lists
    public DataFrame(Map cols){
        addColumns(cols);
        calculateSize();
        makeIndex();
    }


    /*
    !!!!!!!!!!!!!!!!!!!!!!!
    !! Structure methods !!
    !!!!!!!!!!!!!!!!!!!!!!!
     */
    public void isTrue(List<Boolean> rule){
        for (Map.Entry<String, Columns> entry : this.getData().entrySet()) {
            String key = entry.getKey();
            Columns col = entry.getValue();

            this.getData().put(key,Utilities.isTrue(col,rule));
        }
        //TODO: do it for index after complete refactor to include index in columns
        this.build();
    }
    public DataFrame isTrue(List<Boolean> rule, boolean inplace){
        if (inplace){
            this.isTrue(rule);
        }
        else{
            Map<String, Columns> newMap = new HashMap<>();
            for (Map.Entry<String, Columns> entry : this.getData().entrySet()) {
                String key = entry.getKey();
                Columns col = entry.getValue();

                newMap.put(key,Utilities.isTrue(col,rule));
            }
            DataFrame newDf = new DataFrame(newMap);
            return newDf;
        }
        return null;
    }

    // TODO: understand how to compare Map<:,diff type>
    public void addDf(Map<String, Columns> col){
        this.data.putAll(col);
    }

    public void addColumn(Object item){
        Columns newCol = new Columns(item, this.get_size());
        addColumn(newCol);
    }

    public void addColumn(Object item, String name){
        Columns newCol = new Columns(item, this.get_size(), name);
        addColumn(newCol);
    }

    public void addColumns(Map cols){
        Map <String, Columns> tempMap = new HashMap<>();
        if (cols.get(cols.keySet().toArray()[0]).getClass() == Jandas.Columns.class){
            tempMap = cols;
        }
        else{
            Map<String, ArrayList> currentMap = cols;
            for (Map.Entry<String, ArrayList> item : currentMap.entrySet()) {
                tempMap.put(item.getKey(), new Columns(item.getValue()));
            }
        }
        this.data.putAll(tempMap);
    }

    public void addColumn(Columns col){
        String temp_name;
        if (col.get_name() != "Column") {
            temp_name = col.get_name();
        }
        else{
            temp_name = "Column " + this.data.size();
        }
        this.data.put(temp_name, col);
        this.calculateSize();
    }

    public void addColumns(Columns[] cols){
        for (Columns col:cols){
            addColumn(col);
        }
    }

    public void append(Map map2){
        DataFrame df2 = new DataFrame(map2);
        this.append(df2);
    }

    public void append(DataFrame df2){
        for (Map.Entry<String, Columns> col2_i : df2.getData().entrySet()){
            if (this.getData().containsKey(col2_i.getKey())){
                this.getData().get(col2_i.getKey()).append(df2.getData().get(col2_i.getKey()));
            }
        }
        build();
    }

    // override using list of lists
    public void addColumn(Collection col){
        addColumn(new Columns(col));
    }

    public void addColumn(Collection col, String name){
        addColumn(new Columns(col, name));
    }

    public void addColumns(Collection cols){
        for (Object col:cols){
            addColumn((Collection)col);
        }
    }

    public <T> void addColumns(List cols, List names){
        if (cols.size() == names.size())
            for (int i=0; i<cols.size(); i++){
                addColumns((List<T>)cols.get(i), (List<T>)names.get(i));
            }
    }

    private void calculateSize(){
        int size = 0;
        int temp;
        for (Map.Entry<String, Columns> item : this.data.entrySet()){
            temp = item.getValue().get_values().size();
            if (temp > size){
                size = temp;
            }
        }
        this._size = size;
    }

    private void makeIndex(){
        List index = new ArrayList();
        for (int i=0; i<this._size; i++){
            index.add(i);
        }
        this.index = new Index(index);
    }

    // output methods
    public void printData(){
        Map <String, List> tempMap = new HashMap<>();
        for (Map.Entry<String, Columns> item : this.data.entrySet()){
            tempMap.put(item.getKey(), item.getValue().get_values());
        }
        System.out.println(tempMap);
    }

    public void toCsv(String path){
        String csv = "SEP=,\n";
        for (int i=-1; i<this._size; i++) {
            for (Map.Entry<String, Columns> item : this.data.entrySet()){
                if (i == -1){
                    csv += item.getKey() + ", ";
                }
                else{
                    csv += item.getValue().get_values().get(i) + ", ";
                }
            }
            csv = csv.substring(0,csv.length()-2);
            csv += "\n";
        }

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write(csv);
        pw.close();
    }

    // BUILD
    private void build() {
        calculateSize();
        makeIndex();
    }



//    /*
//    override method inside constructor
//    TODO: check if it is correct, because github comments are calling it "WRONG"
//    https://stackoverflow.com/questions/43877035/java-anonymous-subclass-overriding-method-during-object-instance-constructio
//     */
//    @Override
//    public void build() {
//        build("data", _data);
//    }
}
