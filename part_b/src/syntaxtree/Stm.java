package syntaxtree;

import visitor.Visitor;

public abstract class Stm extends AST {

    public abstract <T> T accept(Visitor<T> v);
}
