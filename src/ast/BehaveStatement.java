package ast;

import visitor.Visitor;

public class BehaveStatement extends Statement{
    public BehaveStatement statement;
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
