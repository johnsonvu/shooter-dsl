package ast;

import visitor.Visitor;

import java.util.ArrayList;

public class FunctionDec extends ASTNode{
    public String identifier;
    public ArrayList<String> arguments;
    public String block;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
