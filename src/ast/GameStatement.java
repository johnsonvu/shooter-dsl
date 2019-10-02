package ast;

import visitor.Visitor;

public class GameStatement extends Statement{
    public Statement statement;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
