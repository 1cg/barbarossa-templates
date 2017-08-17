/**
* This class is a lexer for Barebones Templating for use
* in the Barebones IntelliJ plugin.
*/

package bb.ij.lexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.lexer.FlexLexer;


%%

%class _BBLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}




/* Storing Stuff */
COMMENT = "<%--" [^] "--%>"
EXPRESSION_START_OLD = "<%="
EXPRESSION_START_NEW = "${"
DIRECTIVE_START = "<%@"
STATEMENT_START = "<%"

EXPRESSION_END_OLD = "%>"
EXPRESSION_END_NEW = "}"
DIRECTIVE_END = "%>"
STATEMENT_END = "%>"

%state STATEMENT
%state EXPRESSION_OLD
%state EXPRESSION_NEW

%%

<YYINITIAL> {
  {COMMENT} {return BbTokenTypes.COMMENT; }

  {EXPRESSION_START_OLD}  {yybegin(EXPRESSION_OLD); return BBTokenTypes.EXPRESSION_START_OLD; }
  {EXPRESSION_START_NEW} {yybegin(EXPRESSION_NEW); return BBTokenTypes.EXPRESSION_START_NEW; }
  {STATEMENT_START} {yybegin(STATEMENT); return BBTokenTypes.STATEMENT_START; }

  /* Directive Handling */
  {DIRECTIVE_START}   {return BBTokenTypes.DIRECTIVE_START; }
  {DIRECTIVE_END}     {return BBTokenTypes.DIRECTIVE_END; }

  /* Directive Keywords */
  "extends"           {return BBTokenTypes.EXTENDS; }
  "section"           {return BBTokenTypes.SECTION; }
  "end section"       {return BBTokenTypes.END_SECTION; }
  "params"            {return BBTokenTypes.PARAMS; }
  "import"            {return BBTokenTypes.IMPORT; }
  "include"           {return BBTokenTypes.INCLUDE; }
  "layout"            {return BBTokenTypes.LAYOUT; }
  "content"           {return BBTokenTypes.CONTENT; }

  [^]+                {return BBTokenTypes.OUTER_TEMPLATE_CONTENT; }


}

<STATEMENT> {
  {STATEMENT_END}             { yybegin(YYINITIAL); return BBTokenTypes.STATEMENT_END;}
  [^]+                        {return BBTokenTypes.INNER_TEMPLATE_CONTENT;}
}

<EXPRESSION_OLD> {
  {EXPRESSION_END_OLD}             { yybegin(YYINITIAL); return BBTokenTypes.EXPRESSION_END_OLD;}
  [^]+                             {return BBTokenTypes.INNER_TEMPLATE_CONTENT;}
}

<EXPRESSION_NEW> {
  {EXPRESSION_END_NEW}             { yybegin(YYINITIAL); return BBTokenTypes.EXPRESSION_END_NEW;}
  [^]+                        {return BBTokenTypes.INNER_TEMPLATE_CONTENT;}
}
