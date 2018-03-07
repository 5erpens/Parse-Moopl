package syntaxtree;

import visitor.Visitor;

public abstract class Exp extends AST {

    public abstract <T> T accept(Visitor<T> v);
}
