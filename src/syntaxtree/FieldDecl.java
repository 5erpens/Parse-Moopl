package syntaxtree;

import visitor.Visitor;

public class FieldDecl extends AST {

    public final Type t;
    public final String id;

    public FieldDecl(Type at, String aid) {
        t = at;
        id = aid;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
