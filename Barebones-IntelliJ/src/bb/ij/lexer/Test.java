package bb.ij.lexer;

/**
 * Created by eim on 8/16/2017.
 */
public class Test {
    public static void main(String[] args) {
        BBLexer lexer = new BBLexer();
        String toTokenize = "<html>\n" +
                "   <head><title>Hello World</title></head>\n" +
                "   \n" +
                "   <body>\n" +
                "      Hello World!<br/>\n" +
                "      <%\n" +
                "         out.println(\"Your IP address is \" + request.getRemoteAddr());\n" +
                "      %>\n" +
                "   </body>\n" +
                "</html>";
        lexer.start(toTokenize, 0,1,0);
        for(int i = 0; i < 3; i ++) {
            System.out.println(lexer.getTokenType());
            System.out.print(lexer.getTokenSequence());
            lexer.advance();;
        }
    }
}
