package syntaxtree;

import visitor.Visitor;

public class ExpArrayLength extends Exp {

    public final Exp e;

    public ExpArrayLength(Exp ae) {
        e = ae;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
