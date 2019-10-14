package ast;

import visitor.Visitor;

public class Expression extends ASTNode{
    public Expression ex1;
    public Operation op;
    public Expression ex2;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}