package syntaxtree;

import visitor.Visitor;

public class ExpOp extends Exp {

    public enum Op {
        
        AND("and"), LESSTHAN("<"), EQUALS("=="), DIV("div"), PLUS("+"), MINUS("-"), TIMES("*");
        
        private final String name;
        private Op(String name) { this.name = name; }
        public String toString() { return name; }
    }

    public final Exp e1, e2;
    public final Op op;

    public ExpOp(Exp ae1, Op op, Exp ae2) {
        e1 = ae1;
        this.op = op;
        e2 = ae2;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}