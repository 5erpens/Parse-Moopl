package syntaxtree;

import visitor.Visitor;

public class StmAssign extends Stm {

    public final Var v;
    public final Exp e;

    public StmAssign(Var av, Exp ae) {
        v = av;
        e = ae;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
