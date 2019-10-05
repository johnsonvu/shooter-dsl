package ast;

import visitor.Visitor;

public class Type extends ASTNode{
    public String type;

    public Type(String ty){
        this.type = ty;
    }
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
