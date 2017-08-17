/**
* This class is a lexer for Barebones Templating for use
* in the Barebones IntelliJ plugin.
*/

package bb.ij.parsing;

%%

%class BbLexer
%unicode
%line
%column

/* Storing Stuff */
COMMENT = "<%--" [^*] "--%>"
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

  {EXPRESSION_START_OLD}  {yybegin(EXPRESSION_OLD); return BbTokenTypes.EXPRESSION_START_OLD; }
  {EXPRESSION_START_NEW} {yybegin(EXPRESSION_NEW); return BbTokenTypes.EXPRESSION_START_NEW; }
  {STATEMENT_START} {yybegin(STATEMENT); return BbTokenTypes.STATEMENT_START; }

  /* Directive Handling */
  {DIRECTIVE_START}   {return BbTokenTypes.DIRECTIVE_START; }
  {DIRECTIVE_END}     {return BbTokenTypes.DIRECTIVE_END; }

  /* Directive Keywords */
  "extends"           {return BbTokenTypes.EXTENDS; }
  "section"           {return BbTokenTypes.SECTION; }
  "end section"       {return BbTokenTypes.END_SECTION; }
  "params"            {return BbTokenTypes.PARAMS; }
  "import"            {return BbTokenTypes.IMPORT; }
  "include"           {return BbTokenTypes.INCLUDE; }
  "layout"            {return BbTokenTypes.LAYOUT; }
  "content"           {return BbTokenTypes.CONTENT; }

  [^]+                {return BbTokenTypes.OUTER_TEMPLATE_CONTENT; }


}

<STATEMENT> {
  {STATEMENT_END}             { yybegin(YYINITIAL); return BbTokenTypes.STATEMENT_END;}
  [^]+                        {return BbTokenTypes.INNER_TEMPLATE_CONTENT;}
}

<EXPRESSION_OLD> {
  {EXPRESSION_END_OLD}             { yybegin(YYINITIAL); return BbTokenTypes.EXPRESSION_END_OLD;}
  [^]+                             {return BbTokenTypes.INNER_TEMPLATE_CONTENT;}
}

<EXPRESSION_NEW> {
  {EXPRESSION_END_NEW}             { yybegin(YYINITIAL); return BbTokenTypes.EXPRESSION_END_NEW;}
  [^]+                        {return BbTokenTypes.INNER_TEMPLATE_CONTENT;}
}
