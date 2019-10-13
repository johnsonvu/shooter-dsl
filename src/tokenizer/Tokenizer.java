package tokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Tokenizer {
    private static Tokenizer tokenizer;
    private String filePath;
    private String program;
    private String[] reservedWords;
    private String[] tokens;
    private int currentToken;

    private Tokenizer(Path filePath, String[] reservedWords)
    {
        currentToken = 0;
        this.reservedWords = reservedWords;
        try{
            program = Files.readString(filePath, UTF_8);
        }
        catch(IOException ioEx)
        {
            System.out.printf("Error: could not open file.");
        }
    }

    public void tokenize()
    {
        removeComments();

        for (String resWord : reservedWords)
        {
            String resWordWithSpaces = " " + resWord + " ";
            program = program.replaceAll(resWord, resWordWithSpaces);
        }

        tokens = program.split("\\s+");

    }

    private void removeComments() {
        program = program.replaceAll("\\/\\/.*(\\n|\\r\\n)", "");
    }

    public static Tokenizer getInstance() {
        return tokenizer;
    }

    public static Tokenizer getInstance(String fileName, String[] reservedWords)
    {
        if(tokenizer == null) {
            Path path = Paths.get(fileName);
            tokenizer = new Tokenizer(path, reservedWords);
        }
        return tokenizer;
    }

    public boolean checkNext(String expectedTokenRegExp)
    {
        return tokens[currentToken].matches(expectedTokenRegExp);
    }

    /**
     * checkNext
     * @param expectedTokenRegExp
     * @param lookAhead
     * @return this returns next token looking ahead: Example: lookAhead 2 will look 1 token past current token
     */
    public boolean checkNext(String expectedTokenRegExp, int lookAhead)
    {
        return tokens[currentToken+lookAhead-1].matches(expectedTokenRegExp);
    }

    public String getNext()
    {
        return tokens[currentToken++];
    }

    public Boolean getAndCheckNext(String expectedToken)
    {
        return tokens[currentToken++].equals(expectedToken);
    }

    public Boolean hasMoreTokens ()
    {
        return currentToken < tokens.length;
    }
	
}
