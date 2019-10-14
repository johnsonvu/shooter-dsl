package ast;

import visitor.Visitor;

public class Property extends ASTNode{
    public lib.enums.Property property;

    public Property(String property) {
        switch(property.toLowerCase()) {
            case "damage":
                this.property = lib.enums.Property.DAMAGE;
                break;
            case "health":
                this.property = lib.enums.Property.HEALTH;
                break;
            default:
                this.property = lib.enums.Property.HEALTH;
                break;
        }
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
