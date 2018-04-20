package interp;

import java.util.LinkedList;
import java.util.List;
import staticanalysis.ClassSignature;
import staticanalysis.MethodSignature;
import visitor.VisitorAdapter;
import staticanalysis.SymbolTable;
import syntaxtree.*;
import syntaxtree.interp.*;

/**
 * Interpreter visitors.
 *
 * Expressions of all types evaluate to an Integer, with the following meanings
 * for different types:
 *
 * int: the value of the expression
 *
 * boolean: 0 for false, 1 for true
 *
 * class and array types: the "memory address" for an object in the heap (in
 * fact, the key under which the object is stored in the map representing the
 * heap).
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
     * @param symTab the symbol table which contains the class and method
     * signatures
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

    /*=============================================*/
    /* Expression visitors (all return an Integer) */
    /*=============================================*/
    // TODO

    /*======================================*/
    /* Statement visitors (all return null) */
    /*======================================*/
    // TODO

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
