package syntaxtree;

import visitor.Visitor;

public class ExpInteger extends Exp {

    public final int i;

    public ExpInteger(int ai) {
        i = ai;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
