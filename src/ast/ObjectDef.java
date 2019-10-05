package ast;

import visitor.Visitor;

import java.util.List;

public class ObjectDef extends ASTNode{
    public Identifier identifier;
    public List<PropertyStatement> propertyStatements;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
