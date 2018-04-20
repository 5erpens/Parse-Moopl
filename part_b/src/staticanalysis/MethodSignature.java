package staticanalysis;

import java.util.ArrayList;
import java.util.List;
import syntaxtree.MethodDecl;
import syntaxtree.Type;

/**
 * Moopl method signatures.
 */
public class MethodSignature {

    private final String methodName;
    private final Type returnType; // null for procedures
    private final List<Type> paramTypes;
    private final MethodDecl methodDecl;

    public MethodSignature(String methodName, Type type, List<Type> formalTypes, MethodDecl methodDecl) {
        this.methodName = methodName;
        this.returnType = type;
        this.methodDecl = methodDecl;
        paramTypes = new ArrayList<Type>();
        for (Type t : formalTypes) {
            paramTypes.add(t);
        }
    }

    /**
     * The name of this method.
     *
     * @return the method name
     */
    public String getName() {
        return methodName;
    }

    /**
     * The return type of this method.
     *
     * @return the return type
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * The type of a given parameter.
     *
     * @param i the index of the parameter in this method's parameter list
     * (indexes start at zero)
     * @return the type for the specified parameter
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Type getParamType(int i) {
        return paramTypes.get(i);
    }

    /**
     * The arity (number of parameters) of this method.
     *
     * @return the arity of this method
     */
    public int getArity() {
        return paramTypes.size();
    }

    /**
     * The method declaration AST for this method.
     *
     * @return the method declaration AST
     */
    public MethodDecl getMethodDecl() {
        return methodDecl;
    }
}