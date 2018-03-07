package syntaxtree;

import visitor.Visitor;

public class TypeClassType extends Type {

    public final String id;

    public TypeClassType(String aid) {
        id = aid;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TypeClassType)) {
            return false;
        }
        TypeClassType other = (TypeClassType) obj;
        return other.id.equals(id);
    }

    public int hashCode() {
        return 3 + id.hashCode();
    }
    
    public String toString() {
        return id;
    }
}
