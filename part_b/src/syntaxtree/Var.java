package syntaxtree;

public class Var extends AST {

    public final String id;

    public Var(String aid) {
        id = aid;
    }
    
    /**
     * Assigned in a preliminary pass by the interpreter.
     * For a field, this is an offset into the MooplObject containing the field.
     * For a local/parameter, this is the stack frame offset (positive for
     * locals, negative for parameters).
     * @see interp.MooplObject
     * @see interp.MooplRunTime
     * @see interp.VarAllocator
     */
    public int offset;
    
    /**
     * Assigned in a preliminary pass by the interpreter.
     * Set to false for fields, true for locals/parameters.
     */
    public boolean isStackAllocated;
}
