package ast;

import visitor.Visitor;

public class ShootStatement extends ASTNode{
    public Direction direction;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
