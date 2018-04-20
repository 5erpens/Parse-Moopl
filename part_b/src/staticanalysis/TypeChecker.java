package staticanalysis;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import syntaxtree.*;
import syntaxtree.interp.*;
import visitor.VisitorAdapter;

/**
 * Visitors for type checking Moopl programs.
 */
public class TypeChecker extends VisitorAdapter<Type> {

    private static final Type TYPE_BOOLEAN = new TypeBoolean();
    private static final Type TYPE_INT = new TypeInt();

    /**
     * A symbol table of class and method signatures.
     */
    private SymbolTable symbolTable;
    
    /**
     * The name of the class currently being type checked.
     */
    private String currentClassName;
    
    /**
     * A stack of tables mapping the local variables/parameters currently in
     * scope to their types.
     */
    private Deque<Map<String, Type>> locals;

    /**
     * Initialise a new type check visitor.
     *
     * @param s the symbol table to use during type checking
     */
    public TypeChecker(SymbolTable s) {
        symbolTable = s;
        currentClassName = null;
        locals = new LinkedList<>();
    }

    /**
     * ***************
     */
    /* Helper methods */
    /**
     * ***************
     */
    /**
     *
     */
    private void pushLocals() {
        locals.push(new HashMap<String, Type>());
    }

    /**
     * Exit the current local variable scope.
     */
    private void popLocals() {
        locals.pop();
    }

    /**
     * Add an entry to the table of local variables/parameters currently in
     * scope.
     *
     * @param name the name of the local variable/parameter
     * @param type the type
     * @param tags tags for error reporting
     *
     * @throws TypeCheckingException if a local variable/parameter entry with
     * the same name already exists in the closest current scope
     */
    private void addLocal(String name, Type type, List<String> tags) {
        // we check only if name is already declared in the <b>closest</b> scope
        // region so (unlike Java) we allow a local variable declaration
        // in an inner block to hide a declaration of the same name in
        // an enclosing outer block
        if (locals.peek().containsKey(name)) {
            throw new TypeCheckingException("Double declaration of local var/param: " + name, tags);
        }
        locals.peek().put(name, type);
    }

    /**
     * Lookup the type for a variable. Start by checking if it is a local
     * variable/parameter. If that fails, check if it is a field in the current
     * class.
     *
     * @param name the variable name
     * @return the type of the variable
     * @throws TypeCheckingException if the variable is not in scope
     */
    private Type getTypeForVar(Var v) {
        Type t;
        String name = v.id;
        for (Map<String, Type> scope : locals) {
            t = scope.get(name);
            if (t != null) {
                return t;
            }
        }
        ClassSignature currentClassSig = symbolTable.getClassSignature(currentClassName);
        t = currentClassSig.getFieldType(name);
        if (t != null) {
            return t;
        }
        throw new TypeCheckingException("No declaration found for variable: " + name, v.getTags());
    }

    /**
     * Check if a type is defined. All primitive types are defined.
     * A TypeClassType type is defined iff the named class has an entry in the
     * symbol table.
     * An Array type is defined iff its element type is defined.
     *
     * @param t the type to check
     * @throws TypeCheckingException if t is (or contains) a TypeClassType and
     * the named class does not have an entry in the symbol table
     */
    private void checkIsDefinedType(Type t) {
        if (t instanceof TypeClassType) {
            String className = ((TypeClassType) t).id;
            if (!symbolTable.classDeclarationIsInScope(className)) {
                throw new TypeCheckingException("Undefined class type: " + className, t.getTags());
            }
        } else if (t instanceof TypeArray) {
            checkIsDefinedType(((TypeArray) t).t);
        }
    }
    
    // check that target has Object type
    private String findCallTargetClass(Exp target) {
        Type targetType = target.accept(this);
        if (!(targetType instanceof TypeClassType)) {
            throw new TypeCheckingException("Method called on something that is not an object", target.getTags());
        }
        return ((TypeClassType) targetType).id;
    }
    
    private Type checkCall(List<String> tags, String className, String methodName, List<Exp> actuals) {
        String msgName = methodName;
        if (className != null) msgName = className + "." + msgName;
        
        // check that target class does provide named method
        ClassSignature classSig = symbolTable.getClassSignature(className);
        MethodSignature methodSig = classSig.getMethodSignature(methodName);
        if (methodSig == null) {
            throw new TypeCheckingException("Method " + msgName + " is not defined", tags);
        }

        // check that arities match
        int arity = methodSig.getArity();
        if (arity != actuals.size()) {
            throw new TypeCheckingException("Method " + msgName
                    + " called with wrong number of parameters"
                    + " (" + actuals.size() + " instead of " + arity + ")", tags);
        }

        // check actual parameter types against formal parameter types
        for (int i = 0; i < arity; i++) {
            Type t1 = methodSig.getParamType(i);
            Type t2 = actuals.get(i).accept(this);
            if (!symbolTable.isSupertype(t1, t2)) {
                throw new TypeCheckingException(ordinalString(i + 1) + " actual parameter has wrong type for "
                        + msgName, actuals.get(i).getTags());
            }
        }

        // if we get this far, it is a valid method call
        return methodSig.getReturnType();
    }
    
    private String ordinalString(int i) {
        switch (i) {
            case 11: case 12: case 13:
                return i + "th";
            default:
                switch (i % 10) {
                    case 1:
                        return i + "st";
                    case 2:
                        return i + "nd";
                    case 3:
                        return i + "rd";
                    default:
                        return i + "th";
                }
        }
    }

    /**
     * *****************
     */
    /* Visitor methods. */
    /**
     * *****************
     */
    
    // List<ProcDecl> pds;
    // List<ClassDecl> cds;
    public Type visit(Program n) {
        currentClassName = null; // null  is the "name" for the dummy top-level class
        for (ProcDecl pd : n.pds) {
            pd.accept(this);
        }
        for (ClassDecl cd : n.cds) {
            cd.accept(this);
        }
        return null;
    }

    // String id;
    // List<FieldDecl> fds;
    // List<MethodDecl> mds;
    public Type visit(ClassDeclSimple n) {
        currentClassName = n.id;
        for (FieldDecl fd : n.fds) {
            fd.accept(this);
        }
        for (MethodDecl md : n.mds) {
            md.accept(this);
        }
        return null;
    }

    // String id;
    // String pid;
    // List<FieldDecl> fds;
    // List<MethodDecl> mds;
    public Type visit(ClassDeclExtends n) {
        throw new StaticAnalysisException("Basic type checker does not support inheritance.", n.getTags());
    }

    // Type t;
    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    // Exp e;
    public Type visit(FunDecl n) {
        // open new variable scope for the parameters
        pushLocals();
        for (Formal f : n.fs) {
            f.accept(this);
            addLocal(f.id, f.t, f.getTags());
        }
        // open new variable scope for the local variables
        pushLocals();
        // check the body statements
        for (Stm s : n.ss) {
            s.accept(this);
        }
        // check the return expression
        Type retType = n.t;
        checkIsDefinedType(retType);
        if (!symbolTable.isSupertype(retType, n.e.accept(this))) {
            throw new TypeCheckingException("Return expression has wrong type for method " + n.id, n.e.getTags());
        }
        popLocals(); // close local variable scope
        popLocals(); // close parameter scope
        return null;
    }

    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    public Type visit(ProcDecl n) {
        // open new variable scope for the parameters
        pushLocals();
        for (Formal f : n.fs) {
            f.accept(this);
            addLocal(f.id, f.t, f.getTags());
        }
        // open new variable scope for the local variables
        pushLocals();
        // check the body statements
        for (Stm s : n.ss) {
            s.accept(this);
        }
        popLocals(); // close local variable scope
        popLocals(); // close parameter scope
        return null;
    }

    // Type t;
    // String id;
    public Type visit(FieldDecl n) {
        checkIsDefinedType(n.t);
        return null;
    }

    // Type t;
    // String id;
    public Type visit(Formal n) {
        checkIsDefinedType(n.t);
        return null;
    }

    /*======================================*/
    /* Statement visitors (all return null) */
    /*======================================*/
    
    // Type t
    // String id
    public Type visit(StmVarDecl n) {
        checkIsDefinedType(n.t);
        addLocal(n.id, n.t, n.getTags());
        return null;
    }
    
    // Exp e;
    // String id;
    // List<Exp> es;
    public Type visit(StmCall n) {
        String cname = findCallTargetClass(n.e);
        checkCall(n.getTags(), cname, n.id, n.es);
        return null;
    }
    
    // Exp e;
    // StmBlock b1,b2;
    public Type visit(StmIf n) {
        if (!(n.e.accept(this).equals(TYPE_BOOLEAN))) {
            throw new TypeCheckingException("The condition of if must be of type boolean", n.e.getTags());
        }
        n.b1.accept(this);
        n.b2.accept(this);
        return null;
    }

    // Exp e;
    // StmBlock b;
    public Type visit(StmWhile n) {
        if (!(n.e.accept(this).equals(TYPE_BOOLEAN))) {
            throw new TypeCheckingException("The condition of while must be of type boolean", n.e.getTags());
        }
        n.b.accept(this);
        return null;
    }

    // Exp e;
    public Type visit(StmOutput n) {
        if (!(n.e.accept(this).equals(TYPE_INT))) {
            throw new TypeCheckingException("The argument of output must be of type int", n.e.getTags());
        }
        return null;
    }

    // Var v;
    // Exp e;
    public Type visit(StmAssign n) {
        Type t1 = getTypeForVar(n.v);
        Type t2 = n.e.accept(this);
        if (!symbolTable.isSupertype(t1, t2)) {
            throw new TypeCheckingException("Type error in assignment to " + n.v.id, n.e.getTags());
        }
        return null;
    }

    // Exp e1,e2,e3;
    public Type visit(StmArrayAssign n) {
        Type arrayType = n.e1.accept(this);
        if (!(arrayType instanceof TypeArray)) {
            throw new TypeCheckingException("The array expression in an array assignment"
                    + " must be of array type", n.e1.getTags());
        }
        Type elementType = ((TypeArray)arrayType).t;
        if (!(n.e2.accept(this).equals(TYPE_INT))) {
            throw new TypeCheckingException("The index expression in an array assignment"
                    + " must be of type int", n.e2.getTags());
        }
        Type rhsType = n.e3.accept(this);
        if (!symbolTable.isSupertype(elementType, rhsType)) {
            throw new TypeCheckingException("The type of the rhs expression in an array assignment"
                    + " must be a subtype of the array element type", n.e3.getTags());
        }
        return null;
    }

    // List<Stm> ss;
    public Type visit(StmBlock n) {
        pushLocals();
        for (Stm s : n.ss) {
            s.accept(this);
        }
        popLocals();
        return null;
    }

    /*=========================================*/
    /* Expression visitors (all return a Type) */
    /*=========================================*/
    
    // Exp e1, e2;
    // ExpOp.Op op;
    public Type visit(ExpOp n) {
        Type resType;
        switch (n.op) {
            case DIV:
            case PLUS:
            case MINUS:
            case TIMES:
                resType = TYPE_INT;
                break;
            case AND:
            case LESSTHAN:
            case EQUALS:
                resType = TYPE_BOOLEAN;
                break;
            default:
                throw new Error("Unknown operator: " + n.op);
        }
        String errmsg = null;
        Exp keyExp = n;
        switch (n.op) {
            case DIV:
            case PLUS:
            case MINUS:
            case TIMES:
            case LESSTHAN:
                if (!(n.e1.accept(this).equals(TYPE_INT))) {
                    errmsg = "First argument of " + n.op + " must be of type int";
                    keyExp = n.e1;
                } else if (!(n.e2.accept(this).equals(TYPE_INT))) {
                    errmsg = "Second argument of " + n.op + " must be of type int";
                    keyExp = n.e2;
                }
                break;
            case AND:
                if (!(n.e1.accept(this).equals(TYPE_BOOLEAN))) {
                    errmsg = "First argument of " + n.op + " must be of type boolean";
                    keyExp = n.e1;
                } else if (!(n.e2.accept(this).equals(TYPE_BOOLEAN))) {
                    errmsg = "Second argument of " + n.op + " must be of type boolean";
                    keyExp = n.e2;
                }
                break;
            case EQUALS:
                Type t1 = n.e1.accept(this);
                Type t2 = n.e2.accept(this);
                if (!symbolTable.isSupertype(t1, t2) && !symbolTable.isSupertype(t2,t1)) {
                    errmsg = "Arguments to == must have a common subtype";
                }
                break;
            default:
                throw new Error("unknown operator: " + n.op);
        }
        if (errmsg == null) {
            return resType;
        } else {
            throw new TypeCheckingException(errmsg, keyExp.getTags());
        }
    }

    // Exp e1,e2;
    public Type visit(ExpArrayLookup n) {
        Type arrayType = n.e1.accept(this);
        if (!(arrayType instanceof TypeArray)) {
            throw new TypeCheckingException("Target of an array lookup must be of array type", n.e1.getTags());
        }
        if (!(n.e2.accept(this).equals(TYPE_INT))) {
            throw new TypeCheckingException("ArrayLookup index must be of type integer", n.e2.getTags());
        }
        return ((TypeArray)arrayType).t;
    }

    // Exp e;
    public Type visit(ExpArrayLength n) {
        if (!(n.e.accept(this) instanceof TypeArray)) {
            throw new TypeCheckingException("Target of array length must of array type", n.e.getTags());
        }
        return TYPE_INT;
    }

    // Exp e;
    // String id;
    // List<Exp> es;
    public Type visit(ExpCall n) {
        String cname = findCallTargetClass(n.e);
        Type retType = checkCall(n.getTags(), cname, n.id, n.es);
        if (retType == null) {
            throw new TypeCheckingException("Proc " + n.id + " cannot be called here (it's no fun)", n.getTags());
        }
        return retType;
    }

    // int i;
    public Type visit(ExpInteger n) {
        return TYPE_INT;
    }

    public Type visit(ExpTrue n) {
        return TYPE_BOOLEAN;
    }

    public Type visit(ExpFalse n) {
        return TYPE_BOOLEAN;
    }

    // Var v;
    public Type visit(ExpVar n) {
        return getTypeForVar(n.v);
    }

    public Type visit(ExpSelf n) {
        if (currentClassName == null) {
            throw new TypeCheckingException("Cannot use \"self\" in a top-level method.", n.getTags());
        }
        return new TypeClassType(currentClassName);
    }

    // Type t;
    // Exp e;
    public Type visit(ExpNewArray n) {
        Type elementType = n.t;
        if (!(n.e.accept(this).equals(TYPE_INT))) {
            throw new TypeCheckingException("Size expression in array creation must be of type integer", n.e.getTags());
        }
        return new TypeArray(elementType);
    }

    // String id;
    // List<Exp> es;
    public Type visit(ExpNewObject n) {
        if (!symbolTable.classDeclarationIsInScope(n.id)) {
            throw new TypeCheckingException("Class " + n.id + " does not exist.", n.getTags());
        }
        checkCall(n.getTags(), n.id, n.id, n.es);
        return new TypeClassType(n.id);
    }

    // Exp e;
    public Type visit(ExpNot n) {
        if (!(n.e.accept(this).equals(TYPE_BOOLEAN))) {
            throw new TypeCheckingException("Negated expression must be of type boolean", n.e.getTags());
        }
        return TYPE_BOOLEAN;
    }

    // Exp e;
    public Type visit(ExpIsnull n) {
        Type objectType = n.e.accept(this);
        if (!(objectType instanceof TypeClassType) && !(objectType instanceof TypeArray)) {
            throw new TypeCheckingException("Argument of isnull must be of object type", n.e.getTags());
        }
        return TYPE_BOOLEAN;
    }

    /*=====================================*/
    /* ICommand visitors (all return null) */
    /*=====================================*/
    
    
    // String id
    // List<Exp> es
    public Type visit(ICall n) {
        checkCall(n.getTags(), null, n.id, n.es);
        return null;
    }
    
    // Exp e
    public Type visit(IEval n) {
        n.e.accept(this);
        return null;
    }
}
