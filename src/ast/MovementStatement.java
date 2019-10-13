package ast;

import visitor.Visitor;

public class MovementStatement extends BehaveStatement {
    public Expression expr;
    public Direction direction;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
