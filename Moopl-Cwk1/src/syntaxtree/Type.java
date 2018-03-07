package syntaxtree;

import visitor.Visitor;

public abstract class Type extends AST {

    public abstract <T> T accept(Visitor<T> v);
}
