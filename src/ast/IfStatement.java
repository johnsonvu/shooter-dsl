package ast;

import visitor.Visitor;

public class IfStatement extends Statement{
    public Condition condition;
    public Block block;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
