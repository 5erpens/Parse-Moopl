package syntaxtree;

import visitor.Visitor;

public class ExpVar extends Exp {

    public final Var v;

    public ExpVar(Var av) {
        v = av;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
