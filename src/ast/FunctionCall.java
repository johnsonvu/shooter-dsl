package ast;

import visitor.Visitor;
import java.util.List;

public class FunctionCall extends Expression{
    public Identifier name;
    public List<Expression> arguments;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
