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
        this._values = List.copyOf(values);;
    }
}
