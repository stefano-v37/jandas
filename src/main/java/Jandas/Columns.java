package Jandas;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Columns {

    private String _name;
    private List _values;
    private Object _type;

    public <T> void setValues(List<T> values) {
        this._values = Utilities.cloneList(values);
    }

    public <T> void put(T values) {
        this._values.add(values);
    }

    public <T> void put(List<T> values) {
        this._values.addAll(values);
    }
}
