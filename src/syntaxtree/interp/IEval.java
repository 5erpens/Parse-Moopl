package syntaxtree.interp;

import syntaxtree.Exp;
import visitor.Visitor;

public class IEval extends ICommand {
    
    public Exp e;
    
    public IEval(Exp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
    
}
