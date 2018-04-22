package interp;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import staticanalysis.ClassSignature;
import staticanalysis.MethodSignature;
import visitor.VisitorAdapter;
import staticanalysis.SymbolTable;
import syntaxtree.*;
import syntaxtree.interp.*;

/**
 * Interpreter visitors.
 *
 * Expressions of all types evaluate to an Integer, with the following meanings for different types:
 *
 * int: the value of the expression
 *
 * boolean: 0 for false, 1 for true
 *
 * class and array types: the "memory address" for an object in the heap (in fact, the key under which the object is stored in the map representing the heap).
 *
 * Statements all evaluate to null, with side-effects on the Moopl run-time.
 *
 * @see MooplRunTime
 */
public class Interpreter extends VisitorAdapter<Integer> {

    /**
     * The symbol table.
     */
    private SymbolTable symTab;

    /**
     * The current run-time state.
     */
    private MooplRunTime mooplRunTime;

    /**
     * Initialise a new interpreter.
     *
     * @param symTab the symbol table which contains the class and method signatures
     */
    public Interpreter(SymbolTable symTab) {
        this.symTab = symTab;
        mooplRunTime = new MooplRunTime();
    }

    /*
     The FunDecl and ProcDecl visit methods allow us to process a method call
     by setting up the call stack and then simply visiting the method declaration.
     @see visit(ICall)
     @see visit(StmCall)
     @see visit(ExpCall)
     @see visit(ExpNewObject)
     */
    // Type t;
    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    // Exp e;
    public Integer visit(FunDecl n) {
        for (Stm s : n.ss) {
            s.accept(this);
        }
        return n.e.accept(this);
    }

    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    public Integer visit(ProcDecl n) {
        for (Stm s : n.ss) {
            s.accept(this);
        }
        return null;
    }

    //All overrtide methods
    /*=============================================*/
 /* Expression visitors (all return an Integer) */
 /*=============================================*/
    // TODO
    // integer literals
    @Override
    public Integer visit(ExpInteger n) {
        return n.i; // all expressions return an integer (see class header)
    }

    @Override
    public Integer visit(StmIf s) {
        int count = s.e.accept(this);

        if (count == 1) {
            s.b1.accept(this);
        } else {
            s.b2.accept(this);
        }
        return null;
        
    }

    @Override
    public Integer visit(IQuit n) {
        return super.visit(n); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer visit(ExpOp n) {
        switch (n.op) {
            case DIV:
                return n.e1.accept(this) / n.e2.accept(this);
            case PLUS:
                return n.e1.accept(this) + n.e2.accept(this);
            case MINUS:
                return n.e1.accept(this) - n.e2.accept(this);
            case TIMES:
                return n.e1.accept(this) * n.e2.accept(this);
            case AND:
                if (n.e1.accept(this) + n.e2.accept(this) == 2) {
                    return 1;
                } else {
                    return 0;
                }
            case LESSTHAN:
                if (n.e1.accept(this) < n.e2.accept(this)) {
                    return 1;
                } else {
                    return 0;
                }
            case EQUALS:
                if (Objects.equals(n.e1.accept(this), n.e2.accept(this))) {
                    return 1;
                } else {
                    return 0;
                }
        }
        return 0;
    }

    @Override
    public Integer visit(ExpIsnull n) {
        if (n.e.accept(this) == null) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Integer visit(ExpNot n) {
        if (n.e.equals(1)) {
            return 0;
        } else {
            return 1;
        }
    }

    //todo
    @Override
    public Integer visit(ExpNewObject n) {
        int a = mooplRunTime.allocClassInstance(symTab.getClassSignature(n.id).getImmediateFieldCount(),n.id);
        List<Integer> Parameter = new LinkedList<>();
        for (Exp e:n.es){
            Parameter.add(e.accept(this));
        }
        mooplRunTime.pushFrame(a,Parameter,symTab.getClassSignature(n.id).getMethodSignature(n.id).getMethodDecl().stackAllocation);
        symTab.getClassSignature(n.id).getMethodSignature(n.id).getMethodDecl().accept(this);
        mooplRunTime.popFrame();

        return a;
    }

    @Override
    public Integer visit(ExpNewArray n) {
        return mooplRunTime.allocArrayObject(n.e.accept(this), n.t);
    }

    @Override
    public Integer visit(ExpSelf n) {
        return mooplRunTime.getFrameEntry(-2);
    }

    @Override
    public Integer visit(ExpVar n) {
        if (!n.v.isStackAllocated) {
            return mooplRunTime.deref(mooplRunTime.getFrameEntry(-2)).elements[n.v.offset];
        } else {
            return mooplRunTime.getFrameEntry(n.v.offset);
        }
    }

    @Override
    public Integer visit(ExpFalse n) {
        return 0;
    }

    @Override
    public Integer visit(ExpTrue n) {
        return 1;
    }

    @Override
    public Integer visit(ExpCall n) {
        int acc = n.e.accept(this);

        LinkedList<Integer> list = new LinkedList<>();

        for (Exp exp : n.es) {
            list.add(exp.accept(this));
        }

        MethodDecl mdl = symTab.getClassSignature(mooplRunTime.deref(acc).type.toString()).getMethodSignature(n.id).getMethodDecl();
        mooplRunTime.pushFrame(acc, list, mdl.stackAllocation);
       
        int i = mdl.accept(this);
        mooplRunTime.popFrame();
        return i;
    }

    @Override
    public Integer visit(ExpArrayLength n) {
        return mooplRunTime.deref(n.e.accept(this)).elements.length;
    }

    @Override
    public Integer visit(ExpArrayLookup n) {
        return mooplRunTime.deref(n.e1.accept(this)).elements[n.e2.accept(this)];
    }

    /*======================================*/
 /* Statement visitors (all return null) */
 /*======================================*/
    // TODO
    // output statements
    @Override
    public Integer visit(StmOutput n) {
        int v = n.e.accept(this);
        System.out.println(v);
        return null; // statements all return null
    }

    @Override
    public Integer visit(StmCall n) {
        int acc = n.e.accept(this);
        LinkedList<Integer> list = new LinkedList<>();

        for (Exp e : n.es) {
            list.add(e.accept(this));
        }

        MethodDecl mdl = symTab.getClassSignature(mooplRunTime.deref(acc).type.toString()).getMethodSignature(n.id).getMethodDecl();
        mooplRunTime.pushFrame(acc, list, mdl.stackAllocation);

        for (Stm s : mdl.ss) {
            s.accept(this);
        }
        mooplRunTime.popFrame();
        return null;

    }

    @Override
    public Integer visit(StmArrayAssign n) {
        mooplRunTime.deref(n.e1.accept(this)).elements[n.e2.accept(this)] = n.e3.accept(this);
        return null;
    }

    @Override
    public Integer visit(StmAssign n) {
        if (!n.v.isStackAllocated) {
            mooplRunTime.deref(mooplRunTime.getFrameEntry(-2)).elements[n.v.offset] = n.e.accept(this);
        } else {
            mooplRunTime.setFrameEntry(n.v.offset, n.e.accept(this));
        }
        return null;
    }

    @Override
    public Integer visit(StmWhile n) {
        //todo
        if (n.e.accept(this) == 1) {
            n.b.accept(this);
        }
        return null;
    }

    @Override
    public Integer visit(StmVarDecl n) {
        return null;
    }

    @Override
    public Integer visit(StmBlock n) {
        LinkedList<Integer> list = new LinkedList<>();
        for (Stm s : n.ss) {
            list.add(s.accept(this));
        }
        return null;
    }

    /*=====================================*/
 /* ICommand visitors (all return null) */
 /*=====================================*/
    // String id
    // List<Exp> es
    public Integer visit(ICall n) {
        // n.id is the name of a top-level procedure
        // n.es is the list of actual-parameter expressions for this call

        // evaluate the actual-parameter expressions
        List<Integer> actualValues = new LinkedList<Integer>();
        for (Exp e : n.es) {
            actualValues.add(e.accept(this));
        }

        // look up the top-level procedure signature in the dummy top-level
        // class (class name null)
        ClassSignature dummyClassSig = symTab.getClassSignature(null);
        MethodSignature procSig = dummyClassSig.getMethodSignature(n.id);

        // find the "code" (MethodDecl) for the procedure
        MethodDecl procCode = procSig.getMethodDecl();

        // push a new stack frame on the call-stack
        // there is no "self" for a top-level proc, so we pass 0 as an
        // appropriate dummy value (it will never be used)
        mooplRunTime.pushFrame(0, actualValues, procCode.stackAllocation);

        // execute the procedure code
        // @see visit(ProcDecl)
        procCode.accept(this);

        // method call completed, so pop the frame off the call-stack
        mooplRunTime.popFrame();

        // procedures don't return a value, so we return null
        return null;
    }

    // Exp e
    public Integer visit(IEval n) {
        System.out.println(n.e.accept(this));
        return null;
    }
}
