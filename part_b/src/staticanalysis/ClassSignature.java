package staticanalysis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import syntaxtree.MethodDecl;
import syntaxtree.Type;

/**
 * Moopl class signatures.
 */
public class ClassSignature {

    private final String name;
    private final String parentName;
    private final Map<String, MethodSignature> methods;
    private final Map<String, Type> fields;

    public ClassSignature(String name, String parentName) {
        this.name = name;
        this.parentName = parentName;
        methods = new HashMap<String, MethodSignature>();
        fields = new HashMap<String, Type>();
    }

    /**
     * The class name.
     *
     * @return the class name
     */
    public String getName() {
        return name;
    }

    /**
     * The parent name.
     *
     * @return the parent name, or null if this class has no parent
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Add a new field declaration.
     *
     * @param id the field name
     * @param type the field type
     * @return true if the field was added, false if this class already contains
     * a field declaration with the given name (does NOT check for existing
     * declarations in ancestor classes)
     */
    public boolean addField(String id, Type type) {
        if (fields.containsKey(id)) {
            return false;
        } else {
            fields.put(id, type);
            return true;
        }
    }

    /**
     * The type for a named field.
     *
     * @param name the field name
     * @return the type of the field if this class includes a field declaration
     * with the given name, null otherwise (does NOT look for inherited fields)
     */
    public Type getFieldType(String name) {
        return fields.get(name);
    }
    
    /**
     * The number of fields declared in this class signature (does NOT include
     * inherited fields)
     * 
     * @return the number of fields declared in this class signature
     */
    public int getImmediateFieldCount() {
        return fields.size();
    }

    /**
     * This list of field names declared in this class signature.
     *
     * @return the list of field names declared in this class signature (does
     * NOT include inherited fields)
     * @see SymbolTable.getAllFieldNames()
     */
    public List<String> getImmediateFieldNames() {
        List<String> fieldNames = new LinkedList<String>();
        fieldNames.addAll(fields.keySet());
        return fieldNames;
    }

    /**
     * Add a new method signature to the class signature.
     *
     * @param methodName the method name
     * @param type the method return type (null for procedures)
     * @param formalTypes the list of parameter types
     * @param methodDecl the method declaration AST
     * @return true if the signature was added, false if a signature with the
     * given name was already present (does NOT check ancestor classes)
     */
    public boolean addMethod(String methodName, Type type, List<Type> formalTypes, MethodDecl methodDecl) {
        if (methods.containsKey(methodName)) {
            return false;
        }
        MethodSignature sig = new MethodSignature(methodName, type, formalTypes, methodDecl);
        methods.put(methodName, sig);
        return true;
    }

    /**
     * The signature for a named method declared in this class signature.
     *
     * @param id the method name
     * @return the signature for the given method name, or null if this class
     * does not declare a method with the given name (does NOT check for
     * inherited methods)
     */
    public MethodSignature getMethodSignature(String id) {
        return methods.get(id);
    }

    /**
     * This list of method names declared in this class signature.
     *
     * @return the list of method names declared in this class signature (does
     * NOT include inherited methods)
     */
    public List<String> getImmediateMethodNames() {
        List<String> methodNames = new LinkedList<String>();
        methodNames.addAll(methods.keySet());
        return methodNames;
    }
}