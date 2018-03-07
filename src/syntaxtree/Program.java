package syntaxtree;

import java.util.List;
import visitor.Visitor;

public class Program extends AST {

    public final List<ProcDecl> pds;
    public final List<ClassDecl> cds;

    public Program(List<ProcDecl> apds, List<ClassDecl> acds) {
        pds = apds;
        cds = acds;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
