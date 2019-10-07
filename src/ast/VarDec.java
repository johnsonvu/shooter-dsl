package ast;

import visitor.Visitor;

public class VarDec extends ASTNode{
    public String name;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}