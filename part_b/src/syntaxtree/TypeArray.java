package syntaxtree;

import visitor.Visitor;

public class TypeArray extends Type {

    public final Type t;

    public TypeArray(Type at) {
        t = at;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean equals(Object obj) {
        if (obj instanceof TypeArray) {
            TypeArray other = (TypeArray) obj;
            return other.t.equals(t);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 4 + 7 * t.hashCode();
    }

    public String toString() {
        return "arrayof " + t;
    }
}
