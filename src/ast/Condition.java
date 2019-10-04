package ast;

import visitor.Visitor;

public class Condition extends ASTNode{
    public String itemOne;
    public String itemTwo;
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
