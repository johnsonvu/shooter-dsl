package ast;

import visitor.Visitor;

public class Identifier extends ASTNode{
    public String name;

    public Identifier(String id){
        this.name = id;
    }
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
