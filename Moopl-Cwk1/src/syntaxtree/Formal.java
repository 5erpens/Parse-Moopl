package syntaxtree;

import visitor.Visitor;

public class Formal extends AST {

    public final Type t;
    public final String id;

    public Formal(Type at, String aid) {
        t = at;
        id = aid;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
