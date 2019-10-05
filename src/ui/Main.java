package ui;

import ast.ASTNode;
import parser.Parser;
import tokenizer.Tokenizer;

public class Main {
    public static void main(String[] args) {
        String[] reservedWords = {"\\(", "\\)", "\\{", "\\}", ","};
        Tokenizer tokenizer = Tokenizer.getInstance("game.txt", reservedWords);
        tokenizer.tokenize();
        Parser parser = new Parser();
        ASTNode ast = parser.parse();

    }
}
