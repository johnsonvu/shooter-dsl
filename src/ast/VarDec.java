package ast;

import visitor.Visitor;

public class VarDec extends Statement{
    public String name;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}