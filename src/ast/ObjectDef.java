package ast;

import visitor.Visitor;

public class ObjectDef extends ASTNode{
    public Identifier identifier;
    public PropertyStatement propertyStatement;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
