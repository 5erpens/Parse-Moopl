package staticanalysis;

import syntaxtree.*;
import visitor.VisitorAdapter;

/**
 * Visitors for calculating the depth of loop nesting in Moopl programs.
 */
public class LoopDepth extends VisitorAdapter<Integer> {

    public LoopDepth() {}
    
    // List<ProcDecl> pds;
    // List<ClassDecl> cds;
    public Integer visit(Program n) {
        int depth = 0;
        for (ProcDecl pd : n.pds) {
            depth = Math.max(depth, pd.accept(this));
        }
        for (ClassDecl cd : n.cds) {
            depth = Math.max(depth, cd.accept(this));
        }
        return depth;
    }

    // String id;
    // List<FieldDecl> fds;
    // List<MethodDecl> mds;
    public Integer visit(ClassDeclSimple n) {
        int depth = 0;
        for (MethodDecl md : n.mds) {
            depth = Math.max(depth, md.accept(this));
        }
        return depth;
    }

    // String id;
    // String pid;
    // List<FieldDecl> fds;
    // List<MethodDecl> mds;
    public Integer visit(ClassDeclExtends n) {
        int depth = 0;
        for (MethodDecl md : n.mds) {
            depth = Math.max(depth, md.accept(this));
        }
        return depth;
    }

    // Integer t;
    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    // Exp e;
    public Integer visit(FunDecl n) {
        int depth = 0;
        for (Stm s : n.ss) {
            depth = Math.max(depth, s.accept(this));
        }
        return depth;
    }

    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    public Integer visit(ProcDecl n) {
        int depth = 0;
        for (Stm s : n.ss) {
            depth = Math.max(depth, s.accept(this));
        }
        return depth;
    }

    // List<Stm> ss;
    public Integer visit(StmBlock n) {
        int depth = 0;
        for (Stm s : n.ss) {
            depth = Math.max(depth, s.accept(this));
        }
        return depth;
    }
    
    // Exp e;
    // StmBlock b1,b2;
    public Integer visit(StmIf n) {
        return Math.max(n.b1.accept(this), n.b2.accept(this));
    }

    // Exp e;
    // StmBlock b;
    public Integer visit(StmWhile n) {
        return 1 + n.b.accept(this);
    }
    
    // Integer t
    // String id
    public Integer visit(StmVarDecl n) {
        return 0;
    }
    
    // Exp e;
    // String id;
    // List<Exp> es;
    public Integer visit(StmCall n) {
        return 0;
    }

    // Exp e;
    public Integer visit(StmOutput n) {
        return 0;
    }

    // Var v;
    // Exp e;
    public Integer visit(StmAssign n) {
        return 0;
    }

    // Exp e1,e2,e3;
    public Integer visit(StmArrayAssign n) {
        return 0;
    }
}
