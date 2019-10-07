package ast;

import visitor.Visitor;

public class VarSet extends Statement{
    public Identifier id;
    public Expression value;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}