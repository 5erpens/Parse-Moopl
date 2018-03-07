package syntaxtree;

import visitor.Visitor;

public class StmArrayAssign extends Stm {

    public final Exp e1, e2, e3;

    public StmArrayAssign(Exp ae1, Exp ae2, Exp ae3) {
        e1 = ae1;
        e2 = ae2;
        e3 = ae3;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
