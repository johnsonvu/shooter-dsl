package ast;

import visitor.Visitor;

public class Direction extends ASTNode{
    public String direction;
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
