package ast;

import visitor.Visitor;

public class GameDef extends ASTNode{
    public String name;
    public int height;
    public int width;
    public Statement statement;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
