package ast;

import visitor.Visitor;

import java.util.List;

public class Program extends ASTNode{
    public GameDef game;
    public List<ObjectDef> objects;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
