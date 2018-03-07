package syntaxtree;

import visitor.Visitor;

public class StmVarDecl extends Stm {

    public final Type t;
    public final String id;

    public StmVarDecl(Type at, String aid) {
        t = at;
        id = aid;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
    
}
