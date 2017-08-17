package bb.ij.parser;

import bb.ij.lexer.BBTokenTypes;
import bb.tokenizer.Token;
import com.intellij.psi.tree.IElementType;
import bb.ij.lexer.BBTokenTypes.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eim on 8/16/2017.
 */
public class BBIjTokenMap {
    private static final BBIjTokenMap INSTANCE = new BBIjTokenMap();

    @NotNull
    public static BBIjTokenMap instance() {
        return INSTANCE;
    }
    private final Map<Token.TokenType, IElementType> _map = new HashMap<>();
    private BBIjTokenMap() {
        _map.put(Token.TokenType.COMMENT, BBTokenTypes.COMMENT);
        _map.put(Token.TokenType.DIRECTIVE, BBTokenTypes.DIRECTIVE);
        _map.put(Token.TokenType.EXPRESSION, BBTokenTypes.EXPRESSION);
        _map.put(Token.TokenType.STATEMENT, BBTokenTypes.STATEMENT);
        _map.put(Token.TokenType.STRING_CONTENT, BBTokenTypes.STRING_CONTENT);
    }

    public IElementType getTokenType(@NotNull Token.TokenType tokenType) {
        if (_map.containsKey(tokenType)) {
            return _map.get(tokenType);
        }
        return null;
    }
}
