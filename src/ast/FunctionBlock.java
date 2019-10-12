package ast;

import visitor.Visitor;

import java.util.List;

public class FunctionBlock extends ASTNode {
    public Block block;
    public Expression retExpr;
    public List<Identifier> params;


    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
