package ast;

import lib.PROPERTY;
import visitor.Visitor;

public class Property extends ASTNode{
    public PROPERTY property;

    public Property(String property) {
        switch(property.toLowerCase()) {
            case "damage":
                this.property = PROPERTY.DAMAGE;
                break;
            case "health":
                this.property = PROPERTY.HEALTH;
                break;
            default:
                this.property = PROPERTY.HEALTH;
                break;
        }
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
