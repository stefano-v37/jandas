package Jandas;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Columns {

    private String _name;
    private List _values;
    private Object _type;
    private Map<String, Object> _map = new HashMap<>();

    // constructor #1: fast, provide only data and retrieve the rest
    public <T> Columns(List<T> values){
        setValues(values);
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

    // Make a Map for DataFrame
    public void build(){
        this._map.put("values", this._values);
        this._map.put("type", this._type);
    }
}
