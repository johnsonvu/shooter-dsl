package ast;

import visitor.Visitor;
import java.util.List;

public class Block extends ASTNode{
    public List<Statement> statements;
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
