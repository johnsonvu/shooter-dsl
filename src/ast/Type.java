package ast;

import lib.TYPE;
import visitor.Visitor;

public class Type extends ASTNode{
    public TYPE type;

    public Type(String type) {
        this.type = TYPE.valueOf(type);
    }
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
