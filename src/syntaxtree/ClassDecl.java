package syntaxtree;

import visitor.Visitor;

public abstract class ClassDecl extends AST {

    public abstract <T> T accept(Visitor<T> v);
    
}
