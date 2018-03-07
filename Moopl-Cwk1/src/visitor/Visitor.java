package visitor;

import syntaxtree.*;
import syntaxtree.interp.*;

public interface Visitor<T> {

    public T visit(Program n);

    public T visit(ClassDeclSimple n);

    public T visit(ClassDeclExtends n);

    public T visit(FieldDecl n);

    public T visit(ProcDecl n);

    public T visit(FunDecl n);

    public T visit(Formal n);

    public T visit(TypeArray n);

    public T visit(TypeBoolean n);

    public T visit(TypeInt n);

    public T visit(TypeClassType n);

    public T visit(StmBlock n);

    public T visit(StmVarDecl n);

    public T visit(StmIf n);

    public T visit(StmWhile n);

    public T visit(StmOutput n);

    public T visit(StmAssign n);

    public T visit(StmArrayAssign n);

    public T visit(StmCall n);

    public T visit(ExpArrayLookup n);

    public T visit(ExpArrayLength n);

    public T visit(ExpCall n);

    public T visit(ExpInteger n);

    public T visit(ExpTrue n);

    public T visit(ExpFalse n);

    public T visit(ExpVar n);

    public T visit(ExpSelf n);

    public T visit(ExpNewArray n);

    public T visit(ExpNewObject n);

    public T visit(ExpNot n);

    public T visit(ExpIsnull n);
    
    public T visit(ExpOp n);
    
    public T visit(ICall n);
    
    public T visit(IEval n);
    
    public T visit(IQuit n);
}
