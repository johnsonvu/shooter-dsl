package ast;

import lib.enums.Comparator;
import visitor.Visitor;

public class Condition extends ASTNode{
    public Expression ex1;
    public Expression ex2;
    public Comparator comparator;
    public Condition andCondition;
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
