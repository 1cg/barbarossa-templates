package bb.hgen.demo;

import java.io.IOException;


public class Bootstrap extends bb.runtime.BaseBBTemplate {

private static Bootstrap INSTANCE = new Bootstrap();


    public static String render() {
        StringBuilder sb = new StringBuilder();
        renderInto(sb);
        return sb.toString();
    }

    public static void renderInto(Appendable buffer) {
        INSTANCE.renderImpl(buffer);
    }

    public void renderImpl(Appendable buffer) {
    try {
            buffer.append("<html>\n<body>\n<h1>This is a demo template</h1>\n<p>1 + 1 = ");
            buffer.append(toS(1 + 1));
            buffer.append("</p>\n</body>\n</html>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
