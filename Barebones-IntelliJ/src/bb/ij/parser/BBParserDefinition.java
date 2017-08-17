package bb.ij.parser;

import bb.ij.psi.impl.BBFileImpl;
import bb.ij.psi.impl.BBPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;


import bb.ij.lexer.*;
/**
 * Created by eim on 8/14/2017.
 */
public class BBParserDefinition implements ParserDefinition{
    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return null;
    }

    @Override
    public PsiParser createParser(Project project) {
        return null;
    }

    @Override
    public IFileElementType getFileNodeType() {
        return BBTokenTypes.FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return BBTokenTypes.WHITESPACES;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return BBTokenTypes.COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return BBTokenTypes.STRING_LITERALS;
    }

    //TODO: This method needs to be more robust, return more types of elements
    @NotNull
    @Override
    public PsiElement createElement(ASTNode astNode) {
        return new BBPsiElement(astNode);
    }

    @Override
    public PsiFile createFile(FileViewProvider fileViewProvider) {
        return new BBFileImpl(fileViewProvider);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode astNode, ASTNode astNode1) {
        return SpaceRequirements.MAY;
    }
}
