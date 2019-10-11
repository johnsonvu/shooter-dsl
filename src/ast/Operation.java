package ast;

import lib.DIRECTION;
import lib.OPERATION;
import visitor.Visitor;

public class Operation extends ASTNode{
    public OPERATION operation;

    public Operation(String direction) {
        switch (direction.toLowerCase()) {
            case "+":
                this.operation = OPERATION.PLUS;
                break;
            case "-":
                this.operation = OPERATION.MINUS;
                break;
            case "/":
                this.operation = OPERATION.DIVIDE;
                break;
            case "*":
                this.operation = OPERATION.MULTIPLY;
                break;
        }
    }
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
