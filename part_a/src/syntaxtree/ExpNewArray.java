package syntaxtree;

import visitor.Visitor;

public class ExpNewArray extends Exp {

    public final Type t;
    public final Exp e;

    public ExpNewArray(Type at, Exp ae) {
        t = at;
        e = ae;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
