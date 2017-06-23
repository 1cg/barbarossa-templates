package bb.hgen.demo;

import java.io.IOException;

import java.util.*;

public class SectionTest {

    public static class MySection {

        public static class yourSection {

            public static String render() {
                StringBuilder sb = new StringBuilder();
                renderInto(sb);
                return sb.toString();
            }

            public static void renderInto(Appendable buffer) {
                try {
                    buffer.append("\n        <body>\n        ");
                    int fontSize;
                    buffer.append("\n        ");
                    for ( fontSize = 1; fontSize <= 3; fontSize++){
                    buffer.append("\n        <font color = \"green\" size = \"");
                    buffer.append(toS(fontSize));
                    buffer.append("\">\n            JSP Tutorial\n        </font><br />\n        ");
                    }
                    buffer.append("\n        </body>\n    ");
                    buffer.append("\n    ");
                    buffer.append("\n\n</body>\n\n</html>\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private static String toS(Object o) {
                return o == null ? "" : o.toString();
            }
        }


        public static String render() {
            StringBuilder sb = new StringBuilder();
            renderInto(sb);
            return sb.toString();
        }

        public static void renderInto(Appendable buffer) {
            try {
                buffer.append("\n    ");
                buffer.append("\n        <body>\n            <h1>This is a demo template</h1>\n            <p>1 + 1 = ");
                buffer.append(toS(1 + 1));
                buffer.append("</p>\n        </body>\n    ");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static String toS(Object o) {
            return o == null ? "" : o.toString();
        }
    }


    public static String render() {
        StringBuilder sb = new StringBuilder();
        renderInto(sb);
        return sb.toString();
    }

    public static void renderInto(Appendable buffer) {
        try {
            buffer.append("<html>\n<head><title>First JSP</title></head>\n<body>\n");
            double num = Math.random();
if (num > 0.95) {
            buffer.append("\n<h2>You'll have a luck day!</h2><p>( ");
            buffer.append(toS(num));
            buffer.append(" )</p>\n");
            } else {
            buffer.append("\n<h2>Well, life goes on ... </h2><p>( ");
            buffer.append(toS(num));
            buffer.append(" )</p>\n");
            }
            buffer.append("\n<a href=\"www.facebook.com\"><h3>Try Again</h3></a>\n\n    ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toS(Object o) {
        return o == null ? "" : o.toString();
    }
}