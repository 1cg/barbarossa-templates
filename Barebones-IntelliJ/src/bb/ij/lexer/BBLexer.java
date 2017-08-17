package bb.ij.lexer;

import bb.ij.parser.BBIjTokenMap;
import bb.tokenizer.BBTokenizer;
import bb.tokenizer.Token;
import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by eim on 8/14/2017.
 */
public class BBLexer extends LexerBase {
    private BBTokenizer _tokenizer;
    private List<Token> _tokens;
    private CharSequence _buffer;
    private int _iBufferIndex;
    private int _iBufferEndOffset;
    @Nullable
    private IElementType _tokenType;
    private int _iTokenEndOffset; // Positioned after the last symbol of the current token
    private int _tokenIndex;
    private String _tokenSequence;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        _buffer = buffer;
        _iBufferIndex = startOffset;
        _iBufferEndOffset = endOffset;
        _tokenType = null;

        _iTokenEndOffset = startOffset;
        _tokenizer = new BBTokenizer();
        _tokens = _tokenizer.tokenize(buffer.toString());
        _tokenIndex = 0;

    }

    //TODO: Ask scott if this is ok
    @Override
    @NotNull
    public String getTokenSequence() {
        locateToken();

        return _tokenSequence;
    }

    @Override
    public int getState() {
        return 0;
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        locateToken();

        return _tokenType;
    }

    @Override
    public int getTokenStart() {
        return _iBufferIndex;
    }

    @Override
    public int getTokenEnd() {
        locateToken();

        return _iTokenEndOffset;
    }

    @Override
    public void advance() {
        locateToken();
        _tokenType = null;
    }

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return _buffer;
    }

    @Override
    public int getBufferEnd() {
        return _iBufferEndOffset;
    }

    private void locateToken() {
        if (_tokenType != null) {
            return;
        }

        if (_iTokenEndOffset == _iBufferEndOffset) {
            _tokenType = null;
            _iBufferIndex = _iBufferEndOffset;
            return;
        }

        _iBufferIndex = _iTokenEndOffset;

        nextTokenFromTokenizer();

        if (_iTokenEndOffset > _iBufferEndOffset) {
            _iTokenEndOffset = _iBufferEndOffset;
        }
    }

    private void nextTokenFromTokenizer() {
        if (_tokenIndex >= _tokens.size()) {
            return;
        }
        Token currentToken = _tokens.get(_tokenIndex);
        _tokenType = BBIjTokenMap.instance().getTokenType(currentToken.getType());
        _tokenSequence = currentToken.getContent();
        if (_iTokenEndOffset == currentToken.getEndPosition()) {
            _tokenIndex++;
        }
        if (_tokenIndex < _tokens.size()) {
            _iTokenEndOffset = _tokens.get(_tokenIndex).getEndPosition();
        }
    }
}
