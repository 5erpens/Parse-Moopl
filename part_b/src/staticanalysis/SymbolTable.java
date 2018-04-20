package staticanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import syntaxtree.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Symbol tables containing class and method signatures.
 */
public class SymbolTable {

    /**
     * A table mapping class names to their signatures.
     */
    private Map<String, ClassSignature> classTable;

    /**
     * Initialise a new, empty symbol table.
     */
    public SymbolTable() {
        classTable = new HashMap<String, ClassSignature>();
    }

    /**
     * Add a new, empty class signature.
     *
     * @param id the name of the new class
     * @param parent the name of the parent class (use null if the class has no
     * parent)
     * @return true if a new class signature was added, false if a class
     * signature with the given name was already present in the symbol table
     */
    public boolean addClass(String id, String parent) {
        if (classDeclarationIsInScope(id)) {
            return false;
        } else {
            classTable.put(id, new ClassSignature(id, parent));
            return true;
        }
    }

    /**
     * Check if type t1 is a subtype of type t2.
     *
     * @param t1 the "smaller" type
     * @param t2 the "bigger" type
     * @return true if t1 is a subtype of t2
     */
    public boolean isSubtype(Type t1, Type t2) {
        return isSupertype(t2, t1);
    }

    /**
     * Check if type t1 is a supertype of type t2.
     *
     * @param t1 the "bigger" type
     * @param t2 the "smaller" type
     * @return true if t2 is a subtype of t1
     */
    public boolean isSupertype(Type t1, Type t2) {
        // note that this implements "invariant subtyping" for arrays
        if (t1.equals(t2)) {
            return true;
        } else if (t1 instanceof TypeClassType && t2 instanceof TypeClassType) {
            // this deals with inheritance
            TypeClassType i1 = (TypeClassType) t1;
            TypeClassType i2 = (TypeClassType) t2;
            ClassSignature classSig2 = getClassSignature(i2.id);
            while (classSig2 != null) {
                if (i1.id.equals(classSig2.getName())) {
                    return true;
                } else {
                    if (classSig2.getParentName() == null) {
                        classSig2 = null;
                    } else {
                        classSig2 = getClassSignature(classSig2.getParentName());
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Check that the inheritance hierarchy is well-behaved. The following
     * checks are made:
     * <ul>
     * <li>The symbol table contains an entry for the parent of each entry which
     * extends a parent
     * <li>There are no cycles in the inheritance hierarchy
     * <li>No ancestor method is overloaded in a descendant
     * </ul>
     *
     * @throws StaticAnalysisException if any class names an undefined parent
     */
    public void checkInheritanceHierarchy() throws StaticAnalysisException {
        // we should really do a sensible graph analysis but this
        // will be fast enough for small programs
        for (ClassSignature sig : classTable.values()) {
            checkInheritanceHierarchy(sig, new ArrayList<ClassSignature>());
        }
    }

    private void checkInheritanceHierarchy(ClassSignature sig, List<ClassSignature> descendants) {
        String cn = sig.getName();
        List<String> fields = sig.getImmediateFieldNames();
        String pn = sig.getParentName();
        for (ClassSignature dsig : descendants) {
            if (dsig.getName().equals(cn)) {
                throw new StaticAnalysisException("Class " + cn + " is in an inheritance cycle");
            }
            for (String mName : sig.getImmediateMethodNames()) {
                MethodSignature m = sig.getMethodSignature(mName);
                MethodSignature dm = dsig.getMethodSignature(mName);
                if (dm != null) {
                    int arity = m.getArity();
                    int pArity = dm.getArity();
                    Type t = m.getReturnType();
                    Type dt = dm.getReturnType();
                    boolean overload = false;
                    if (arity != pArity) {
                        overload = true;
                    } else if ((t != null && dt == null) || (t == null && dt != null) || (t != null && dt != null && !isSupertype(t, dt))) {
                        overload = true;
                    } else {
                        for (int i = 0; i < arity; i++) {
                            if (!isSupertype(dm.getParamType(i), m.getParamType(i))) {
                                overload = true;
                                break;
                            }
                        }
                    }
                    if (overload) {
                        throw new StaticAnalysisException("Method " + m.getName() + " in class " + cn + " is overloaded in descendant " + dsig.getName());
                    }
                }
            }
        }

        if (pn != null) {
            ClassSignature psig = classTable.get(pn);
            if (psig == null) {
                throw new StaticAnalysisException("Class " + cn + " extends undefined parent " + pn);
            }
            descendants.add(sig);
            checkInheritanceHierarchy(psig, descendants);
        }
    }

    /**
     * Look up a class signature. Use null as the class name to look up the
     * signature for the dummy top-level "class".
     *
     * @param id the class name (or null for dummy the top-level class)
     * @return the class signature, or null if no signature exists for the given
     * name
     */
    public ClassSignature getClassSignature(String id) {
        return classTable.get(id);
    }

    /**
     * Check if a class signature exists for a given class name.
     *
     * @param id the class name
     * @return true if the symbol table contains an entry for the given name,
     * false otherwise
     */
    public boolean classDeclarationIsInScope(String id) {
        return classTable.containsKey(id);
    }

}
