package staticanalysis;

import java.util.ArrayList;
import syntaxtree.*;
import java.util.List;
import visitor.VisitorAdapter;

/**
 * Visitors which build a symbol table for a Moopl AST.
 */
public class SymbolTableBuilder extends VisitorAdapter<Void> {

    private SymbolTable symbolTable;
    private ClassSignature currentClass;

    /**
     * Initialise a new symbol table builder.
     */
    public SymbolTableBuilder() {
        symbolTable = new SymbolTable();
    }

    /**
     * The symbol table which has been built so far.
     * @return the symbol table
     */
    public SymbolTable getSymTab() {
        return symbolTable;
    }

    // List<ProcDecl> pds;
    // List<ClassDecl> cds;
    public Void visit(Program n) {
        // add a dummy top-level class signature to contain signatures
        // for the top-level procedures
        if (!symbolTable.addClass(null, null)) {
            // this is probably impossible
            throw new StaticAnalysisException("Double definition for dummy top-level class!", n.getTags());
        }
        currentClass = symbolTable.getClassSignature(null);
        for (ProcDecl pd : n.pds) {
            pd.accept(this);
        }
        currentClass = null;
        for (ClassDecl cd : n.cds) {
            cd.accept(this);
        }
        symbolTable.checkInheritanceHierarchy();
        return null;
    }

    // String id;
    // List<FieldDecl> fds;
    // List<MethodDecl> mds;
    public Void visit(ClassDeclSimple n) {
        if (!symbolTable.addClass(n.id, null)) {
            throw new StaticAnalysisException("Double definition for class " + n.id, n.getTags());
        }
        currentClass = symbolTable.getClassSignature(n.id);
        processClassBody(n.fds, n.mds);
        currentClass = null;
        return null;
    }

    // String id;
    // String pid;
    // List<FieldDecl> fds;
    // List<MethodDecl> mds;
    public Void visit(ClassDeclExtends n) {
        if (!symbolTable.addClass(n.id, n.pid)) {
            throw new StaticAnalysisException("Double definition for class " + n.id, n.getTags());
        }
        currentClass = symbolTable.getClassSignature(n.id);
        processClassBody(n.fds, n.mds);
        currentClass = null;
        return null;
    }
    
    private void processClassBody(List<FieldDecl> fds, List<MethodDecl> mds) {
        for (FieldDecl fd : fds) {
            boolean ok = currentClass.addField(fd.id, fd.t);
            if (!ok) {
                throw new StaticAnalysisException("Field " + fd.id
                    + " is already defined in " + reportStringForCurrentClass(), fd.getTags());
            }
        }
        for (MethodDecl md : mds) {
            md.accept(this);
        }
    }

    // Type t;
    // String id;
    // List<Formal> fds;
    // List<Stm> ss;
    // Exp e;
    public Void visit(FunDecl n) {
        String methodName = n.id;
        List<Type> formalTypes = new ArrayList<Type>();
        for (Formal f : n.fs) {
            formalTypes.add(f.t);
        }
        if (!currentClass.addMethod(methodName, n.t, formalTypes, n)) {
            throw new StaticAnalysisException("Method " + methodName
                    + " is already defined in " + reportStringForCurrentClass(), n.getTags());
        }
        return null;
    }

    // String id;
    // List<Formal> fds;
    // List<Stm> ss;
    public Void visit(ProcDecl n) {
        String methodName = n.id;
        List<Type> formalTypes = new ArrayList<Type>();
        for (Formal f : n.fs) {
            formalTypes.add(f.t);
        }
        if (!currentClass.addMethod(methodName, null, formalTypes, n)) {
            throw new StaticAnalysisException("Method " + methodName
                    + " is already defined in " + reportStringForCurrentClass(), n.getTags());
        }
        return null;
    }
    
    private String reportStringForCurrentClass() {
        String msg = currentClass.getName();
        if (msg == null) {
            msg = "dummy top-level class";
        } else {
            msg = "class " + msg;
        }
        return msg;
    }
}
