package ast;

import visitor.Visitor;
import java.util.List;

public class FunctionDec extends ASTNode{
    public Identifier name;
    public FunctionBlock functionBlock;

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
