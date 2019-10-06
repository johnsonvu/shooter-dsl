package ast;

import visitor.Visitor;

public class PropertyStatement extends Statement{
    public Property property;
    public Number value;


    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
