package parser;

import ast.ASTNode;
import ast.Program;
import tokenizer.Tokenizer;
import visitor.Visitor;

public class ParseVisitor implements Visitor<ASTNode> {
    Tokenizer tokzenizer;

    @Override
    public ASTNode visit(ASTNode v) {
        return null;
    }

    @Override
    public ASTNode visit(Program p) {
        tokenizer.checkNext();
        return null;
    }
}
