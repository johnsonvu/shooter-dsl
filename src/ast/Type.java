package ast;

import visitor.Visitor;

public class Type extends ASTNode{
    public lib.enums.Type type;

    public Type(String type) {
        this.type = lib.enums.Type.valueOf(type.toUpperCase());
    }
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
