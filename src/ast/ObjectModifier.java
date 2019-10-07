package ast;

import visitor.Visitor;

import java.util.List;

public class ObjectModifier extends ASTNode{
    public Identifier identifier;
    public List<PropertyStatement> propertyStatements;
    public Identifier behave;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
