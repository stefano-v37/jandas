package Jandas;

import java.util.Collection;
import java.util.List;

public class Index extends Columns{
    // TODO make df autofill and also pass Index in construct
    public Index(List index) {
        super(index, Index.class, "index");
    }
}
