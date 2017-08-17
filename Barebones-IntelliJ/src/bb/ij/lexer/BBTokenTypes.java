package bb.ij.lexer;

import bb.ij.lang.BBLanguage;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * Created by eim on 8/14/2017.
 */
public class BBTokenTypes {
    private BBTokenTypes(){

    }

    //Default BB Token Types
    public static final IElementType STRING_CONTENT = new BBTokenType("STRING_CONTENT");
    public static final IElementType STATEMENT = new BBTokenType("STATEMENT");
    public static final IElementType EXPRESSION= new BBTokenType("EXPRESSION");
    public static final IElementType DIRECTIVE = new BBTokenType("DIRECTIVE");
    public static final IElementType COMMENT = new BBTokenType("COMMENT");

    public static final IElementType OUTER_ELEMENT_TYPE = new BBTokenType("BB_FRAGMENT");
    public static final IElementType OUTER_TEMPLATE_CONTENT = new BBTokenType("OUTER_TEMPLATE_CONTENT");

    public static final IElementType WHITE_SPACE = new BBTokenType("WHITESPACES");

    public static final IElementType EXPRESSION_START_OLD = new BBTokenType("EXPRESSION_START_OLD");
    public static final IElementType EXPRESSION_START_NEW = new BBTokenType("EXPRESSION_START_NEW");
    public static final IElementType DIRECTIVE_START = new BBTokenType("DIRECTIVE_START");
    public static final IElementType STATEMENT_START = new BBTokenType("STATEMENT_START");

    public static final IElementType EXPRESSION_END_OLD = new BBTokenType("EXPRESSION_END_OLD");
    public static final IElementType EXPRESSION_END_NEW = new BBTokenType("EXPRESSION_END_NEW");
    public static final IElementType DIRECTIVE_END = new BBTokenType("DIRECTIVE_END");
    public static final IElementType STATEMENT_END = new BBTokenType("STATEMENT_END");


    public static final IElementType INNER_JAVA_CONTENT = new BBTokenType("INNER_JAVA_CONTENT");

    public static final IElementType EXTENDS = new BBTokenType("EXTENDS");
    public static final IElementType SECTION = new BBTokenType("SECTION");
    public static final IElementType END_SECTION = new BBTokenType("END_SECTION");
    public static final IElementType PARAMS = new BBTokenType("PARAMS");
    public static final IElementType IMPORT = new BBTokenType("IMPORT");
    public static final IElementType INCLUDE = new BBTokenType("INCLUDE");
    public static final IElementType LAYOUT = new BBTokenType("LAYOUT");
    public static final IElementType CONTENT = new BBTokenType("CONTENT");

    public static final TokenSet DIRECTIVE_KEYWORDS = TokenSet.create(
            EXTENDS,
            SECTION,
            END_SECTION,
            PARAMS,
            IMPORT,
            INCLUDE,
            LAYOUT,
            CONTENT
    );

    public static final TemplateDataElementType OUTER_TEMPLATE = new TemplateDataElementType("OUTER_TEMPLATE", StdFileTypes.HTML.getLanguage(), OUTER_TEMPLATE_CONTENT, OUTER_ELEMENT_TYPE);

    public static final IFileElementType FILE = new IFileElementType("File", BBLanguage.INSTANCE);

    public static final TokenSet STRING_LITERALS = TokenSet.create();
    public static final TokenSet WHITESPACES = TokenSet.create(WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(COMMENT);

}
