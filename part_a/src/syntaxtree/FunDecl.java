package syntaxtree;

import java.util.List;
import visitor.Visitor;

public class FunDecl extends MethodDecl {

    public final Type t;
    public final Exp e;

    public FunDecl(Type at, String id, List<Formal> fs, List<Stm> ss, Exp ae) {
        super(id, fs, ss);
        t = at;
        e = ae;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
