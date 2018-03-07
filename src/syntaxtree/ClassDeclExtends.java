package syntaxtree;

import java.util.List;
import visitor.Visitor;

public class ClassDeclExtends extends ClassDecl {

    public final String id;
    public final String pid;
    public final List<FieldDecl> fds;
    public final List<MethodDecl> mds;

    public ClassDeclExtends(String aid, String apid,
            List<FieldDecl> afds, List<MethodDecl> amds) {
        id = aid;
        pid = apid;
        fds = afds;
        mds = amds;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
