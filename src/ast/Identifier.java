package ast;

import visitor.Visitor;

public class Identifier extends ASTNode{
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
