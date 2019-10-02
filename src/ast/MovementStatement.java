package ast;

import visitor.Visitor;

public class MovementStatement extends ASTNode{
    public Number number;
    public Direction direction;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
