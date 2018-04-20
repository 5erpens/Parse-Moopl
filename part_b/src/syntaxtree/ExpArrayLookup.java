package syntaxtree;

import visitor.Visitor;

public class ExpArrayLookup extends Exp {

    public final Exp e1, e2;

    public ExpArrayLookup(Exp ae1, Exp ae2) {
        e1 = ae1;
        e2 = ae2;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
