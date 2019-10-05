package ast;

import visitor.Visitor;
import java.util.List;

public class FunctionCall extends ASTNode{
    public Identifier name;
    public List<Number> arguments; //should be expr

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
