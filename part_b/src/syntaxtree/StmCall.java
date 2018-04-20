package syntaxtree;

import java.util.List;
import visitor.Visitor;

public class StmCall extends Stm {

    public final Exp e;
    public final String id;
    public final List<Exp> es;

    public StmCall(Exp ae, String aid, List<Exp> aes) {
        e = ae;
        id = aid;
        es = aes;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
