# shooter-dsl
A DSL to quickly prototype shooter games. A CPSC 410 project.

## EBNF
```
PROGRAM ::= GAME_DEF OBJECT_DEF* FUNCTION_DEC*

GAME_DEF ::= "make game" IDENTIFIER "{" "height = " NUMBER ", width = " NUMBER" "}" "{" GAME_STATEMENT* "}"

OBJECT_DEF ::= "define" IDENTIFIER "{" PROPERTY_STATEMENTS "}"

PROPERTY_STATEMENTS ::= PROPERTY_STATEMENT* ("behave = " IDENTIFIER)?

PROPERTY_STATEMENT  ::= PROPERTY "=" (NUMBER | DIRECTION)

STATEMENT :: = GAME_STATEMENT | BEHAVE_STATEMENT | PROPERTY_STATEMENT

GAME_STATEMENT ::= MAKE_STATEMENT | FOR_LOOP

MAKE_STATEMENT ::=  "make" NUMBER? TYPE IDENTIFIER

BEHAVE_STATEMENT ::= MOVEMENT_STATEMENT | SHOOT_STATEMENT

MOVEMENT_STATEMENT ::= "move" NUMBER DIRECTION

SHOOT_STATEMENT ::= "shoot" DIRECTION

FUNCTION_DEC ::= "define" IDENTIFIER "(" (PARAM,)* PARAM? ")" BLOCK

FUNCITON_CALL ::= "call" IDENTIFIER "(" (PARAM,)* PARAM? ")"

BLOCK ::= "{" STATEMENT* "}"

PROPERTY ::= "damage" | "health"

IF_STATEMENT ::= "if (" CONDITION ") {" RESULT_STATEMENT "}"

GAME_STATE ::= win | lose

TYPE ::= "player" | "projectile" | "enemy" | "item"

IDENTIFIER ::= [A-Z|a-z|0-9]+

NUMBER ::= [0-9]+

CONDITION ::= IDENTIFIER (">"| "<" | "=") IDENTIFIER

DIRECTION ::= UP | DOWN | LEFT | RIGHT
```

## Group members
1. Johnson Vu
2. Justin Kwan
3. Leon Lui
4. MengXin Zhao
5. Varun Belani


