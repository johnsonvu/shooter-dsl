package ast;

import visitor.Visitor;

import java.util.List;

public class GameDef extends ASTNode{
    public String name;
    public int width;
    public int height;
    public List<GameStatement> statements;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
