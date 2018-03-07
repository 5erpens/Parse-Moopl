package syntaxtree;

import visitor.Visitor;

public class TypeBoolean extends Type {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean equals(Object obj) {
        return (obj instanceof TypeBoolean);
    }

    public int hashCode() {
        return 0;
    }
    
    public String toString() {
        return "boolean";
    }
}
