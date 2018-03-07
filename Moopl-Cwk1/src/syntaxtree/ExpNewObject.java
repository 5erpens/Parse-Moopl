package syntaxtree;

import java.util.List;
import visitor.Visitor;

public class ExpNewObject extends Exp {

    public final String id;
    public final List<Exp> es;

    public ExpNewObject(String aid, List<Exp> aes) {
        id = aid;
        es = aes;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
