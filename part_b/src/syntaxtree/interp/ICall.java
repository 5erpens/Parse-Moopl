package syntaxtree.interp;

import java.util.List;
import syntaxtree.Exp;
import visitor.Visitor;

public class ICall extends ICommand {
    
    public final String id;
    public final List<Exp> es;
    
    public ICall(String id, List<Exp> es) {
        this.id = id;
        this.es = es;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
    
}
