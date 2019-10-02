package ast;

import visitor.Visitor;

public class LoopStatement extends Statement {
    public int iterations;
    public Block block;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
