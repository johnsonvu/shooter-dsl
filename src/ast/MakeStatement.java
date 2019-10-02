package ast;

import visitor.Visitor;

public class MakeStatement extends ASTNode{
    public Number number;
    public Type type;
    public Identifier identifier;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
