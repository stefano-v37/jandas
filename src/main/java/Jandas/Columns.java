package Jandas;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Columns {

    private String _name;
    private List _values;
    private Object _type;
    private Map<String, Object> _map = new HashMap<>();

    //constructor #0: only create the instance
    public Columns(){
        List voidList = new ArrayList();
        setValues(voidList);
        setType();
        setName();

        build();
    }

    // constructor #1: fast, provide only data and retrieve the rest
    public <T> Columns(Collection<T> values){
        setValues((List)values);
        setType();
        setName();

        build();
    }

    // constructor #2: initialize void column providing type and name
    public <T> Columns(T type){
        List<T> values = new ArrayList<T>();
        setValues(values);
        setType(type);
        setName();

        build();
    }

    public <T> Columns(T type, String name){
        List<T> values = new ArrayList<T>();
        setValues(values);
        setType(type);
        setName(name);

        build();
    }

    // constructor #3: full
    public <T> Columns(List values, T type, String name){
        setValues(values);
        setType(type);
        setName(name);

        build();
    }

    // constructor #4: pass object
    public Columns(List values, String name){
        setValues(values);
        setType();
        setName(name);

        build();
    }

    public Columns(Object value, int length){
        List values = new ArrayList<>();
        for (int i=0; i<length; i++){
            values.add(value);
        }
        setValues(values);
        setName();
        setType();

        build();
    }

    public Columns(Object value, int length, String name){
        List values = new ArrayList<>();
        for (int i=0; i<length; i++){
            values.add(value);
        }
        setValues(values);
        setName(name);
        setType();

        build();
    }

    // name methods
    public void setName(){
        setName("Column");
    }

    public void setName(String name){
        this._name = name;
    }

    // data methods
    public <T> void setValues(List<T> values) {
        this._values = Utilities.cloneList(values);
    }

    public <T> void put(T values) {
        this._values.add(values);
    }

    public <T> void put(List<T> values) {
        this._values.addAll(values);
    }

    // type methods
    public <T> void setType() {
        this._setType(this._values);
    }

    public <T> void setType(T type) {
        this._type = type;
    }

    public <T> void _setType(List<T> values) {
        if (values.size()>0) {
            this._type = values.get(0).getClass();
        }
        else {
            System.out.println("Please add data to the Column or initialize the type");
        }
    }

    // struct methods
    public void append(Columns col2){
        if (this.get_type() == col2.get_type()){
            this.put(col2.get_values());
        }
    }

    public void append(List values){
        Columns col2 = new Columns(values);
        if (this.get_type() == col2.get_type()){
            this.put(col2.get_values());
        }
    }

    // Make a Map for DataFrame
    public void build(){
        build("values", this._values);
    }

    public void build(String dataName, Object values){
        this._map.put(dataName, values);
        this._map.put("type", this._type);
    }
}
