package bb.tokenizer;

import java.util.List;
import java.util.ArrayList;
import bb.tokenizer.Token.TokenType;

import static bb.tokenizer.Token.TokenType.*;

public class ETokenizer implements ITokenizer {
    private int line, column, position;

    ETokenizer() {
        line = 1;
        column = 1;
        position = 0;
    }


    public List<Token> tokenize(String str) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        while(str.length() > 0) {
            str = addNextToken(str, tokens);
        }
        line = 1;
        column = 1;
        position = 0;
        return tokens;
    }

    /** Creates the next token from the given String, and adds it to tokens. */
    private String addNextToken(String str, ArrayList<Token> tokens) {
        TokenType nextToken = getNextTokenType(str);
        if (nextToken == STRING_CONTENT) {
            return addStringContent(str, tokens);
        } else if (nextToken == STATEMENT) {
            return addStatement(str.substring(2), tokens);
        } else if (nextToken == EXPRESSION) {
            return addExpression(str.substring(2), tokens);
        } else if (nextToken == DIRECTIVE) {
            return addDirective(str, tokens);
        }
        throw new RuntimeException("Error at line " + line + "and column " + column);
    }

    /** Returns the correct token type to be parsed. */
    private TokenType getNextTokenType(String str) {
        if (str.length() >= 2) {
            if (str.charAt(0) == '<' && str.charAt(1) == '%') {
                return STATEMENT;
            }
            if (str.charAt(0) == '$' && str.charAt(1) == '{') {
                return EXPRESSION;
            }
        }
        return STRING_CONTENT;
    }

    /** Helper method: Given that the next token to add is a STRING_CONTENT,
     *  correctly processes the token, adds it to tokens, and returns a string
     *  with the token removed. */
    private String addStringContent(String str, ArrayList<Token> tokens) {
        int index = 0;
        int tokenStartCol = column;
        int tokenStartLine = line;
        Character current = null;
        Character previous;
        while (index < str.length() - 1) {
            previous = current;
            current = str.charAt(index);
            if ((current == '%' && previous == '<') || (current == '{' && previous == '$')) {
                Token currentToken = new Token(STRING_CONTENT, str.substring(0, index - 1), tokenStartLine, tokenStartCol, position);
                tokens.add(currentToken);
                return str.substring(index - 1);
            }
            if (current == 12) {
                line += 1;
                column = 0;
            }
            index += 1;
            position += 1;
            column += 1;
        }
        Token currentToken = new Token(STRING_CONTENT, str, tokenStartLine, tokenStartCol, 0);
        tokens.add(currentToken);
        return "";
    }

    /** Helper method: Given that the next token to add is a STATEMENT,
     *  correctly processes the token, adds it to tokens, and returns a string
     *  with the token removed.*/
    private String addStatement(String str, ArrayList<Token> tokens) {
        int index = 0;
        int tokenStartCol = column;
        int tokenStartLine = line;
        Character current = null;
        Character previous;
        int quoteState = 0;
        while (index < str.length() - 1) {
            previous = current;
            current = str.charAt(index);
            if (current == '"') {
                if (quoteState == 1 && previous != '\\') {
                    quoteState = 0;
                } else if (quoteState == 0) {
                    quoteState = 1;
                }
            } else if (current == '\'') {
                if (quoteState == 2 && previous != '\\') {
                    quoteState = 0;
                } else if (quoteState == 0) {
                    quoteState = 2;
                }
            } else if (quoteState == 0) {
                if (current == '>' && previous == '%') {
                    Token currentToken = new Token(STATEMENT, str.substring(0, index - 1), tokenStartLine, tokenStartCol, position);
                    tokens.add(currentToken);
                    return str.substring(index + 1);
                }
                if (current == '%' && previous == '<') {
                    throw new RuntimeException("Attempted to open new statement within statement");
                }
            }
            if (current == 12) {
                line += 1;
                column = 0;
            }
            index += 1;
            position += 1;
            column += 1;
        }
        throw new RuntimeException("Error: Statement beginning at col " + tokenStartCol + " and line " + tokenStartLine + "is not closed");
    }

    /** Helper method: Given that the next token to add is an EXPRESSION,
     *  correctly processes the token, adds it to tokens, and returns a string
     *  with the token removed.
     *  */
    private String addExpression(String str, ArrayList<Token> tokens) {
        int index = 0;
        int tokenStartCol = column;
        int tokenStartLine = line;
        Character current = null;
        Character previous;
        int quoteState = 0;
        while (index < str.length()) {
            previous = current;
            current = str.charAt(index);
            if (current == '"') {
                if (quoteState == 1 && previous != '\\') {
                    quoteState = 0;
                } else if (quoteState == 0) {
                    quoteState = 1;
                }
            } else if (current == '\'') {
                if (quoteState == 2 && previous != '\\') {
                    quoteState = 0;
                } else if (quoteState == 0) {
                    quoteState = 2;
                }
            }
            else if (quoteState == 0) {
                if (current.equals('}')) {
                    Token currentToken = new Token(EXPRESSION, str.substring(0, index), tokenStartLine, tokenStartCol, position);
                    tokens.add(currentToken);
                    return str.substring(index + 1);
                } else if (index < str.length() - 1 && current == '{' && previous == '$') {
                    throw new RuntimeException("Error: Attempted to open new expression within expression");
                }
            }
            if (current == 12) {
                line += 1;
                column = 0;
            }
            index += 1;
            position += 1;
            column += 1;
        }
        throw new RuntimeException("Error: Expression beginning at col " + tokenStartCol + " and line " + tokenStartLine + "is not closed");
    }

    private String addDirective(String str, ArrayList<Token> tokens) {
        return null;
    }

}
