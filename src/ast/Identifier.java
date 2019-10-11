package ast;

import visitor.Visitor;

public class Identifier extends Expression{
    public String name;

    public Identifier(String id){
        this.name = id;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public static boolean isValid(Identifier id) {
        return id.name.matches( "[A-Z|a-z|0-9]+");
    }
}
