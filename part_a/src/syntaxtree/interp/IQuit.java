package syntaxtree.interp;

import visitor.Visitor;

public class IQuit extends ICommand {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
