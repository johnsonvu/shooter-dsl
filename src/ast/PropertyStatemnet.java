package ast;

import visitor.Visitor;

import java.util.Map;

public class PropertyStatemnet extends ASTNode{
    public Map propertyMap;
    public FunctionDec func;


    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
