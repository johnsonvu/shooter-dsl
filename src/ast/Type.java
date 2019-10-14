package ast;

import visitor.Visitor;

public class Type extends ASTNode{
    public lib.enums.Type type;

    public Type(String type) {
        try {
            this.type = lib.enums.Type.valueOf(type.toUpperCase());
        }catch (Exception e){
            System.err.println("Expected \"player\", \"item\", \"enemy\", or \"projectile\" but found \"" + type + "\"");
            System.exit(0);
        }
    }
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
