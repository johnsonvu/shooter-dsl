package ast;

import visitor.Visitor;

import java.util.ArrayList;

public class FunctionDec extends ASTNode{
    public String name;
    public ArrayList<String> parameters;
    public Block block;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
