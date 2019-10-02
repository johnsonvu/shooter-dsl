package ast;

import visitor.Visitor;

public class Condition extends ASTNode{
    public String identifierOne;
    public String identifierTwo;
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
