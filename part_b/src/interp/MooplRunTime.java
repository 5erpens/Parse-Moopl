package interp;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import syntaxtree.Type;

/**
 * Run-time state for the Moopl interpreter, comprising heap and call-stack.
 */
public class MooplRunTime {
    
    public static int DEFAULT_STACK_SIZE = 1024 * 32;

    /**
     * The heap.
     */
    private Map<Integer, MooplObject> heap;

    /**
     * The call stack.
     */
    private int[] stack;

    /**
     * The next free address in the heap.
     */
    private int nextFree;
    
    /**
     * The call-stack stack pointer.
     * This is the "address" (array index) of the top-most stack position
     * which is currently allocated.
     * Set to -1 when the call-stack is empty.
     */
    private int sp;

    /**
     * The call-stack frame pointer.
     * This position on the stack holds the previous frame's frame pointer.
     * The position below this holds the previous frame's stack pointer.
     * Count down from here to access parameters.
     * Count up from here to access local variables.
     * Set to -1 when the call-stack is empty.
     */
    private int fp;

    /**
     * Initialise a new run-time state with default call-stack size.
     * Heap and call-stack are initially empty.
     */
    public MooplRunTime() {
        this(DEFAULT_STACK_SIZE);
    }

    /**
     * Initialise a new run-time state with a specified call-stack size.
     * Heap and call-stack are initially empty.
     * 
     * @param stackSize the call-stack size
     */
    public MooplRunTime(int stackSize) {
        heap = new HashMap<Integer, MooplObject>();
        nextFree = 1;
        stack = new int[stackSize];
        sp = -1;
        fp = -1;
    }

    /**
     * The arity of the current method invocation (excluding "self").
     *
     * @return the arity of the current method invocation
     */
    private int getArity() {
        return fp - (stack[fp - 1] + 3);
    }

    /**
     * Is the call stack empty?
     *
     * @return true if the call stack is empty
     */
    public boolean stackIsEmpty() {
        return sp < 0;
    }

    /**
     * Push a new stack frame on top of the call stack.
     * Allocates space for method parameters (including "self"), and local
     * variables. Initialises parameters.
     *
     * @param self the heap reference of the object which is the target of the
     * method call
     * @param actuals the actual parameters for the call
     * @param localsCount the number of slots to allocate for local variables
     *  (above the frame pointer)
     */
    public void pushFrame(Integer self, List<Integer> actuals, int localsCount) {
        int arity = actuals.size();
        int newFp = sp + arity + 3;
        int newSp = newFp + localsCount;
        if (newSp >= stack.length) {
            throw new MooplRunTimeException("Call-stack overflow!");
        }
        for (int i = 1; i <= arity; ++i) {
            stack[sp + i] = actuals.get(arity - i);
        }
        stack[newFp - 2] = self;
        stack[newFp - 1] = sp;
        stack[newFp] = fp;
        fp = newFp;
        sp = newSp;
    }

    /**
     * Pop the current stack frame off the call stack.
     */
    public void popFrame() {
        if (stackIsEmpty()) {
            throw new IllegalStateException("Attempt to pop frame off an empty call stack");
        }
        sp = stack[fp - 1];
        fp = stack[fp];
    }
    
    /**
     * Checks if an offset is legal for the current stack frame.
     * This is used only for debugging purposes.
     */
    private void checkOffset(int offset) {
        if (stackIsEmpty()) {
            throw new IllegalStateException("Attempt to access frame entry in an empty call stack");
        }
        int localsCount = sp - fp;
        int paramsCount = getArity() + 1;
        if (offset > localsCount) {
            throw new IllegalStateException("Frame offset too big. Current frame has " + localsCount + "local variable slots but offset = " + offset);
        } else if (-offset > paramsCount + 1) {
            throw new IllegalStateException("Frame offset too small. Current frame has " + paramsCount + "parameters (including self) but offset = " + offset);
        } else if (offset == -1 || offset == 0) {
            throw new IllegalStateException(offset + " is not a legal frame offset.");
        }
    }
    
    /**
     * Checks if an offset is legal for setting the value of a
     * stack-allocated variable in the current stack frame.
     * This is used only for debugging purposes.
     */
    private void checkSetOffset(int offset) {
        checkOffset(offset);
        if (offset == -2) {
            throw new IllegalStateException("Must not modify value of self (offset -2) after frame is pushed.");
        }
    }

    /**
     * Retrieve a value from the current stack frame.
     * Local variables are stored at offsets 1, 2, ...
     * Parameters (including "self") are stored at offsets -2, -3, ...
     * Note that the first parameter is "self" at offset -2 (offsets 0 and -1
     * should not be accessed via this method).
     *
     * @param offset an offset from the current frame pointer
     * @return the value stored at the specified offset
     */
    public int getFrameEntry(int offset) {
        checkOffset(offset); // for debugging purposes
        return stack[fp + offset];
    }

    /**
     * Set a value in the current stack frame.
     * Local variables are stored at offsets 1, 2, ...
     * Parameters (including "self") are stored at offsets -2, -3, ...
     * Note that the first parameter is "self" at offset -2 (offsets 0 and -1
     * should not be accessed via this method).
     *
     * @param offset an offset from the current frame pointer
     * @param value the new value
     */
    public void setFrameEntry(int offset, int value) {
        checkSetOffset(offset); // for debugging purposes
        stack[fp + offset] = value;
    }

    /**
     * Create a class instance object in the heap.
     * 
     * @param fieldCount how many fields the object will contain
     * @param className the name of the class of which to create an instance
     * @return the "memory address" of the new object in the heap
     */
    public Integer allocClassInstance(int fieldCount, String className) {
        int address = nextFree;
        nextFree++;
        MooplObject object = new MooplObject(fieldCount, className);
        heap.put(address, object);
        return address;
    }

    /**
     * Create a new array object in the heap.
     *
     * @param length the length of the array
     * @param elementType the type of the array elements
     * @return the "memory address" of the new object in the heap
     */
    public Integer allocArrayObject(int length, Type elementType) {
        int address = nextFree;
        nextFree++;
        MooplObject object = new MooplObject(length, elementType);
        heap.put(address, object);
        return address;
    }

    /**
     * Look up a reference in the heap and return the object found there.
     *
     * @param a the "memory address" of the object in the heap
     * @return the object found
     */
    public MooplObject deref(Integer a) {
        MooplObject o = heap.get(a);
        if (o == null) {
            throw new MooplRunTimeException("Panic! Invalid heap address: " + a);
        }
        return o;
    }
    
    /**
     * Print the top stack frame to stderr. For debugging purposes.
     */
    public void dumpTopFrame() {
        dumpFrame(sp, fp);
    }
    
    /**
     * Print the stack to stderr. For debugging purposes.
     */
    public void dumpStack() {
        System.err.println("=== STACK  TOP ===");
        int dsp = sp;
        int dfp = fp;
        while (dfp > 0) {
            dumpFrame(dsp, dfp);
            dsp = stack[dfp - 1];
            dfp = stack[dfp];
        }
        System.err.println("== STACK BOTTOM ==");
    }
    
    private void dumpFrame(int dsp, int dfp) {
        int localsCount = dsp - dfp;
        int arity = dfp - (stack[dfp - 1] + 3);
        System.err.println("== SP=" + dsp + " == FP=" + dfp + " ==");
        for (int i = localsCount; i >= 1; --i) {
            System.err.println("local " + i + " = " + stack[dfp+i]);
        }
        System.err.println("Prev FP = " + stack[dfp]);
        System.err.println("Prev SP = " + stack[dfp-1]);
        System.err.println("self    = " + stack[dfp-2]);
        for (int i = arity; i >= 1; --i) {
            System.err.println("param " + i + " = " + stack[dfp-(2+i)]);
        }
        System.err.println("==================");
    }
}
