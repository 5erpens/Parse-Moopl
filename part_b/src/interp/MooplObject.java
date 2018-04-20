package interp;

import staticanalysis.SymbolTable;
import syntaxtree.Type;
import syntaxtree.TypeArray;
import syntaxtree.TypeClassType;

/*
 * Heap allocated objects in the Moopl interpreter.
 */
public class MooplObject {
    
    /** The elements (field values or array elements) of this object. */
    public final int[] elements;
    
    /** The type of which this object is an instance. */
    public final Type type;

    /**
     * Initialise a new class instance object.
     * @param fieldCount how many fields this object contains
     * @param className the name of the class of which this object is an
     *  instance
     */
    public MooplObject(int fieldCount, String className) {
        type = new TypeClassType(className);
        elements = new int[fieldCount];
    }

    /**
     * Initialise a new array object.
     * @param length the length of the array
     * @param elementType the element type of the array
     */
    public MooplObject(int length, Type elementType) {
        type = new TypeArray(elementType);
        elements = new int[length];
    }
}
