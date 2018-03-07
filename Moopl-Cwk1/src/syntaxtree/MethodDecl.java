package syntaxtree;

import java.util.List;
import visitor.Visitor;

public abstract class MethodDecl extends AST {

    public final String id;
    public final List<Formal> fs;
    public final List<Stm> ss;

    protected MethodDecl(String aid, List<Formal> afs, List<Stm> ass) {
        id = aid;
        fs = afs;
        ss = ass;
    }

    public abstract <T> T accept(Visitor<T> v);
}
