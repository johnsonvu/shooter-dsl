package ast;

import visitor.Visitor;

import java.util.ArrayList;

public class Block extends ASTNode{
    public ArrayList<Statement> statements;
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
