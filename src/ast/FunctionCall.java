package ast;

import visitor.Visitor;

import java.util.ArrayList;

public class FunctionCall extends ASTNode{
    public String identifier;
    public ArrayList<String> arguments;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
