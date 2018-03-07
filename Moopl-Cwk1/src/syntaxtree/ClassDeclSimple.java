package syntaxtree;

import java.util.List;
import visitor.Visitor;

public class ClassDeclSimple extends ClassDecl {

    public final String id;
    public final List<FieldDecl> fds;
    public final List<MethodDecl> mds;

    public ClassDeclSimple(String aid, List<FieldDecl> afds, List<MethodDecl> amds) {
        id = aid;
        fds = afds;
        mds = amds;
    }
    
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
