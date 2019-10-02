package ast;

import visitor.Visitor;

public class GameDef extends ASTNode{
    public String identifier;
    public int height;
    public int width;
    public String statement;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
