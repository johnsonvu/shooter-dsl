package parser;

import ast.ASTNode;
import ast.Program;
import tokenizer.Tokenizer;

public class Parser {

    public Parser(){

    }

    public ASTNode parse(){
        Program programBuddy = new Program();
        ParseVisitor visitorBuddy = new ParseVisitor();

        ASTNode returnBuddy = programBuddy.accept(visitorBuddy);
        return returnBuddy;

    }
}
