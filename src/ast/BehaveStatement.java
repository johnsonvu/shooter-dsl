package ast;

import visitor.Visitor;

public class BehaveStatement extends Statement {
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
