package ast;

import visitor.Visitor;

public class IfStatement extends GameStatement{
    public Condition condition;
    public Block block;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
