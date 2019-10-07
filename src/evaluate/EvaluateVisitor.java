package evaluate;

import ast.*;
import ast.Number;
import game.Game;
import visitor.Visitor;

import java.util.HashMap;
import java.util.function.Function;

public class EvaluateVisitor implements Visitor<Game> {
    ASTNode ast;
    Game game;

    public EvaluateVisitor(ASTNode astNode){
        ast = astNode;
    }

    @Override
    public Game visit(ASTNode v) {
        return null;
    }

    @Override
    //PROGRAM ::= GAME_DEF OBJECT_MODIFIER* FUNCTION_DEC*
    public Game visit(Program p) {
        game = new Game();
        p.game.accept(this);
        for(ObjectModifier om : p.objects){
            om.accept(this);
        }
        for(FunctionDec fn : p.functions){
            fn.accept(this);
        }
        return game;
    }

    @Override
    //GAME_DEF ::= "make game" IDENTIFIER "{" "height = " NUMBER ", width = " NUMBER" "}" "{" GAME_STATEMENT* "}"
    public Game visit(GameDef gd) {
        game.setWindow(gd.name, gd.height, gd.width);
        for(GameStatement gs : gd.statements) {
            gs.accept(this);
        }

        return null;
    }

    @Override
    public Game visit(ObjectModifier gd) {
        return null;
    }

    @Override
    public Game visit(Statement s) {
        return null;
    }

    @Override
    //GAME_STATEMENT ::= MAKE_STATEMENT | FOR_LOOP
    public Game visit(GameStatement gs) {
        gs.accept(this);
        return null;
    }

    @Override
    //MAKE_STATEMENT ::=  "make" NUMBER? TYPE IDENTIFIER
    public Game visit(MakeStatement ms) {
        switch(ms.type.type){
            case PLAYER:
                game.makePlayer(ms.identifier.name, ms.number.number);
                break;
            case ENEMY:
                game.makeEnemy(ms.identifier.name, ms.number.number);
                break;
            case PROJECTILE:
                game.makeProjectile(ms.identifier.name, ms.number.number);
                break;
            case ITEM:
                game.makeItem(ms.identifier.name, ms.number.number);
                break;
            default:

        }
        return null;
    }

    @Override
    public Game visit(PropertyStatement ps) {
        return null;
    }

    @Override
    public Game visit(FunctionDec fd) {
        return null;
    }

    @Override
    public Game visit(FunctionCall fc) {
        return null;
    }

    @Override
    public Game visit(Block b) {
        return null;
    }

    @Override
    public Game visit(BehaveStatement bs) {
        return null;
    }

    @Override
    public Game visit(MovementStatement ms) {
        return null;
    }

    @Override
    public Game visit(ShootStatement ss) {
        return null;
    }

    @Override
    public Game visit(Property p) {
        return null;
    }

    @Override
    public Game visit(Type t) {
        return null;
    }

    @Override
    public Game visit(Identifier id) {
        return null;
    }

    @Override
    public Game visit(Number n) {
        return null;
    }
}