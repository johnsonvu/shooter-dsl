package evaluate;

import ast.*;
import ast.Number;
import evaluate.protoypes.*;
import game.model.*;
import game.view.Game;

import ui.Main;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EvaluateVisitor implements Visitor<Integer> {
    private static int MAX_NUMBER = 100000;

    private static HashMap<String, Integer> varTable = null;
    private static HashMap<String, Integer> globalVarTable = new HashMap<>();

    private static GameObject gameObject = null; //for changing instances
    private static GameObjectProto objectPrototype = null; //Blueprints for an instance's properties (kind of like js prototypes?)
    private static HashMap<String, GameObjectProto> objectProtoTable = new HashMap<>();

    private static HashMap<String, FunctionBlock> blockTable = new HashMap<>();

    public EvaluateVisitor(){
        FunctionBlock defaultFB = new FunctionBlock();
        defaultFB.params = new ArrayList<>();
        defaultFB.block = new Block();
        defaultFB.block.statements = new ArrayList<>();
        ShootStatement ss = new ShootStatement();
        ss.direction = new Direction("down");
        defaultFB.block.statements.add(ss);

        blockTable.put("default", defaultFB);
    }

    @Override
    public Integer visit(ASTNode v) {
        return 0;
    }

    @Override
    //PROGRAM ::= GAME_DEF OBJECT_MODIFIER* FUNCTION_DEC*
    public Integer visit(Program p) {

        p.game.accept(this);
        for(ObjectModifier om : p.objects){
            om.accept(this);
        }
        for(FunctionDec fn : p.functions){
            fn.accept(this);
        }
        return 0;
    }

    @Override
    //GAME_DEF ::= "make game" IDENTIFIER "{" "height = " NUMBER ", width = " NUMBER" "}" "{" GAME_STATEMENT* "}"
    public Integer visit(GameDef gd) {
        Game.getInstance().setWindow(gd.name, gd.height, gd.width);
        for(Statement s : gd.statements) {
            s.accept(this);
        }
        return 0;
    }

    @Override
    public Integer visit(ObjectModifier om) {
        objectPrototype = objectProtoTable.get(om.identifier.name);
        if (objectPrototype == null){
            objectPrototype = new EnemyProto(om.identifier.name, 1, 1);
            objectProtoTable.put(om.identifier.name, objectPrototype);
        }

        for(PropertyStatement ps : om.propertyStatements){
            ps.accept(this);
        }

        objectPrototype.behaviour = om.behave;

        return 0;
    }


    @Override
    public Integer visit(Statement s) {
        return 0;
    }

    @Override
    //GAME_STATEMENT ::= MAKE_STATEMENT | FOR_LOOP
    public Integer visit(GameStatement gs) {
        gs.accept(this);
        return 0;
    }

    @Override
    //MAKE_STATEMENT ::=  "make" EXPR? TYPE IDENTIFIER
    public Integer visit(MakeStatement ms) {

        int number = ms.number == null? 1 : ms.number.accept(this);

        switch(ms.type.type){
            case PLAYER:
                PlayerProto playerProto = new PlayerProto(ms.identifier.name,1, 1);
                if(objectProtoTable.containsKey(ms.identifier.name)) {
                    playerProto = (PlayerProto) objectProtoTable.get(ms.identifier.name);
                }
                else {
                    playerProto.behaviour = new Identifier("default");
                    objectProtoTable.put(ms.identifier.name, playerProto);
                }

                for(int i =0; i< number; i++){
                    Player player = new Player(playerProto, ms.identifier.name);
                    player.apply(playerProto);
                    Main.gameObjects.add(player);
                }
                break;
            case ENEMY:
                EnemyProto enemyProto = new EnemyProto(ms.identifier.name, 1, 1);
                if(objectProtoTable.containsKey(ms.identifier.name)) {
                    enemyProto = (EnemyProto) objectProtoTable.get(ms.identifier.name);
                }
                else {
                    enemyProto.behaviour = new Identifier("default");
                    objectProtoTable.put(ms.identifier.name, enemyProto);  //enemy should implement GameObject
                }

                for(int i =0; i< number; i++){
                    Enemy enemy = new Enemy(enemyProto, ms.identifier.name);
                    enemy.apply(enemyProto);
                    Main.gameObjects.add(enemy);
                }
                break;
            case PROJECTILE:
                ProjectileProto projectileProto = new ProjectileProto(ms.identifier.name,1, 1);
                objectProtoTable.put(ms.identifier.name, projectileProto);

//                for(int i =0; i< number; i++){
//                    Projectile projectile = new Projectile(projectileProto, ms.identifier.name, DIRECTION.UP);
//                    Main.gameObjects.add(projectile);
//                }
                break;

            case ITEM:
                ItemProto itemProto = new ItemProto(ms.identifier.name,1, 1);
                if(objectProtoTable.containsKey(ms.identifier.name)) {
                    itemProto = (ItemProto) objectProtoTable.get(ms.identifier.name);
                }
                else {
                    itemProto.behaviour = new Identifier("default");
                    objectProtoTable.put(ms.identifier.name, itemProto);
                }

                for(int i =0; i< number; i++){
                    Item item = new Item(itemProto, ms.identifier.name);
                    item.apply(itemProto);
                    Main.gameObjects.add(item);
                }
                break;
        }

        return 0;
    }

    @Override
    public Integer visit(PropertyStatement ps) {
        GameObjectProto gop = objectPrototype;

        switch (ps.property.property){
            case DAMAGE:
                gop.damage = ps.expr.accept(this); //this is wrong because it's doing prototype, not instance
                applyAll(gop, g->g.damage=gop.damage);
                break;
            case HEALTH:
                gop.health = ps.expr.accept(this);
                applyAll(gop, g->g.health=gop.health);
                break;
        }

        return null;
    }

    @Override
    public Integer visit(FunctionDec fd) {
        blockTable.put(fd.name.name, fd.functionBlock);
        return null;
    }

    @Override
    public Integer visit(FunctionBlock fb) {
        fb.block.accept(this);
        return fb.retExpr == null? null : fb.retExpr.accept(this);
    }

    @Override
    public Integer visit(FunctionCall fc) {
        FunctionBlock fb =  blockTable.get(fc.name.name);
        if(fb == null){
            System.err.println("Cannot find identifier \"" + fc.name.name + "\" in declaration");
            System.exit(0);
        }
        String key;
        Integer value;
        List<Integer> args = fc.arguments.stream().map(arg -> arg.accept(this)).collect(Collectors.toList());

        for (int i=0; i<fc.arguments.size(); i++) {
            key = fb.params.get(i).name;
            value = args.get(i);
            store(key, value);
        }

        HashMap<String, Integer> currentScope = varTable;
        varTable = new HashMap<>();

        Integer retVal = fb.accept(this);

        varTable = currentScope;
        return retVal;
    }

    @Override
    public Integer visit(Block b) {
        for (Statement s: b.statements) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Integer visit(BehaveStatement bs) {
        return null;
    }

    @Override
    public Integer visit(MovementStatement ms) {
//        applyAll(objectPrototype, go -> {
//            int times = ms.expr == null? 1 : ms.expr.accept(this);
//            for(int i=0; i<times;i++) {
//                go.move(ms.direction.direction);
//            }});
            int times = ms.expr == null? 1 : ms.expr.accept(this);
            for(int i=0; i<times;i++) {
                gameObject.move(ms.direction.direction);
            }
        return null;
    }

    @Override
    public Integer visit(ShootStatement ss) {
//        applyAll(objectPrototype, go -> ((Enemy) go).shoot(ss.direction.direction));
        ((Enemy) gameObject).shoot(ss.direction.direction);
        return null;
    }

    @Override
    public Integer visit(IfStatement iffy) {
        if(iffy.condition.accept(this) == 1){
            iffy.block.accept(this);
        }

        return null;
    }

    @Override
    public Integer visit(Condition cond) {
        if(cond.andCondition != null) {
            if (cond.andCondition.accept(this) == 0) return 0;

        }

        int num1 = cond.ex1.accept(this);
        int num2 = cond.ex2.accept(this);

        switch(cond.comparator){
            case GREATER_THAN:
                return num1 > num2 ? 1 : 0;
            case LESS_THAN:
                return num1 < num2 ? 1 : 0;
            case EQUAL:
                return num1 == num2 ? 1 : 0;
        }
        return 0;
    }

    @Override
    public Integer visit(Property p) {
        return null;
    }

    @Override
    public Integer visit(VarDec vd) {
        store(vd.name, null);
        return 0;
    }

    @Override
    public Integer visit(VarSet vs) {
        store(vs.id, vs.value.accept(this));
        return 0;
    }

    @Override
    public Integer visit(Expression expr) {
        if (expr.op == null) {
            return expr.ex1.accept(this);
        }

        if(expr.op != null){
            Expression myExpr = new Expression();
            switch (expr.op.operation) {
                case PLUS:
                    try {
                        return Math.addExact(expr.ex1.accept(this), expr.ex2.accept(this));
                    }catch(ArithmeticException ae){
                        return MAX_NUMBER;
                    }
                case MINUS:
                    return expr.ex1.accept(this) - expr.ex2.accept(this);
                case MULTIPLY:
                    try {
                        myExpr.ex1 = new Number(Math.multiplyExact(expr.ex1.accept(this), expr.ex2.ex1.accept(this)));
                    }catch(ArithmeticException ae){
                        myExpr.ex1 = new Number(MAX_NUMBER);
                    }
                    myExpr.op = expr.ex2.op;
                    myExpr.ex2 = expr.ex2.ex2;
                    return myExpr.accept(this);
                case DIVIDE:
                    myExpr.ex1 = new Number(expr.ex1.accept(this) / expr.ex2.ex1.accept(this));
                    myExpr.op = expr.ex2.op;
                    myExpr.ex2 = expr.ex2.ex2;
                    return myExpr.accept(this);
                }
        }
        return null;
    }



    @Override
    public Integer visit(Type t) {
        return null;
    }

    @Override
    public Integer visit(Identifier id) {
        return lookup(id);
    }

    @Override
    public Integer visit(Number n) {
        return n.number;
    }

    private Integer lookup(String key){
        if(key.equals("damage")) return gameObject.damage;
        if(key.equals("health")) return gameObject.health;

        Integer value = null;
        if(varTable != null) value = varTable.get(key);
        if(value == null) value = globalVarTable.get(key);

        return value;
    }

    private Integer lookup(Identifier id){
        if(id.name.equals("damage")) return gameObject.damage;
        if(id.name.equals("health")) return gameObject.health;

        Integer value = null;
        if(varTable != null) value = varTable.get(id.name);
        if(value == null) value = globalVarTable.get(id.name);

        return value;
    }

    private void store(String key, Integer value){
        if(key.equals("damage")) {
            gameObject.damage=value;
            return;
        }
        if(key.equals("health")) {
            gameObject.health=value;
            return;
        }

        if(varTable == null) globalVarTable.put(key, value);
        else varTable.put(key, value);
    }

    private void store(Identifier key, Integer value){
        if(key.equals("damage")) {
            gameObject.damage=value;
            return;
        }
        if(key.equals("health")) {
            gameObject.health=value;
            return;
        }

        if(varTable == null) globalVarTable.put(key.name, value);
        else varTable.put(key.name, value);
    }

    public void run(Identifier behaviour, GameObject go){
        String id = behaviour.name;

        gameObject = go;

        objectPrototype = go.proto;

        FunctionBlock block =  blockTable.get(id);
        if(block == null){
            System.err.println("Cannot find identifier \"" + id + "\" in declaration");
            System.exit(0);
        }
        block.accept(this);
    }


    /***
     * Takes a GameObjectPrototype and for every GameObject that has that prototype, we apply the function f.
     * @param proto
     * @param f
     */
    public void applyAll(GameObjectProto proto, Consumer<GameObject> f){

        List<GameObject> relevantObjects = Main.gameObjects.stream().filter(go -> go.proto.equals(proto)).collect(Collectors.toList());

        for (GameObject g: relevantObjects) {
            f.accept(g);
        }
    }
}