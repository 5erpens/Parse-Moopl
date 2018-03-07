package syntaxtree;

import visitor.Visitor;

public class StmIf extends Stm {

    public final Exp e;
    public final StmBlock b1, b2;

    public StmIf(Exp ae, StmBlock ab1, StmBlock ab2) {
        e = ae;
        b1 = ab1;
        b2 = ab2;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
