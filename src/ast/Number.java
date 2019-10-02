package ast;

import visitor.Visitor;

public class Number extends ASTNode{
    public int number;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
