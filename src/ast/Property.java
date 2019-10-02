package ast;

import visitor.Visitor;

public class Property extends ASTNode{
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
