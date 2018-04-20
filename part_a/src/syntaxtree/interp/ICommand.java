package syntaxtree.interp;

import visitor.Visitor;

/** Abstract supertype for interpreter command abstract syntax trees. */
public abstract class ICommand extends syntaxtree.AST {

    public abstract <T> T accept(Visitor<T> v);

}
