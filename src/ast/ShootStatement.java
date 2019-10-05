package ast;

import visitor.Visitor;

public class ShootStatement extends BehaveStatement{
    public Direction direction;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
