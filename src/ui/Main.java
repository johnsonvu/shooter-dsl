package ui;

import ast.ASTNode;
import ast.FunctionDec;
import evaluate.Evaluator;
import parser.Parser;
import tokenizer.Tokenizer;
import java.util.HashMap;

public class Main {
    public static HashMap<String, String> varTable = new HashMap<>();
    public static HashMap<String, FunctionDec> funTable = new HashMap<>();

    public static void main(String[] args) {
        String[] reservedWords = {"\\(", "\\)", "\\{", "\\}", ","};
        Tokenizer tokenizer = Tokenizer.getInstance("game.txt", reservedWords);
        tokenizer.tokenize();
        Parser parser = new Parser();
        ASTNode ast = parser.parse();

        Evaluator evaluate = new Evaluator(ast);
        evaluate.evaluate();
        System.out.println("dones");
    }
}
