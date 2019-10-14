package ast;

import visitor.Visitor;

public class MakeStatement extends GameStatement {
    public Expression number;
    public Type type;
    public Identifier identifier;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
