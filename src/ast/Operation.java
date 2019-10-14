package ast;

import visitor.Visitor;

public class Operation extends ASTNode{
    public lib.enums.Operation operation;

    public Operation(String direction) {
        switch (direction.toLowerCase()) {
            case "+":
                this.operation = lib.enums.Operation.PLUS;
                break;
            case "-":
                this.operation = lib.enums.Operation.MINUS;
                break;
            case "/":
                this.operation = lib.enums.Operation.DIVIDE;
                break;
            case "*":
                this.operation = lib.enums.Operation.MULTIPLY;
                break;
        }
    }
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
