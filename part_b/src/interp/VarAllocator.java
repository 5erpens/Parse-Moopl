package interp;

import java.util.LinkedList;
import java.util.List;
import staticanalysis.ClassSignature;
import staticanalysis.StaticAnalysisException;
import staticanalysis.SymbolTable;
import syntaxtree.*;
import syntaxtree.interp.ICall;
import syntaxtree.interp.IEval;
import visitor.VisitorAdapter;

/**
 * Classify all Vars as stack or heap allocated and annotate with the correct integer offsets.
 */
public class VarAllocator extends VisitorAdapter<Void> {

    SymbolTable symTab;

    String currentClassName;

    List<String> currentParameters, currentLocals;

    public VarAllocator(SymbolTable symTab) {
        this.symTab = symTab;
        currentClassName = null;
        currentParameters = new LinkedList<>();
        currentLocals = new LinkedList<>();
    }

    private void assignOffset(Var v) {
        if (currentParameters.contains(v.id)) {
            // v is a parameter
            v.isStackAllocated = true;
            v.offset = -(3 + currentParameters.indexOf(v.id));
        } else if (currentLocals.contains(v.id)) {
            // v is a local variable
            v.isStackAllocated = true;
            v.offset = 1 + currentLocals.indexOf(v.id);
        } else {
            // v is a field
            v.isStackAllocated = false;
            ClassSignature classSig = symTab.getClassSignature(currentClassName);
            v.offset = classSig.getImmediateFieldNames().lastIndexOf(v.id);
        }
    }

    // List<ProcDecl> pds;
    // List<ClassDecl> cds;
    public Void visit(Program n) {
        currentClassName = null;
        for (ProcDecl pd : n.pds) {
            pd.accept(this);
        }
        for (ClassDecl cd : n.cds) {
            cd.accept(this);
        }
        return null;
    }

    // String id;
    // List<MethodDecl> mds;
    public Void visit(ClassDeclSimple n) {
        currentClassName = n.id;
        for (MethodDecl md : n.mds) {
            md.accept(this);
        }
        return null;
    }

    // String id;
    // String pid;
    // List<FieldDecl> fds;
    // List<MethodDecl> mds;
    public Void visit(ClassDeclExtends n) {
        throw new StaticAnalysisException("Basic var allocator does not support inheritance", n.getTags());
    }

    // Type t;
    // String id;
    public Void visit(FieldDecl n) {
        return null;
    }

    // Type t;
    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    // Exp e;
    public Void visit(FunDecl n) {
        currentParameters.clear();
        currentLocals.clear();
        for (Formal f : n.fs) {
            currentParameters.add(f.id);
        }
        for (Stm s : n.ss) {
            s.accept(this);
        }
        n.e.accept(this);
        n.stackAllocation = currentLocals.size();
        return null;
    }

    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    public Void visit(ProcDecl n) {
        currentParameters.clear();
        currentLocals.clear();
        for (Formal f : n.fs) {
            currentParameters.add(f.id);
        }
        for (Stm s : n.ss) {
            s.accept(this);
        }
        n.stackAllocation = currentLocals.size();
        return null;
    }

    // List<Stm> ss;
    public Void visit(StmBlock n) {
        for (Stm s : n.ss) {
            s.accept(this);
        }
        return null;
    }

    // Type t;
    // String id;
    public Void visit(StmVarDecl n) {
        if (currentLocals.contains(n.id)) {
            throw new StaticAnalysisException("Basic variable allocator requires all local variables to have distinct names", n.getTags());
        }
        currentLocals.add(n.id);
        return null;
    }

    // Exp e;
    // StmBlock b1,b2;
    public Void visit(StmIf n) {
        n.e.accept(this);
        n.b1.accept(this);
        n.b2.accept(this);
        return null;
    }

    // Exp e;
    // StmBlock b;
    public Void visit(StmWhile n) {
        n.e.accept(this);
        n.b.accept(this);
        return null;
    }

    // Exp e;
    public Void visit(StmOutput n) {
        n.e.accept(this);
        return null;
    }

    // Var v;
    // Exp e;
    public Void visit(StmAssign n) {
        assignOffset(n.v);
        n.e.accept(this);
        return null;
    }

    // Exp e1,e2,e3;
    public Void visit(StmArrayAssign n) {
        n.e1.accept(this);
        n.e2.accept(this);
        n.e3.accept(this);
        return null;
    }

    // Exp e;
    // String id;
    // List<Exp> es;
    public Void visit(StmCall n) {
        n.e.accept(this);
        for (Exp e : n.es) {
            e.accept(this);
        }
        return null;
    }

    // Exp e1,e2;
    public Void visit(ExpArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    // Exp e;
    public Void visit(ExpArrayLength n) {
        n.e.accept(this);
        return null;
    }

    // Exp e;
    // String id;
    // List<Exp> es;
    public Void visit(ExpCall n) {
        n.e.accept(this);
        for (Exp e : n.es) {
            e.accept(this);
        }
        return null;
    }

    // int i;
    public Void visit(ExpInteger n) {
        return null;
    }

    public Void visit(ExpTrue n) {
        return null;
    }

    public Void visit(ExpFalse n) {
        return null;
    }

    // Var v;
    public Void visit(ExpVar n) {
        assignOffset(n.v);
        return null;
    }

    public Void visit(ExpSelf n) {
        return null;
    }

    // Type t;
    // Exp e;
    public Void visit(ExpNewArray n) {
        n.e.accept(this);
        return null;
    }

    // String id;
    // List<Exp> es;
    public Void visit(ExpNewObject n) {
        for (Exp e : n.es) {
            e.accept(this);
        }
        return null;
    }

    // Exp e;
    public Void visit(ExpNot n) {
        n.e.accept(this);
        return null;
    }

    // Exp e;
    public Void visit(ExpIsnull n) {
        n.e.accept(this);
        return null;
    }

    // Exp e1, e2;
    // ExpOp.Op op;
    public Void visit(ExpOp n) {
        n.e1.accept(this);
        n.e2.accept(this);
        return null;
    }

    /*===================*/
    /* ICommand visitors */
    /*===================*/
    // String id
    // List<Exp> es
    public Void visit(ICall n) {
        return null;
    }

    // Exp e
    public Void visit(IEval n) {
        return null;
    }

}
