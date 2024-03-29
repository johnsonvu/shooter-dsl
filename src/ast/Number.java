package ast;

import visitor.Visitor;

public class Number extends Expression{
    public int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
