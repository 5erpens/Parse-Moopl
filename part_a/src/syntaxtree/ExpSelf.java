package syntaxtree;

import visitor.Visitor;

public class ExpSelf extends Exp {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
