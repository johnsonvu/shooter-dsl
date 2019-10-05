package ast;

import lib.PROPERTY;
import visitor.Visitor;

public class PropertyStatement extends Statement{
    public PROPERTY property;
    public Number value;


    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
